package entities;

import java.util.HashMap;

public class CommonUser implements User {

    final private String username;
    final private String password;
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


        public void updatePortfolio(String ticker, Double amount) {
        if (amount == 0) {
            portfolio.remove(ticker);
            return;
        }
        portfolio.put(ticker, amount);
    }
    public Double getStockOwned(String ticker) {
        return portfolio.get(ticker);
    }
    public boolean hasStock(String ticker) {
        return portfolio.containsKey(ticker);
    }

    public void addToHistory(String stockName, TransactionHistory entry) {
        history.put(stockName, entry);
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

    public CommonUser(String username, String password, HashMap<String, Double> portfolio, HashMap<String,
            TransactionHistory> history, Double balance) {
        this.username = username;
        this.password = password;
        this.portfolio = portfolio;
        this.history = history;
        this.balance = balance;
    }

    public CommonUser(String username, String password) {
        this.username = username;
        this.password = password;
        this.balance = 10000.0;
        this.portfolio = new HashMap<>();
        this.history = new HashMap<>();
    }

    @Override
    public HashMap<String, TransactionHistory> getHistory() {
        return history;
    }

    @Override
    public Double getBalance() {
        return balance;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }
}
