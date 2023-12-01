package use_case.GetTransactionHistory;

import java.util.HashMap;
import java.util.List;

public class GetTransactionHistoryOutputData {
    private List<List<String>> userRecord;

    public GetTransactionHistoryOutputData(List<List<String>> userRecord) {
        this.userRecord = userRecord;
    }

    public void setUserRecord(List<List<String>> userRecord) {
        this.userRecord = userRecord;
    }

    public List<List<String>> getUserRecord() {
        return userRecord;
    }
}
