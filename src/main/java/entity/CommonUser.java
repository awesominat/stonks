package entity;

import java.util.HashMap;

public class CommonUser implements User {

    private final HashMap<String, Double> portfolio;
    private final HashMap<String, TransactionHistory> history;
    private Double balance;

    @Override
    public HashMap<String, Double> getPortfolio() {
        return portfolio;
    }

    public void addToPortfolio(String ticker, Double amount) {
        portfolio.put(ticker, amount);
    }
    public void removeFromPortfolio(String ticker) {
        portfolio.remove(ticker);
    }

    @Override
    public Boolean isInPortfolio(String ticker) {
        return portfolio.containsKey(ticker);
    }

    public Double getStockOwned(String ticker) {
        return portfolio.get(ticker);
    }

    public boolean hasEnough(Double amount) {
        return this.balance >= amount;
    }
    public void spendBalance(Double difference) {
        this.balance -= difference;
    }
    public void addBalance(Double addition) {
        this.balance += addition;
    }

    @Override
    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public CommonUser(
            HashMap<String, Double> portfolio,
            HashMap<String, TransactionHistory> history,
            Double balance
    ) {
        this.portfolio = portfolio;
        this.history = history;
        this.balance = balance;
    }

    public CommonUser() {
        this.balance = 10000.0;
        this.portfolio = new HashMap<>();
        this.history = new HashMap<>();
    }

    @Override
    public HashMap<String, TransactionHistory> getHistory() {
        return history;
    }
    // String is TICKER

    @Override
    public Double getBalance() {
        return balance;
    }

    @Override
    public void clearPortfolio() {
        this.portfolio.clear();
    }

}
