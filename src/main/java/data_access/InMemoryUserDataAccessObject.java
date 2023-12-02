package data_access;


import entity.CommonUser;
import entity.TransactionHistory;
import entity.User;
import use_case.Buy.BuyDataAccessInterface;
import use_case.CacheStockInformation.CacheStockInformationDataAccessInterface;
import use_case.Dashboard.DashboardDataAccessInterface;
import use_case.GetTransactionHistory.GetTransactionHistoryDataAccessInterface;
import use_case.ResetBalance.ResetBalanceDataAccessInterface;
import use_case.Sell.SellDataAccessInterface;

import java.util.HashMap;

public class InMemoryUserDataAccessObject implements
        BuyDataAccessInterface,
        SellDataAccessInterface,
        ResetBalanceDataAccessInterface,
        DashboardDataAccessInterface,
        GetTransactionHistoryDataAccessInterface,
        CacheStockInformationDataAccessInterface {

    private final User testUser = new CommonUser();

    HashMap<String, Double> portfolio;

    HashMap<String, TransactionHistory> history;

    Double balance;

    @Override
    public void save() {
    }

    @Override
    public User get() {
        return this.testUser;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Double getBalance() {
        return balance;
    }

    public HashMap<String, Double> getPortfolio() {
        return portfolio;
    }

    public HashMap<String, TransactionHistory> getHistory() {
        return history;
    }

    public void setHistory(HashMap<String, TransactionHistory> history) {
        this.history = history;
    }

    public void setPortfolio(HashMap<String, Double> portfolio) {
        this.portfolio = portfolio;
    }
}
