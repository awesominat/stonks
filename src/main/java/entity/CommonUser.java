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

    /**
     * Used to check if the user has enough money to buy a certain stock
     * @param amount    amount the user needs for the transaction to be successful
     * @return          boolean representing whether the user has enough for a successful transaction
     */
    public boolean hasEnough(Double amount) {
        return this.balance >= amount;
    }

    /**
     * Spends the balance on buying a stock
     *
     * @param difference    amount to spend
     */
    public void spendBalance(Double difference) {
        this.balance -= difference;
    }

    /**
     * Adds to balance after selling a stock
     *
     * @param addition      amount to add back to user balance after a sell is done
     */
    public void addBalance(Double addition) {
        this.balance += addition;
    }

    @Override
    public void setBalance(Double balance) {
        this.balance = balance;
    }

    /**
     * Constructor for CommonUser
     *
     * @param portfolio     portfolio of the user containing stocks owned and their amounts
     * @param history       transaction history of the user
     * @param balance       current balance of the user
     */
    public CommonUser(
            HashMap<String, Double> portfolio,
            HashMap<String, TransactionHistory> history,
            Double balance
    ) {
        this.portfolio = portfolio;
        this.history = history;
        this.balance = balance;
    }

    /**
     * Constructor with no arguments that creates a new user with an empty
     * portfolio and a balance of 10000
     */
    public CommonUser() {
        this.balance = User.DEFAULT_BALANCE;
        this.portfolio = new HashMap<>();
        this.history = new HashMap<>();
    }

    /**
     * Returns the transaction history of the user
     *
     * @return      returns a map from ticker strings to a TransactionHistory object
     *              with all transactions associated with that ticker
     */
    @Override
    public HashMap<String, TransactionHistory> getHistory() {
        return history;
    }

    @Override
    public Double getBalance() {
        return balance;
    }

    /**
     * Empties the user portfolio. Used in the Reset use case.
     */
    @Override
    public void clearPortfolio() {
        this.portfolio.clear();
    }

}
