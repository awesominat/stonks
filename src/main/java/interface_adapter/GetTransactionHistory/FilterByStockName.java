package interface_adapter.GetTransactionHistory;

import java.util.ArrayList;
import java.util.List;

public class FilterByStockName implements Filter{
    @Override
    public List<List<String>> filter(
            List<List<String>> transactionsToFilter,
            String stockName
    ) {
        if (!stockName.equals("No filter")) {
            List<List<String>> filteredTransactions = new ArrayList<>();
            for (List<String> transaction : transactionsToFilter) {
                if (transaction.get(0).equals(stockName)) {
                    filteredTransactions.add(transaction);
                }
            }
            return filteredTransactions;
        }
        return transactionsToFilter;
    }
}
