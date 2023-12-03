package entity;

/**
 * Class containing information about a single stock in the users portfolio
 * attributes:
 * ticker       stock that is owned by the user
 * fullName     full name of the stock owned by the user
 * amount       amount of the stock owned by the user
 * price        price of the stock when most recently bought/sold by user
 */
public class PortfolioInformation {
    private String ticker;
    private String fullName;
    private Double amount;
    private Double price;

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getPrice() {
        return this.price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

}
