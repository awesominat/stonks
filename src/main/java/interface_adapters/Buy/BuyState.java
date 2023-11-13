package interface_adapters.Buy;

public class BuyState {
    private String amount = "";
    private String amountError = null;

    public BuyState(String amount, String amountError) {
        // TODO: Ricky
        this.amount = amount;
        this.amountError = amountError;
    }

    public String getAmount() {

        return amount;
    }

    public void setAmount(String amount) {

        this.amount = amount;
    }

    public String getAmountError() {

        return amountError;
    }

    public void setAmountError(String amountError) {

        this.amountError = amountError;
    }

    // Because of the previous copy constructor, the default constructor must be explicit. Hence overloading.
    public BuyState() {

    }

}
