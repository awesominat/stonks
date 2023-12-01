package interface_adapter.GetTransactionHistory;

import java.util.ArrayList;
import java.util.List;

public class FilterCollection {
    List<Filter> filterType;
    public FilterCollection() {
        filterType = new ArrayList<>();
    }
    public void add(Filter filter){
        this.filterType.add(filter);
    }

    public List<List<String>> applyFilters(List<List<String>> toFilter, String[] filterArguments){
        System.out.println(filterType);
        if (filterArguments.length != filterType.size()) {
            System.out.println(filterArguments.length);
            System.out.println(filterType.size());
            throw new RuntimeException("Incorrect Number of Filter Arguments");
        }
        for (int i = 0; i < filterArguments.length; i++) {
            toFilter = filterType.get(i).filter(toFilter, filterArguments[i]);
        }
        return toFilter;
    }
}
