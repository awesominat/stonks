package interface_adapter.GetTransactionHistory;

import java.util.HashMap;
import java.util.List;

public class GetTransactionHistoryState {
    private List<List<String>> userRecord;

    public List<List<String>> getUserRecord() {
        return userRecord;
    }

    public void setUserRecord(List<List<String>> userRecord) {
        this.userRecord = userRecord;
    }
}
