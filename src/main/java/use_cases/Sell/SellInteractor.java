package use_cases.Sell;

import entities.*;
import use_cases.APIAccessInterface;
import use_cases.BaseStockInteractor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SellInteractor extends BaseStockInteractor implements SellInputBoundary {
    final SellDataAccessInterface userDataAccessObject;
    SellOutputBoundary sellPresenter;
    APIAccessInterface driverAPI;

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

    @Override
    public void execute(SellInputData sellInputData) {
        if (sellInputData.isExecuteTypeSell()) {
            String ticker = sellInputData.getTicker();
            Double amount = sellInputData.getAmount();

            User user = userDataAccessObject.get();

            if (amount <= 0.0) {
                sellPresenter.prepareFailView("Please enter a decimal value greater than 0");
                return;
            }
            if (!user.hasStock(ticker) || user.getStockOwned(ticker) < amount) {
                sellPresenter.prepareFailView("You can't sell more than you have.");
                return;
            }

            Double currentPrice = driverAPI.getCurrentPrice(ticker).getPrice();
            Double currentlyOwned = user.getStockOwned(ticker);

            user.addBalance(currentPrice * amount);
            HashMap<String, TransactionHistory> userHistory = user.getHistory();

            Transaction transaction = new SellTransaction(
                    amount,
                    new PricePoint(
                            LocalDate.now(),
                            currentPrice));

            super.updatePortfolio(
                    user,
                    ticker,
                    currentlyOwned - amount
            );

            super.addToHistory(
                    userHistory,
                    ticker,
                    currentPrice,
                    transaction
            );

            super.updatePortfolio(user, ticker, currentlyOwned - amount);
            super.addToHistory(userHistory, ticker, currentPrice, transaction);

            userDataAccessObject.save();

            SellOutputData result = new SellOutputData(
                    sellInputData.getAmount(),
                    sellInputData.getTicker()
            );

            sellPresenter.prepareSuccessView(result);

        } else {
            User user = userDataAccessObject.get();
            HashMap<String, Double> portfolio = user.getPortfolio();
            Double balance = user.getBalance();

            List<String> ownedStocks = new ArrayList<String>();
            ownedStocks.addAll(portfolio.keySet());

            List<Double> ownedAmounts = new ArrayList<Double>();
            ownedAmounts.addAll(portfolio.values());

            List<Double> sellAmounts = new ArrayList<Double>();
            for (String ownedStock : ownedStocks) {
                Double stockPrice = driverAPI.getCurrentPrice(ownedStock).getPrice();
                sellAmounts.add(stockPrice);
            }

            SellOutputData result = new SellOutputData(
                    ownedStocks,
                    ownedAmounts,
                    sellAmounts,
                    balance
            );

            sellPresenter.prepareSuccessView(result);
        }
    }
}
