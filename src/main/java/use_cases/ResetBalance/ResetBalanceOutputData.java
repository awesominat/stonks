package use_cases.ResetBalance;

public class ResetBalanceOutputData {
    public Double newBalance;

    public ResetBalanceOutputData(Double newBalance) {
        this.newBalance = newBalance;
    }

    public Double getNewBalance() {
        return newBalance;
    }

}
