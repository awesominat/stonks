package entity;

import java.util.HashMap;

public interface User {
    Double DEFAULT_BALANCE = 10000.0;
    HashMap<String, TransactionHistory> getHistory();
    void addToPortfolio(String ticker, Double amount);
    void removeFromPortfolio(String ticker);
    Boolean isInPortfolio(String ticker);
    HashMap<String, Double> getPortfolio();
    Double getStockOwned(String ticker);
    boolean hasEnough(Double amount);
    void spendBalance(Double difference);
    void setBalance(Double amount);
    void addBalance(Double addition);
    Double getBalance();
    void clearPortfolio();
}
