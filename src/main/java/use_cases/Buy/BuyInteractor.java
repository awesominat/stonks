package use_cases.Buy;

import entities.*;
import use_cases.APIAccessInterface;
import use_cases.BaseStockInteractor;

import java.time.LocalDate;
import java.util.HashMap;

public class BuyInteractor extends BaseStockInteractor implements BuyInputBoundary {
    final BuyDataAccessInterface userDataAccessObject;
    BuyOutputBoundary buyPresenter;
    APIAccessInterface driverAPI;

    public BuyInteractor(BuyDataAccessInterface userDataAccessInterface,
                         BuyOutputBoundary buyPresenter, APIAccessInterface driverAPI) {
        super(driverAPI);
        this.userDataAccessObject = userDataAccessInterface;
        this.buyPresenter = buyPresenter;
        this.driverAPI = driverAPI;
    }

    @Override
    public void execute(BuyInputData buyInputData) {
        String ticker = buyInputData.getTicker();
        Double amount = buyInputData.getAmount();
        Boolean amountFormatError = buyInputData.isAmountFormatError();

        User user = userDataAccessObject.get();

        Double currentPrice = driverAPI.getCurrentPrice(ticker).getPrice();

        if (amountFormatError) {
            buyPresenter.prepareFailView("Please enter a decimal value");
            return;
        }
        if (!user.hasEnough(currentPrice * amount)) {
            buyPresenter.prepareFailView("You are broke. no money. no balance. no stock. no equity.");
            return;
        }

        user.spendBalance(currentPrice * amount);
        HashMap<String, TransactionHistory> userHistory = user.getHistory();

        Transaction transaction = new BuyTransaction(amount, new PricePoint(LocalDate.now(), currentPrice));

        super.updatePortfolio(user, ticker, amount);
        super.addToHistory(userHistory, ticker, user, amount, currentPrice, transaction);
        userDataAccessObject.save();

        BuyOutputData result = new BuyOutputData(buyInputData.getAmount(), buyInputData.getTicker());
        buyPresenter.prepareSuccessView(result);
    }
}
