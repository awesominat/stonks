package interface_adapters.ResetBalance;

public class ResetBalanceState {
    private String statusError = null;

    public ResetBalanceState(String amount, String amountError) {
        // TODO
        this.statusError = amountError;
    }

    public String getStatusError() {
        return statusError;
    }

    public void setStatusError(String statusError) {
        this.statusError = statusError;
    }

    public ResetBalanceState() {
        // TODO
    }
}
