package use_case.ResetBalance;

import entity.PricePoint;
import entity.TopupTransaction;
import entity.Transaction;
import entity.User;
import use_case.APIAccessInterface;
import use_case.BaseStockInteractor;

import java.time.LocalDateTime;

public class ResetBalanceInteractor extends BaseStockInteractor implements ResetBalanceInputBoundary {
    final ResetBalanceDataAccessInterface userDataAccessObject;
    ResetBalanceOutputBoundary resetBalancePresenter;
    APIAccessInterface driverAPI;

    /**
     * Constructor for the reset balance use case interactor
     *
     * @param userDataAccessInterface       allows us to access the user entity, which
     *                                      allows us to reset the balance and portfolio
     * @param resetBalancePresenter         the presenter, which allows us to modify the
     *                                      dashboard state and cause a reset balance success popup to show
     * @param driverAPI                     this is simply needed so that our implementation adheres
     *                                      to the BaseStockInteractor class. This is not used in any way.
     */
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
                        LocalDateTime.now(),
                        amountToAdd
                )
        );
    }

    /**
     * This function runs the reset balance use case and resets the users current balance to 10000
     * and also empties the users portfolio.
     *
     * @param resetBalanceInputData     contains a boolean indicating whether the reset button has been
     *                                  pressed or not
     */
    @Override
    public void execute(ResetBalanceInputData resetBalanceInputData) {
        Boolean resetPressed = resetBalanceInputData.getResetPressed();
        User user = userDataAccessObject.get();
        Double curBalance = user.getBalance();
        Double amountToAdd = User.DEFAULT_BALANCE;

        user.setBalance(amountToAdd);
        user.clearPortfolio();

        Transaction transaction = createResetTransaction(amountToAdd);
        super.addToHistory(user, "RESET", curBalance, transaction);

        this.userDataAccessObject.save();

        ResetBalanceOutputData result = new ResetBalanceOutputData(resetPressed);

        resetBalancePresenter.prepareSuccessView(result);
    }
}
