package entities;

import java.util.List;

public class Stock {
    private Double lastSeenPrice;
    private List<PricePoint> priceHistory;
    final private String fullName;
    final private String ticker;

    public Stock(Double lastSeenPrice, String fullName, String ticker) {
        this.lastSeenPrice = lastSeenPrice;
        this.fullName = fullName;
        this.ticker = ticker;
    }

    public Double getLastSeenPrice() {
        return lastSeenPrice;
    }

    public void setLastSeenPrice(Double lastSeenPrice) {
        this.lastSeenPrice = lastSeenPrice;
    }

    public List<PricePoint> getPriceHistory() {
        return priceHistory;
    }

    public void setPriceHistory(List<PricePoint> priceHistory) {
        this.priceHistory = priceHistory;
    }

    public String getFullName() {
        return fullName;
    }

    public String getTicker() {
        return ticker;
    }

    @Override
    public String toString() {
        return fullName;
    }
}
