package interface_adapters.ResetBalance;
import use_cases.ResetBalance.ResetBalanceInputBoundary;
public class ResetBalanceController {
    final private ResetBalanceInputBoundary resetBalanceInteractor;

    public ResetBalanceController(ResetBalanceInputBoundary resetBalanceInteractor) {
        this.resetBalanceInteractor = resetBalanceInteractor;
    }

    public void execute() {
        resetBalanceInteractor.execute();
    }
}
