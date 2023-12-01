package interface_adapter.GetTransactionHistory;

import java.util.ArrayList;
import java.util.List;

public class FilterByStockName implements Filter{
    @Override
    public void filter(
            List<List<String>> transactionsToFilter,
            String stockName
    ) {
        if (!stockName.equals("No filter")) {
            for (int i = transactionsToFilter.size() - 1; i>=0; i--) {
                if (!transactionsToFilter.get(i).get(0).equals(stockName)) {
                    transactionsToFilter.remove(i);
                }
            }
        }
    }
}
