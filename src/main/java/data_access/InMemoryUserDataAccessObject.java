package data_access;


import entities.CommonUser;
import entities.TransactionHistory;
import entities.User;
import use_cases.Buy.BuyDataAccessInterface;
import use_cases.Dashboard.DashboardDataAccessInterface;
import use_cases.GetNews.GetNewsDataAccessInterface;
import use_cases.GetTransactionHistory.GetTransactionHistoryDataAccessInterface;
import use_cases.ResetBalance.ResetBalanceDataAccessInterface;
import use_cases.Sell.SellDataAccessInterface;

import java.util.HashMap;

public class InMemoryUserDataAccessObject implements
        BuyDataAccessInterface,
        SellDataAccessInterface,
        ResetBalanceDataAccessInterface,
        DashboardDataAccessInterface,
        GetTransactionHistoryDataAccessInterface,
        GetNewsDataAccessInterface {

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
