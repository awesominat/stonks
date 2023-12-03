package entity;

import java.time.LocalDateTime;

public class PricePoint {
    private LocalDateTime timeStamp;
    private Double price;

    /**
     * Constructor for a PricePoint object
     *
     * @param timeStamp     the timestamp at which this price point was generated
     * @param price         the price of a specific stock
     */
    public PricePoint(LocalDateTime timeStamp, Double price) {
        this.price = price;
        this.timeStamp = timeStamp;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public Double getPrice() {
        return price;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "(" + getTimeStamp().toString() + ": price is " + price + ")";
    }
}
