package use_case.ResetBalance;

import entity.*;
import use_case.APIAccessInterface;
import use_case.BaseStockInteractor;

import java.time.LocalDate;
import java.util.HashMap;

public class ResetBalanceInteractor extends BaseStockInteractor implements ResetBalanceInputBoundary {
    final ResetBalanceDataAccessInterface userDataAccessObject;
    ResetBalanceOutputBoundary resetBalancePresenter;
    APIAccessInterface driverAPI;

    public ResetBalanceInteractor(ResetBalanceDataAccessInterface userDataAccessInterface,
                                  ResetBalanceOutputBoundary resetBalancePresenter, APIAccessInterface driverAPI) {
        super(driverAPI);
        this.userDataAccessObject = userDataAccessInterface;
        this.resetBalancePresenter = resetBalancePresenter;
        this.driverAPI = driverAPI;
    }
    @Override
    public void execute() {
        User user = userDataAccessObject.get();
        Double curBalance = user.getBalance();
        Double amountToAdd = 10000.0;
        user.setBalance(amountToAdd);
        user.clearPortfolio();

        // honestly, maybe consider adding a TopupTransaction
        HashMap<String, TransactionHistory> userHistory = user.getHistory();

        Transaction transaction = new TopupTransaction(curBalance + amountToAdd, new PricePoint(LocalDate.now(), curBalance));

        super.addToHistory(userHistory, "Stonks App", curBalance, transaction);
        userDataAccessObject.save();

        ResetBalanceOutputData result = new ResetBalanceOutputData(curBalance + amountToAdd);
        resetBalancePresenter.prepareSuccessView(result);
    }
}