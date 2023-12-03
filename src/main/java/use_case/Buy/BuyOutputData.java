package use_case.Buy;

/**
 * Encapsulates the output data of the Buy use case.
 */
public class BuyOutputData {
    private final Double newBalance;
    private Boolean executedPurchase = false;

    /**
     * @param newBalance the new balance of the user after the purchase (assuming one is executed)
     */
    public BuyOutputData(Double newBalance) {
        this.newBalance = newBalance;
    }

    /**
     * @param newBalance the new balance of the user after a property change (potential purchase)
     * @param executedPurchase whether the purchase was executed or not
     */
    public BuyOutputData(Double newBalance, Boolean executedPurchase) {
        this.newBalance = newBalance;
        this.executedPurchase = executedPurchase;
    }

    /**
     * @return whether the purchase was executed or not
     */
    public Boolean getExecutedPurchase() {
        return executedPurchase;
    }

    /**
     * @return the new balance of the user after a property change (potential purchase)
     */
    public Double getNewBalance() {
        return newBalance;
    }
}
