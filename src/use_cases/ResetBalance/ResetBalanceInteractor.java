package use_cases.ResetBalance;

import entities.*;
import interface_adapters.ResetBalance.ResetBalanceOutputBoundary;
import use_cases.APIAccessInterface;
import use_cases.BaseStockInteractor;

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
    public void execute(ResetBalanceInputData sellInputData) {
        String username = sellInputData.getUsername();

        User user = userDataAccessObject.get(username);

        if (user.getBalance() >= 500.0) {
            resetBalancePresenter.prepareFailView("You have way too much money.");
            return;
        }

        Double curBalance = user.getBalance();
        Double amountToAdd = 10000.0;
        user.addBalance(amountToAdd);

        // honestly, maybe consider adding a TopupTransaction
        HashMap<String, TransactionHistory> userHistory = user.getHistory();

        Transaction transaction = new TopupTransaction(curBalance + amountToAdd, new PricePoint(LocalDate.now(), curBalance));

        super.addToHistory(userHistory, "Stonks App", user, curBalance + amountToAdd, curBalance, transaction);
        userDataAccessObject.save(user);

        ResetBalanceOutputData result = new ResetBalanceOutputData(curBalance + amountToAdd);
        resetBalancePresenter.prepareSuccessView(result);
    }
}
