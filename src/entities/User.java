package entities;

import java.util.HashMap;

public interface User {
    String getUsername();
    String getPassword();
    HashMap<String, TransactionHistory> getHistory();
    void updatePortfolio(String ticker, Double amount);
    HashMap<String, Double> getPortfolio();
    Double getStockOwned(String ticker);
    boolean hasStock(String ticker);
    boolean hasEnough(Double amount);
    void addToHistory(String stockName, TransactionHistory entry);
    void spendBalance(Double difference);
    void addBalance(Double addition);
    Double getBalance();
}
