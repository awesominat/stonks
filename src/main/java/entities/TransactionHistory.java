package entities;

import java.util.List;

public class TransactionHistory {
    private final Stock stock;
    private List<Transaction> transactions;

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public Stock getStock() {
        return stock;
    }

    public Transaction getMostRecentTransaction() {
        return transactions.get(transactions.size() - 1);
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    public TransactionHistory(Stock stock, List<Transaction> transactions) {
        this.stock = stock;
        this.transactions = transactions;
    }

    @Override
    public String toString() {
        System.out.println(transactions);
        return "This very cool person has had " + transactions.size() + " transactions with " + stock.toString();
    }
}
