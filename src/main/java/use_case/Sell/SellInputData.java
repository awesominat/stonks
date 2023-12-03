package use_case.Sell;

public class SellInputData {
    public Double amount;
    public String ticker;
    public boolean executeTypeSell;

    /**
     * First constructor for the sell input data. Input data is constructed this way whenever
     * the user hits the sell button. i.e., the methods we want to run in the use case
     * correspond to a sell transaction.
     *
     * @param amount    amount of the stock to be sold
     * @param ticker    ticker of the stock to be sold
     */
    public SellInputData(Double amount, String ticker) {
        this.amount = amount;
        this.ticker = ticker;
        this.executeTypeSell = true;
    }

    /**
     * Second constructor. This is used when we do not want to sell stocks, but rather update
     * the stocks table on the sell view. Input data is constructed this way whenever the user
     * navigates to the sell page or whenever the cache refreshes.
     */
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
