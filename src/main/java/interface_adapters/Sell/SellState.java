package interface_adapters.Sell;

import java.util.List;

public class SellState {
    private String stockSelected = null;
    private String amountError = null;
    private String amount = null;
    private String sellSuccess = null;
    private List<String> ownedStocks = null;

    public SellState(String stockSelected, String amount, String amountError, List<String> ownedStocks, String sellSuccess) {
        this.amountError = amountError;
        this.amount = amount;
        this.stockSelected = stockSelected;
        this.ownedStocks = ownedStocks;
        this.sellSuccess = sellSuccess;
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

    // Because of the previous copy constructor, the default constructor must be explicit. Hence overloading.
    public SellState() {

    }

}
