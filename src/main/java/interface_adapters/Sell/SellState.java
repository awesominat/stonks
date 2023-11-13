package interface_adapters.Sell;

public class SellState {
    private String amountError = null;
    private String amount = null;

    public SellState(String amount, String amountError) {
        this.amountError = amountError;
        this.amount = amount;
    }

    public String getAmountError() {
        return amountError;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void setAmountError(String amountError) {
        this.amountError = amountError;
    }

    // Because of the previous copy constructor, the default constructor must be explicit. Hence overloading.
    public SellState() {

    }

}
