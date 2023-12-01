package interface_adapter.GetTransactionHistory;

import java.util.ArrayList;
import java.util.List;

public class FilterByTransactionType implements Filter{
    @Override
    public List<List<String>> filter(
            List<List<String>> transactionsToFilter,
            String stockType
    ) {
        List<List<String>> filteredTransactions = new ArrayList<>();
        for (List<String> transaction: transactionsToFilter){
            if (transaction.get(1).equals(stockType)){
                filteredTransactions.add(transaction);
            }
        }

        return filteredTransactions;
    }
}
