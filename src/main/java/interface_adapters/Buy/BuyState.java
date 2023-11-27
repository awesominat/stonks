package interface_adapters.Buy;

import java.util.Map;

public class BuyState {
    private String amount = "";
    private String amountError = null;
    private String ticker = "";
    private Map<String, String> stockInfo;
    private Boolean renderNewInfo;
    private Boolean boughtStock;
    private Double currentlyHeld;
    private Double curBalance;

    public Boolean getRenderNewInfo() {
        return renderNewInfo;
    }

    public Double getCurBalance() {
        return curBalance;
    }

    public Boolean getBoughtStock() {
        return boughtStock;
    }

    public void setBoughtStock(Boolean boughtStock) {
        this.boughtStock = boughtStock;
    }

    public Double getCurrentlyHeld() {
        return currentlyHeld;
    }

    public void setCurrentlyHeld(Double currentlyHeld) {
        this.currentlyHeld = currentlyHeld;
    }

    public void setCurBalance(Double curBalance) {
        this.curBalance = curBalance;
    }

    public void setRenderNewInfo(Boolean renderNewInfo) {
        this.renderNewInfo = renderNewInfo;
    }

    public String getTicker() {
        return ticker;
    }

    public Map<String, String> getStockInfo() {
        return stockInfo;
    }

    public void setStockInfo(Map<String, String> stockInfo) {
        this.stockInfo = stockInfo;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
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

    public BuyState() {

    }

}
