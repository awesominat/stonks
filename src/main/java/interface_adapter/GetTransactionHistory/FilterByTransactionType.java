package interface_adapter.GetTransactionHistory;

import java.util.ArrayList;
import java.util.List;

public class FilterByTransactionType implements Filter{
    @Override
    public void filter(
            List<List<String>> transactionsToFilter,
            String stockType
    ) {
        if (!stockType.equals("No filter")) {
            for (int i = transactionsToFilter.size() - 1; i>=0; i--) {
                if (!transactionsToFilter.get(i).get(1).equals(stockType)) {
                    transactionsToFilter.remove(i);
                }
            }
        }
    }
}
