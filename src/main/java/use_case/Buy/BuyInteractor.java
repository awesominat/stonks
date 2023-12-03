package use_case.Buy;

import entity.*;
import use_case.APIAccessInterface;
import use_case.BaseStockInteractor;

import java.time.LocalDateTime;
import java.util.HashMap;

/**
 * Applies the enterprise application rules for the Buy use case.
 * Handles the business logic for the Buy use case.
 */
public class BuyInteractor extends BaseStockInteractor implements BuyInputBoundary {

    /** The data access object for the user. */
    final BuyDataAccessInterface userDataAccessObject;

    /** The presenter for the Buy use case. */
    BuyOutputBoundary buyPresenter;

    /** The API driver for the Buy use case. */
    APIAccessInterface driverAPI;

    /**
     * @param userDataAccessInterface The data access object for the user.
     * @param buyPresenter The presenter for the Buy use case.
     * @param driverAPI The API driver for the Buy use case.
     */
    public BuyInteractor(
            BuyDataAccessInterface userDataAccessInterface,
            BuyOutputBoundary buyPresenter,
            APIAccessInterface driverAPI
    ) {
        super(driverAPI);
        this.userDataAccessObject = userDataAccessInterface;
        this.buyPresenter = buyPresenter;
        this.driverAPI = driverAPI;
    }

    private Transaction createBuyTransaction(Double amount, Double currentPrice) {
        return new BuyTransaction(
                amount,
                new PricePoint(
                        LocalDateTime.now(),
                        currentPrice
                ));
    }

    /**
     * @param buyInputData The input data for the Buy use case. Contains the ticker and amount.
     */
    @Override
    public void execute(BuyInputData buyInputData) {
        String ticker = buyInputData.getTicker();
        Double amount = buyInputData.getAmount();

        User user = userDataAccessObject.get();

        if (ticker == null && amount == null) {
            BuyOutputData buyOutputData = new BuyOutputData(user.getBalance());
            buyPresenter.prepareSuccessView(buyOutputData);
            return;
        }
        HashMap<String, Double> portfolio = user.getPortfolio();

        if (amount == null) {
            try {
                CompanyInformation companyInformation = driverAPI.getCompanyProfile(ticker);
                Double currentPrice = driverAPI.getCurrentPrice(ticker).getPrice();

                Double currentlyHeld = 0.0;
                if (portfolio.containsKey(ticker)) {
                    currentlyHeld = portfolio.get(ticker);
                }

                BuySearchOutputData result = new BuySearchOutputData(
                        ticker,
                        companyInformation,
                        currentPrice,
                        currentlyHeld,
                        user.getBalance()
                );
                buyPresenter.prepareSuccessView(result);
            } catch (APIAccessInterface.TickerNotFoundException e) { // this should be its own exception, TickerNotFoundException
                buyPresenter.prepareFailView("Please enter a valid ticker.");
            }
            return;
        } else if (amount <= 0) {
            buyPresenter.prepareFailView("Please enter a decimal value greater than 0");
            return;
        }

        Double currentPrice = null;
        try {
            currentPrice = driverAPI.getCurrentPrice(ticker).getPrice();
        } catch (APIAccessInterface.TickerNotFoundException e) {
            buyPresenter.prepareFailView("Please enter a valid ticker.");
            return;
        }
        if (!user.hasEnough(currentPrice * amount)) {
            buyPresenter.prepareFailView("You need $" + (user.getBalance() - (currentPrice * amount))
                        + " more to complete this transaction.");
            return;
        }

        user.spendBalance(currentPrice * amount);

        Transaction transaction = createBuyTransaction(amount, currentPrice);

        super.updatePortfolio(user, ticker, amount);

        super.addToHistory(
                user,
                ticker,
                currentPrice,
                transaction
        );

        userDataAccessObject.save();

        BuyOutputData result = new BuyOutputData(user.getBalance(), true);

        buyPresenter.prepareSuccessView(result);
    }

}
