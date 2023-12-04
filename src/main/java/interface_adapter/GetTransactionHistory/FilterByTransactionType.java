package interface_adapter.GetTransactionHistory;

import java.util.ArrayList;
import java.util.List;

/**
 * The {@code FilterByTransactionType} class implements the {@code Filter} interface
 * and provides a method to filter a list of transactions based on a specified
 * stock type. It removes transactions from the list that do not match the specified
 * stock type, if filtering is applied.
 */
public class FilterByTransactionType implements Filter{

    /**
     * Filters a list of transactions based on the specified stock type.
     * If the transaction type is not equal to "No filter," the method iterates through
     * the list of transactions and removes those that do not match the specified
     * stock type.
     *
     * @param transactionsToFilter the list of transactions to be filtered
     * @param stockType            the transaction type used for filtering
     */
    @Override
    public void filter(
            List<List<String>> transactionsToFilter,
            String stockType
    ) {
        // The filter is only applied if the argument is not "No filter".
        if (!stockType.equals("No filter")) {
            for (int i = transactionsToFilter.size() - 1; i>=0; i--) {
                if (!transactionsToFilter.get(i).get(1).equals(stockType)) {
                    transactionsToFilter.remove(i);
                }
            }
        }
    }
}
