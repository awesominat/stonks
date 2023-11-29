package use_case.Buy;

public class BuyOutputData {
    private final Double newBalance;
    private Boolean executedPurchase = false;

    public BuyOutputData(Double newBalance) {
        this.newBalance = newBalance;
    }

    public BuyOutputData(Double newBalance, Boolean executedPurchase) {
        this.newBalance = newBalance;
        this.executedPurchase = executedPurchase;
    }

    public Boolean getExecutedPurchase() {
        return executedPurchase;
    }

    public Double getNewBalance() {
        return newBalance;
    }
}
