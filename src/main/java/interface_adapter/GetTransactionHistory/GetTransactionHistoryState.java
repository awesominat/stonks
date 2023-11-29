package interface_adapter.GetTransactionHistory;

import java.util.HashMap;
import java.util.List;

public class GetTransactionHistoryState {
    private HashMap<String, List<List<String>>> userRecord;

    public HashMap<String, List<List<String>>> getUserRecord() {
        return userRecord;
    }

    public void setUserRecord(HashMap<String, List<List<String>>> userRecord) {
        this.userRecord = userRecord;
    }
}
