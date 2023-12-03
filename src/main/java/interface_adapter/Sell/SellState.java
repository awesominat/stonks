package interface_adapter.Sell;

import java.util.List;

public class SellState {
    // contains the current selected stock in the dropdown menu
    private String stockSelected = null;
    // contains the error message regarding the amount, either a format error
    // or the user tried to sell more stocks than owned
    private String amountError = null;
    // contains the amount of stocks the user wants to sell
    private String amount = null;
    // contains the success message displayed upon selling stocks.
    private String sellSuccess = null;
    // list containing stocks currently owned by the user
    private List<String> ownedStocks = null;
    // list containing amounts of stocks currently owned by the user
    private List<Double> ownedAmounts = null;
    // list containing the sell prices of the stocks owned by the user
    private List<Double> sellAmounts = null;
    // contains the current balance of the user
    private Double balance = null;

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
public void setStockSelected(String stockSelected) { this.stockSelected = stockSelected; }
    public void setAmountError(String amountError) {
        this.amountError = amountError;
    }

    public void setOwnedStocks(List<String> ownedStocks) {
        this.ownedStocks = ownedStocks;
    }

    public List<String> getOwnedStocks() {
        return ownedStocks;
    }

    public void setSellSuccess(String sellSuccess) {
        this.sellSuccess = sellSuccess;
    }

    public String getSellSuccess() {
        return sellSuccess;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Double getBalance() {
        return balance;
    }

    public void setOwnedAmounts(List<Double> ownedAmounts) {
        this.ownedAmounts = ownedAmounts;
    }

    public List<Double> getSellAmounts() {
        return sellAmounts;
    }

    public void setSellAmounts(List<Double> sellAmounts) {
        this.sellAmounts = sellAmounts;
    }

    public List<Double> getOwnedAmounts() {
        return ownedAmounts;
    }

    /**
     * Empty initializer since all attributes have default null values.
     */
    public SellState() {}

}
