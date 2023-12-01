package interface_adapter.ResetBalance;
import use_case.ResetBalance.ResetBalanceInputBoundary;
import use_case.ResetBalance.ResetBalanceInputData;

public class ResetBalanceController {
    final private ResetBalanceInputBoundary resetBalanceInteractor;

    public ResetBalanceController(ResetBalanceInputBoundary resetBalanceInteractor) {
        this.resetBalanceInteractor = resetBalanceInteractor;
    }

    public void execute(Boolean resetPressed) {
        resetBalanceInteractor.execute(new ResetBalanceInputData(resetPressed));
    }
}
