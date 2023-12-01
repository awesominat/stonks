package interface_adapter.GetTransactionHistory;

import java.util.ArrayList;
import java.util.List;

public class FilterCollection implements FilterCollectionInterface {
    List<Filter> filterType;
    public FilterCollection() {
        filterType = new ArrayList<>();
    }
    public void add(Filter filter){
        this.filterType.add(filter);
    }

    public void applyFilters(List<List<String>> toFilter, String[] filterArguments){
        if (filterArguments.length != filterType.size()) {
            throw new RuntimeException("Incorrect Number of Filter Arguments");
        }
        for (int i = 0; i < filterArguments.length; i++) {
            filterType.get(i).filter(toFilter, filterArguments[i]);
        }
    }
}
