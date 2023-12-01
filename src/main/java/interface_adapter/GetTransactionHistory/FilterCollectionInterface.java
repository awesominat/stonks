package interface_adapter.GetTransactionHistory;

import java.util.List;

public interface FilterCollectionInterface {
    public void add(Filter filter);
    public void applyFilters(
            List<List<String>> toFilter,
            String[] filterArguments
    );
}
