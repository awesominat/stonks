package use_case.Sell;

public class SellInputData {
    public Double amount;
    public String ticker;
    public boolean executeTypeSell;

    public SellInputData(Double amount, String ticker) {
        this.amount = amount;
        this.ticker = ticker;
        this.executeTypeSell = true;
    }

    public SellInputData() {
        this.executeTypeSell = false;
    }

    public Double getAmount() {
        return amount;
    }

    public String getTicker() {
        return ticker;
    }

    public boolean isExecuteTypeSell() {
        return executeTypeSell;
    }
}
