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

    /**
     * Retrieves a {@code HashSet} containing all unique stock names present
     * in the user's transaction history.
     *
     * @return a {@code HashSet<String>} containing all unique stock names
     *         in the transaction history
     */
    public HashSet<String> allStocksInHistory() {
        HashSet<String> stocks = new HashSet<>();
        for (List<String> transaction: this.userRecord) {
            stocks.add(transaction.get(0));
        }
        return stocks;
    }

    /**
     * Retrieves a {@code HashSet} containing all unique transaction types present
     * in the user's transaction history.
     *
     * @return a {@code HashSet<String>} containing all unique transaction types
     *         in the transaction history
     */
    public HashSet<String> allTypesInHistory() {
        HashSet<String> stockTypes = new HashSet<>();
        for (List<String> transaction: this.userRecord) {
            stockTypes.add(transaction.get(1));
        }
        return stockTypes;
    }
}
