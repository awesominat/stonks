package use_cases.Buy;

public class BuyInputData {
    public Double amount;
    public String ticker;

    public BuyInputData(Double amount, String ticker) {
        this.amount = amount;
        this.ticker = ticker;
    }
    public BuyInputData(String ticker) {
        this.amount = null;
        this.ticker = ticker;
    }


    public Double getAmount() {
        return amount;
    }

    public String getTicker() {
        return ticker;
    }
}
