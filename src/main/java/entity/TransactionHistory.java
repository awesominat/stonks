package entity;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class TransactionHistory implements Iterable<Transaction> {
    private Stock stock;
    private List<Transaction> transactions;

    class TransactionIter implements Iterator<Transaction> {
        int idx = 0;

        public boolean hasNext() {
            return idx < transactions.size();
        }

        public Transaction next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return transactions.get(idx++);
        }
    }

    @Override
    public Iterator<Transaction> iterator() {
        return new TransactionIter();
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
    public void setStock(Stock stock) {
        this.stock = stock;
    }
    @Override
    public String toString() {
        System.out.println(transactions);
        return "This very cool person has had " + transactions.size() + " transactions with " + stock.toString();
    }
}
