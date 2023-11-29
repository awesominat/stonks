package entity;

import java.time.LocalDate;

public class PricePoint {
    private LocalDate timeStamp;
    private Double price;

    public PricePoint(LocalDate timeStamp, Double price) {
        this.price = price;
        this.timeStamp = timeStamp;
    }

    public LocalDate getTimeStamp() {
        return timeStamp;
    }

    public Double getPrice() {
        return price;
    }

    public void setTimeStamp(LocalDate timeStamp) {
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
