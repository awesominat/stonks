package use_cases.Sell;

import entities.*;
import interface_adapters.Sell.SellOutputBoundary;
import use_cases.APIAccessInterface;
import use_cases.BaseStockInteractor;

import java.time.LocalDate;
import java.util.HashMap;

public class SellInteractor extends BaseStockInteractor implements SellInputBoundary {
    final SellDataAccessInterface userDataAccessObject;
    SellOutputBoundary sellPresenter;
    APIAccessInterface driverAPI;

    public SellInteractor(SellDataAccessInterface userDataAccessInterface,
                          SellOutputBoundary sellPresenter, APIAccessInterface driverAPI) {
        super(driverAPI);
        this.userDataAccessObject = userDataAccessInterface;
        this.sellPresenter = sellPresenter;
        this.driverAPI = driverAPI;
    }

    @Override
    public void execute(SellInputData sellInputData) {
        String username = sellInputData.getUsername();
        String ticker = sellInputData.getTicker();
        Double amount = sellInputData.getAmount();

        User user = userDataAccessObject.get(username);

        if (!user.hasStock(ticker) || user.getStockOwned(ticker) < amount) {
            sellPresenter.prepareFailView("You can't sell more than you have.");
            return;
        }
        Double currentPrice = driverAPI.getCurrentPrice(ticker).getPrice();
        Double currentlyOwned = user.getStockOwned(ticker);

        user.addBalance(currentPrice * amount);
        HashMap<String, TransactionHistory> userHistory = user.getHistory();

        Transaction transaction = new SellTransaction(amount, new PricePoint(LocalDate.now(), currentPrice));

        user.updatePortfolio(ticker, currentlyOwned - amount);
        super.addToHistory(userHistory, ticker, user, amount, currentPrice, transaction);
        userDataAccessObject.save(user);

        SellOutputData result = new SellOutputData(sellInputData.getAmount(), sellInputData.getTicker());
        sellPresenter.prepareSuccessView(result);
    }
}
