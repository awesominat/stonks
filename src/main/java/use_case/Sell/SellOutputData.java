package use_case.Sell;

import java.util.List;

public class SellOutputData {
    private Double amount;
    private String ticker;
    private boolean executeTypeSell;
    private List<String> ownedStocks;
    private List<Double> ownedAmounts;
    private Double balance;

    /**
     * The first constructor which is used when the execution type is sell.
     * Contains amounts and ticker to be sold so that a success popup can be displayed
     *
     * @param amount    amount of stocks to be sold
     * @param ticker    ticker of stock to be sold
     */
    public SellOutputData(Double amount, String ticker) {
        this.amount = amount;
        this.ticker = ticker;
        this.executeTypeSell = true;
    }

    /**
     * The second constructor which is used when the execution type is refresh.
     * Contains stocks owned, amounts of those stocks and the current user balance
     * so that these can be updated on the sell view.
     *
     * More importantly, this sets the attribute executeTypeSell to be false.
     * This is checked for in the presenter, which can then also use the dashboard state to grab
     * current, up-to-date stock prices to the displayed on the sell view.
     *
     * @param ownedStocks   list of currently owned stocks
     * @param ownedAmounts  parallel list of amounts of currently owned stocks
     * @param balance       current user balance
     */
    public SellOutputData(
            List<String> ownedStocks,
            List<Double> ownedAmounts,
            Double balance
    ) {
        this.ownedStocks = ownedStocks;
        this.ownedAmounts = ownedAmounts;
        this.balance = balance;
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

    public List<Double> getOwnedAmounts() {
        return ownedAmounts;
    }

    public Double getBalance() {
        return balance;
    }

    public List<String> getOwnedStocks() {
        return ownedStocks;
    }
}
