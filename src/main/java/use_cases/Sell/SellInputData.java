package use_cases.Sell;

public class SellInputData {
    public Double amount;
    public String ticker;
    public boolean amountFormatError = false;

    public SellInputData(Double amount, String ticker) {
        this.amount = amount;
        this.ticker = ticker;
    }

    public SellInputData(String ticker) {
        this.amount = null;
        this.ticker = ticker;
        this.amountFormatError = true;
    }

    public Double getAmount() {
        return amount;
    }

    public String getTicker() {
        return ticker;
    }

    public boolean isAmountFormatError() {
        return amountFormatError;
    }
}
