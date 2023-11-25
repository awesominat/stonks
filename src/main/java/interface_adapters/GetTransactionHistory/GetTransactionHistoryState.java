package interface_adapters.GetTransactionHistory;

import java.util.List;

public class GetTransactionHistoryState {
    private List<String> stockFullNameList;

    private List<List<Object>> listOfTransactionFacts;

    // Check with Zain or Gursi
//    public GetTransactionHistoryState(List<String> stockFullNameList, List<List<Object>> listOfTransactionFacts){
//        this.listOfTransactionFacts = listOfTransactionFacts;
//        this.stockFullNameList = stockFullNameList;
//    }
    public List<List<Object>> getListOfTransactionFacts() {
        return listOfTransactionFacts;
    }

    public List<String> getStockFullNameList() {
        return stockFullNameList;
    }

    public void setListOfTransactionFacts(List<List<Object>> listOfTransactionFacts) {
        this.listOfTransactionFacts = listOfTransactionFacts;
    }

    public void setStockFullNameList(List<String> stockFullNameList) {
        this.stockFullNameList = stockFullNameList;
    }
}
