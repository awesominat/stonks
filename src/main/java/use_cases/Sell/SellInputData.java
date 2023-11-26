package use_cases.Sell;

public class SellInputData {
    public Double amount;
    public String ticker;

    public SellInputData(Double amount, String ticker) {
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
