package interface_adapter.GetTransactionHistory;

import java.util.List;

public interface Filter {

    public void filter(
            List<List<String>> transactionsToFilter,
            String filterBy
    );

}
