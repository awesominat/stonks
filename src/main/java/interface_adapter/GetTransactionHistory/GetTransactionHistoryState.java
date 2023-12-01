package interface_adapter.GetTransactionHistory;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class GetTransactionHistoryState {
    private List<List<String>> userRecord;

    public List<List<String>> getUserRecord() {
        return userRecord;
    }

    public void setUserRecord(List<List<String>> userRecord) {
        this.userRecord = userRecord;
    }

    public HashSet<String> allStocksInHistory() {
        HashSet<String> stocks = new HashSet<>();
        for (List<String> transaction: this.userRecord) {
            stocks.add(transaction.get(0));
        }
        return stocks;
    }


}
