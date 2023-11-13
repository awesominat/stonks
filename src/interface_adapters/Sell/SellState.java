package interface_adapters.Sell;

public class SellState {
    private String amountError = null;

    public SellState(String amountError) {
        this.amountError = amountError;
    }

    public String getAmountError() {
        return amountError;
    }

    public void setAmountError(String amountError) {
        this.amountError = amountError;
    }

    // Because of the previous copy constructor, the default constructor must be explicit. Hence overloading.
    public SellState() {

    }

}
