package interface_adapter.Buy;

import java.util.Map;

/**
 * Stores the state of the BuyView and BuyViewModel.
 */
public class BuyState {
    private String amount = "";
    private String amountError = null;
    private String ticker = "";
    private Map<String, String> stockInfo;
    private Boolean renderNewInfo;
    private Boolean boughtStock;
    private Double currentlyHeld;
    private Double curBalance;

    /**
     * @return true if the user has just bought a stock or made a search, false otherwise
     */
    public Boolean getRenderNewInfo() {
        return renderNewInfo;
    }

    /**
     * @return the current balance of the user
     */
    public Double getCurBalance() {
        return curBalance;
    }

    /**
     * @return true if the user has just bought a stock, false otherwise
     */
    public Boolean getBoughtStock() {
        return boughtStock;
    }

    /**
     * @param boughtStock true if the user has just bought a stock, false otherwise
     */
    public void setBoughtStock(Boolean boughtStock) {
        this.boughtStock = boughtStock;
    }

    /**
     * @return the amount of the stock the user currently holds
     */
    public Double getCurrentlyHeld() {
        return currentlyHeld;
    }

    /**
     * @param currentlyHeld the amount of the stock the user currently holds
     */
    public void setCurrentlyHeld(Double currentlyHeld) {
        this.currentlyHeld = currentlyHeld;
    }

    /**
     * @param curBalance the current balance of the user
     */
    public void setCurBalance(Double curBalance) {
        this.curBalance = curBalance;
    }

    /**
     * @param renderNewInfo true if the user has just bought a stock or made a search, false otherwise
     */
    public void setRenderNewInfo(Boolean renderNewInfo) {
        this.renderNewInfo = renderNewInfo;
    }

    /**
     * @return the ticker of the stock the user is searching for or purchasing
     */
    public String getTicker() {
        return ticker;
    }

    /**
     * @return information about the stock the user is searching for or purchasing
     */
    public Map<String, String> getStockInfo() {
        return stockInfo;
    }

    /**
     * @param stockInfo information about the stock the user is searching for or purchasing
     */
    public void setStockInfo(Map<String, String> stockInfo) {
        this.stockInfo = stockInfo;
    }

    /**
     * @param ticker the ticker of the stock the user is searching for or purchasing
     */
    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    /**
     * @return the amount of stock the user is purchasing
     */
    public String getAmount() {
        return amount;
    }

    /**
     * @param amount the amount of stock the user is purchasing
     */
    public void setAmount(String amount) {
        this.amount = amount;
    }

    /**
     * @return an error message if the user has entered an invalid amount, null otherwise
     */
    public String getAmountError() {
        return amountError;
    }

    /**
     * @param amountError an error message if the user has entered an invalid amount, null otherwise
     */
    public void setAmountError(String amountError) {
        this.amountError = amountError;
    }

    /**
     * Default constructor.
     */
    public BuyState() {}

}
