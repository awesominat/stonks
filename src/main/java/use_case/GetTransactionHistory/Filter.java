package use_case.GetTransactionHistory;

import java.util.List;

public interface Filter {

    public List<List<String>> filter(
            List<List<String>> transactionsToFilter,
            String filterBy
    );

}
