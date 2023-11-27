package entities;

import java.util.HashMap;

public interface User {
    HashMap<String, TransactionHistory> getHistory();
    void addToPortfolio(String ticker, Double amount);
    void removeFromPortfolio(String ticker);
    Boolean isInPortfolio(String ticker);
    HashMap<String, Double> getPortfolio();
    Double getStockOwned(String ticker);
    boolean hasStock(String ticker);
    boolean hasEnough(Double amount);
    void addToHistory(String stockName, TransactionHistory entry);
    void spendBalance(Double difference);
    void setBalance(Double amount);
    void addBalance(Double addition);
    Double getBalance();
    void clearPortfolio();
}
