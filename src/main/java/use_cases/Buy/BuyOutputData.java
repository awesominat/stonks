package use_cases.Buy;

public class BuyOutputData {
    public Double amount;
    public String ticker;

    public BuyOutputData(Double amount, String ticker) {
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
