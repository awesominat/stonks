package entity;

public class StockInformation {
    private Double currentPrice;
    private Double priceChange;
    private Double percentChange;

    /**
     * Constructor for stock information. Contains all information that needs to be shown in the
     * currently owned stocks table on the dashboard.
     * All these attributes are grabbed from the API. There is no stock/ticker attribute because
     * instances of this class are always stored in a HashMap with the key being the ticker.
     *
     * @param currentPrice      current price of the stock
     * @param priceChange       price change of the stock
     * @param percentChange     percent change of the stock
     */
    public StockInformation(
            Double currentPrice,
            Double priceChange,
            Double percentChange
    ) {
        this.currentPrice = currentPrice;
        this.priceChange = priceChange;
        this.percentChange = percentChange;
    }

    public void setPriceChange(Double priceChange) {
        this.priceChange = priceChange;
    }

    public Double getPriceChange() {
        return priceChange;
    }

    public void setPercentChange(Double percentChange) {
        this.percentChange = percentChange;
    }

    public Double getPercentChange() {
        return percentChange;
    }

    public Double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(Double currentPrice) {
        this.currentPrice = currentPrice;
    }
}
