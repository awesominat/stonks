package interface_adapter.GetTransactionHistory;

import java.util.ArrayList;
import java.util.List;

/**
 * The {@code FilterByStockName} class implements the {@code Filter} interface
 * and provides a method to filter a list of transactions based on a specified
 * stock name. It removes transactions from the list that do not match the specified
 * stock name, if filtering is applied.
 */
public class FilterByStockName implements Filter{
    /**
     * Filters a list of transactions based on the specified stock name.
     * If the stock name is not equal to "No filter," the method iterates through
     * the list of transactions and removes those that do not match the specified
     * stock name.
     *
     * @param transactionsToFilter the list of transactions to be filtered
     * @param stockName            the stock name used for filtering
     */
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
