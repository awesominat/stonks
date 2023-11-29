package interface_adapter.ResetBalance;
import use_case.ResetBalance.ResetBalanceInputBoundary;
public class ResetBalanceController {
    final private ResetBalanceInputBoundary resetBalanceInteractor;

    public ResetBalanceController(ResetBalanceInputBoundary resetBalanceInteractor) {
        this.resetBalanceInteractor = resetBalanceInteractor;
    }

    public void execute() {
        resetBalanceInteractor.execute();
    }
}
