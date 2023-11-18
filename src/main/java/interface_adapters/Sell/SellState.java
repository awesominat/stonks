package interface_adapters.Sell;

public class SellState {
    private String stockSelected = null;
    private String amountError = null;
    private String amount = null;

    public SellState(String stockSelected, String amount, String amountError) {
        this.amountError = amountError;
        this.amount = amount;
        this.stockSelected = stockSelected;
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

    public String getStockSelected() {
        return stockSelected;
    }

    public void setStockSelected(String stockSelected) {
        this.stockSelected = stockSelected;
    }

    public void setAmountError(String amountError) {
        this.amountError = amountError;
    }

    // Because of the previous copy constructor, the default constructor must be explicit. Hence overloading.
    public SellState() {

    }

}
