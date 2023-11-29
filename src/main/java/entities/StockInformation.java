package entities;

public class StockInformation {
    private Double currentPrice;
    private Double priceChange;
    private Double percentChange;

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
