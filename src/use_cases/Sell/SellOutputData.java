package use_cases.Sell;

public class SellOutputData {
    public Double amount;
    public String ticker;

    public SellOutputData(Double amount, String ticker) {
        this.amount = amount;
        this.ticker = ticker;
    }

    public Double getAmount() {
        return amount;
    }

    public String getTicker() {
        return ticker;
    }
}
