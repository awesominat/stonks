package use_cases.Sell;

public class SellInputData {
    public Double amount;
    public String ticker;
    public String username;

    public SellInputData(Double amount, String ticker, String username) {
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
