package interface_adapters.ResetBalance;
import use_cases.ResetBalance.ResetBalanceInputBoundary;
import use_cases.ResetBalance.ResetBalanceInputData;
public class ResetBalanceController {
    final private ResetBalanceInputBoundary resetBalanceInteractor;

    public ResetBalanceController(ResetBalanceInputBoundary resetBalanceInteractor) {
        this.resetBalanceInteractor = resetBalanceInteractor;
    }

    public void execute(String username) {
        ResetBalanceInputData resetBalanceInputData = new ResetBalanceInputData(username);
        resetBalanceInteractor.execute(resetBalanceInputData);
    }
}
