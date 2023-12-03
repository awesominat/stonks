package interface_adapter.Sell;

import java.util.List;

public class SellState {
    private String stockSelected = null;
    private String amountError = null;
    private String amount = null;
    private String sellSuccess = null;
    private List<String> ownedStocks = null;
    private List<Double> ownedAmounts = null;
    private List<Double> sellAmounts = null;
    private Double balance = null;

    /**
     * constructor for the sell state
     *
     * @param stockSelected     contains the current selected stock in the dropdown menu
     * @param amount            contains the amount of stocks the user wants to sell
     * @param amountError       contains the error message regarding the amount, either a format error
     *                          or the user tried to sell more stocks than owned
     * @param ownedStocks       list containing stocks currently owned by the user
     * @param sellSuccess       contains the success message displayed upon selling stocks.
     * @param ownedAmounts      contains amounts of stocks owned by the user
     * @param balance           contains the current balance of the user
     * @param sellAmounts       list containing the sell prices of the stocks owned by the user
     */
    public SellState(
            String stockSelected,
            String amount,
            String amountError,
            List<String> ownedStocks,
            String sellSuccess,
            List<Double> ownedAmounts,
            Double balance,
            List<Double> sellAmounts
    ) {
        this.amountError = amountError;
        this.amount = amount;
        this.stockSelected = stockSelected;
        this.ownedStocks = ownedStocks;
        this.sellSuccess = sellSuccess;
        this.ownedAmounts = ownedAmounts;
        this.balance = balance;
        this.sellAmounts = sellAmounts;
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
