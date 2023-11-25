package interface_adapters.GetTransactionHistory;

import java.util.HashMap;
import java.util.List;

public class GetTransactionHistoryState {
    private HashMap<String, List<List<Object>>> userRecord;

    public HashMap<String, List<List<Object>>> getUserRecord() {
        return userRecord;
    }

    public void setUserRecord(HashMap<String, List<List<Object>>> userRecord) {
        this.userRecord = userRecord;
    }

}
