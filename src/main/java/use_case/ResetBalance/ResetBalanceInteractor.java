package use_case.ResetBalance;

import entity.PricePoint;
import entity.TopupTransaction;
import entity.Transaction;
import entity.User;
import use_case.APIAccessInterface;
import use_case.BaseStockInteractor;
import java.util.HashMap;

import java.time.LocalDate;

public class ResetBalanceInteractor extends BaseStockInteractor implements ResetBalanceInputBoundary {
    final ResetBalanceDataAccessInterface userDataAccessObject;
    ResetBalanceOutputBoundary resetBalancePresenter;
    APIAccessInterface driverAPI;

    public ResetBalanceInteractor(
            ResetBalanceDataAccessInterface userDataAccessInterface,
            ResetBalanceOutputBoundary resetBalancePresenter,
            APIAccessInterface driverAPI
    ) {
        super(driverAPI);
        this.userDataAccessObject = userDataAccessInterface;
        this.resetBalancePresenter = resetBalancePresenter;
        this.driverAPI = driverAPI;
    }

    private Transaction createResetTransaction(Double amountToAdd) {
        return new TopupTransaction(
                1.0,
                new PricePoint(
                        LocalDate.now(),
                        amountToAdd
                )
        );
    }

    @Override
    public void execute() {
        User user = userDataAccessObject.get();

        Double curBalance = user.getBalance();

        Double amountToAdd = 10000.0;

        user.setBalance(amountToAdd);

        user.clearPortfolio();

        Transaction transaction = createResetTransaction(amountToAdd);
        super.addToHistory(user, "RESET", curBalance, transaction);

        this.userDataAccessObject.save();

        ResetBalanceOutputData result = new ResetBalanceOutputData(amountToAdd);

        resetBalancePresenter.prepareSuccessView(result);
    }
}
