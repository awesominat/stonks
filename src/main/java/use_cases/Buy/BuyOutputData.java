package use_cases.Buy;

public class BuyOutputData {
    private String ticker;
    private Double newBalance;
    private Double amount;

    public BuyOutputData(String ticker, Double newBalance, Double amount) {
        this.ticker = ticker;
        this.newBalance = newBalance;
        this.amount = amount;
    }

    public Double getAmount() {
        return amount;
    }

    public String getTicker() {
        return ticker;
    }

    public Double getNewBalance() {
        return newBalance;
    }
}
