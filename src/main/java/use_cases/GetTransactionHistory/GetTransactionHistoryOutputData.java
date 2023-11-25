package use_cases.GetTransactionHistory;

import java.util.HashMap;
import java.util.List;

public class GetTransactionHistoryOutputData {
    private HashMap<String, List<List<Object>>> userRecord;

    public GetTransactionHistoryOutputData(HashMap<String, List<List<Object>>> userRecord) {
        this.userRecord = userRecord;
    }

    public void setUserRecord(HashMap<String, List<List<Object>>> userRecord) {
        this.userRecord = userRecord;
    }

    public HashMap<String, List<List<Object>>> getUserRecord() {
        return userRecord;
    }
}
