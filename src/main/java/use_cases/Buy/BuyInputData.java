package use_cases.Buy;

public class BuyInputData {
    public Double amount;
    public String ticker;
    public String username;

    public BuyInputData(Double amount, String ticker, String username) {
        this.amount = amount;
        this.ticker = ticker;
        this.username = username;
    }

    public Double getAmount() {
        return amount;
    }

    public String getTicker() {
        return ticker;
    }

    public String getUsername() {
        return username;
    }
}
