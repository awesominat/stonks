package use_cases.GetTransactionHistory;

import entities.TransactionHistory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetTransactionHistoryOutputData {
    private List<String> stocks;
    private List<List<Object>> listOfTransactionFacts;

    public GetTransactionHistoryOutputData(List<String> stocks, List<List<Object>> listOfTransactionFacts) {
        this.stocks = stocks;
        this.listOfTransactionFacts = listOfTransactionFacts;
    }

    public void setStocks(List<String> stocks) {
        this.stocks = stocks;
    }

    public List<String> getStocks() {
        return this.stocks;
    }

    public void setListOfTransactionFacts(List<List<Object>> listOfTransactionFacts) {
        this.listOfTransactionFacts = listOfTransactionFacts;
    }

    public List<List<Object>> getListOfTransactionFacts() {
        return this.listOfTransactionFacts;
    }
}
