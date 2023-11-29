package use_case.Sell;

import java.util.List;

public class SellOutputData {
    public Double amount;
    public String ticker;
    public boolean executeTypeSell;
    public List<String> ownedStocks;
    public List<Double> ownedAmounts;
    public Double balance;

    public SellOutputData(Double amount, String ticker) {
        this.amount = amount;
        this.ticker = ticker;
        this.executeTypeSell = true;
    }

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
