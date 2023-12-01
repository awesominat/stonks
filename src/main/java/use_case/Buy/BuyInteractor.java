package use_case.Buy;

import entity.*;
import use_case.APIAccessInterface;
import use_case.BaseStockInteractor;

import java.time.LocalDate;
import java.util.HashMap;

public class BuyInteractor extends BaseStockInteractor implements BuyInputBoundary {
    final BuyDataAccessInterface userDataAccessObject;
    BuyOutputBoundary buyPresenter;
    APIAccessInterface driverAPI;

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
                        LocalDate.now(),
                        currentPrice
                ));
    }
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
            buyPresenter.prepareFailView("You are broke. no money. no balance. no stock. no equity.");
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
