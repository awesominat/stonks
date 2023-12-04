package interface_adapter.ResetBalance;
import use_case.ResetBalance.ResetBalanceInputBoundary;
import use_case.ResetBalance.ResetBalanceInputData;

public class ResetBalanceController {
    final private ResetBalanceInputBoundary resetBalanceInteractor;

    /**
     * Constructor for the reset balance controller
     *
     * @param resetBalanceInteractor    The reset balance use case interactor
     */
    public ResetBalanceController(ResetBalanceInputBoundary resetBalanceInteractor) {
        this.resetBalanceInteractor = resetBalanceInteractor;
    }

    /**
     * The execute method for the reset balance controller. Only has one argument, which is
     * whether the reset button was pressed or not. The reset use case requires no other input data.
     *
     * @param resetPressed      A boolean representing whether the reset button was pressed
     */
    public void execute(Boolean resetPressed) {
        resetBalanceInteractor.execute(new ResetBalanceInputData(resetPressed));
    }
}
