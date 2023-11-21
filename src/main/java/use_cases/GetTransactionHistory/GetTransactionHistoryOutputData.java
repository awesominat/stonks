package use_cases.GetTransactionHistory;

import entities.TransactionHistory;

import java.util.HashMap;

public class GetTransactionHistoryOutputData {
    private HashMap<String, TransactionHistory> transactionHistory;

    public GetTransactionHistoryOutputData(HashMap<String, TransactionHistory> transactionHistory) {
        this.transactionHistory = transactionHistory;
    }

    public HashMap<String, TransactionHistory> getTransactionHistory() {
        return this.transactionHistory;
    }

    public void setTransactionHistory(HashMap<String, TransactionHistory> transactionHistory) {
        this.transactionHistory = transactionHistory;
    }
}
