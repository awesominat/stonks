package entity;

import java.util.List;

public class Stock {
    private Double lastSeenPrice;
    private List<PricePoint> priceHistory;
    final private String fullName;
    final private String ticker;

    /**
     * Contains common information about a specific stock.
     *
     * @param lastSeenPrice     last seen price of the stock from the last API call
     * @param fullName          full name of the stock
     * @param ticker            ticker name of the stock
     */
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
