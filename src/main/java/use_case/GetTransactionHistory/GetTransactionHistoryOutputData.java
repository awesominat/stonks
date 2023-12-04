package use_case.GetTransactionHistory;

import java.util.HashMap;
import java.util.List;

public class GetTransactionHistoryOutputData {
    private List<List<String>> userRecord;

    /**
     * Constructs a new {@code GetTransactionHistoryOutputData} instance with the specified user record.
     *
     * @param userRecord A list of lists of strings representing transaction information.
     */
    public GetTransactionHistoryOutputData(List<List<String>> userRecord) {
        this.userRecord = userRecord;
    }

    /**
     * Gets the user record containing transaction information.
     *
     * @return A list of lists of strings representing transaction information.
     */
    public List<List<String>> getUserRecord() {
        return userRecord;
    }
}
