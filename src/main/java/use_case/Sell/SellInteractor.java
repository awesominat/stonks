package use_case.Sell;

import entity.PricePoint;
import entity.SellTransaction;
import entity.Transaction;
import entity.User;
import use_case.APIAccessInterface;
import use_case.BaseStockInteractor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SellInteractor extends BaseStockInteractor implements SellInputBoundary {
    final SellDataAccessInterface userDataAccessObject;
    SellOutputBoundary sellPresenter;
    APIAccessInterface driverAPI;

    /**
     * Constructor for the sell interactor.
     *
     * @param userDataAccessInterface   allows us to grab the current user
     * @param sellPresenter             allows us to display changes to the screen when the use case is completed.
     * @param driverAPI                 allows us to grab updated stock prices before the sell transaction is made.
     */
    public SellInteractor(
            SellDataAccessInterface userDataAccessInterface,
            SellOutputBoundary sellPresenter,
            APIAccessInterface driverAPI
    ) {
        super(driverAPI);
        this.userDataAccessObject = userDataAccessInterface;
        this.sellPresenter = sellPresenter;
        this.driverAPI = driverAPI;
    }

    /**
     * Creates an instance of the SellTransaction entity
     *
     * @param amount        the amount of the stock to be sold
     * @param currentPrice  the current, up-to-date price of the stock
     * @return              returns the parent Transaction type (relies on abstractions, not
     *                      a concrete implementation like SellTransaction)
     */
    private Transaction createSellTransaction(Double amount, Double currentPrice) {
        return new SellTransaction(
                amount,
                new PricePoint(
                        LocalDateTime.now(),
                        currentPrice
                )
        );
    }

    /**
     * Checks whether the execution type is sell or refresh with the executeTypeSell attribute of the
     * sell input data.
     * Split into two cases with the outer if block, which handles the different execution types
     *
     * @param sellInputData     an instance of sell input data created in the controller
     */
    @Override
    public void execute(SellInputData sellInputData) {
        if (sellInputData.isExecuteTypeSell()) {
            // The first execution type, which occurs when the user presses the sell button from the sell view.
            String ticker = sellInputData.getTicker();
            Double amount = sellInputData.getAmount();

            User user = userDataAccessObject.get();

            // handling all error cases.
            // The error case where the entered value in the sell amount field is invalid.
            if (amount <= 0.0) {
                sellPresenter.prepareFailView("Please enter a decimal value greater than 0");
                return;
            }

            // The error case where the user tries to sell more than they own.
            if (!user.isInPortfolio(ticker) || user.getStockOwned(ticker) < amount) {
                sellPresenter.prepareFailView("You can't sell more than you have.");
                return;
            }

            // This error case will ideally never occur, since we grab stocks from the user portfolio
            // which cannot contain a ticker that does not exist. However, we need to handle
            // the exception that can be thrown by driverAPI.
            Double currentPrice = null;
            try {
                currentPrice = driverAPI.getCurrentPrice(ticker).getPrice();
            } catch (APIAccessInterface.TickerNotFoundException e) {
                sellPresenter.prepareFailView("This ticker does not exist!");
                return;
            }

            user.addBalance(currentPrice * amount);
            Transaction transaction = createSellTransaction(amount, currentPrice);

            super.updatePortfolio(
                    user,
                    ticker,
                    -amount
            );

            super.addToHistory(
                    user,
                    ticker,
                    currentPrice,
                    transaction
            );

            userDataAccessObject.save();

            // prepares the output data with amount and ticker to display a success message
            SellOutputData result = new SellOutputData(
                    sellInputData.getAmount(),
                    sellInputData.getTicker()
            );

            sellPresenter.prepareSuccessView(result);
        } else {
            // This is the second execution case, which is a refresh case. This occurs when the user
            // navigates to the sell page or when the cache refreshes
            User user = userDataAccessObject.get();
            HashMap<String, Double> portfolio = user.getPortfolio();
            Double balance = user.getBalance();

            List<String> ownedStocks = new ArrayList<String>();
            ownedStocks.addAll(portfolio.keySet());

            List<Double> ownedAmounts = new ArrayList<Double>();
            ownedAmounts.addAll(portfolio.values());

            SellOutputData result = new SellOutputData(
                    ownedStocks,
                    ownedAmounts,
                    balance
            );

            sellPresenter.prepareSuccessView(result);
        }
    }
}
