package interface_adapter.GetTransactionHistory;

import java.util.ArrayList;
import java.util.List;

public class FilterCollection implements FilterCollectionInterface {
    List<Filter> filterType;

    /**
     * Constructor that creates an empty collection of filters
     */
    public FilterCollection() {
        filterType = new ArrayList<>();
    }

    /**
     * Adds a filter to the filter collection
     *
     * @param filter    filter to add to filter collection
     */
    public void add(Filter filter){
        this.filterType.add(filter);
    }

    /**
     * Applies the filter to a list of transactions
     *
     * @param toFilter          the list to be filtered
     * @param filterArguments   the arguments of the filters. The length of this must
     *                          match the number and order of filters in our filter collection.
     */
    public void applyFilters(List<List<String>> toFilter, String[] filterArguments){
        if (filterArguments.length != filterType.size()) {
            throw new RuntimeException("Incorrect Number of Filter Arguments");
        }
        for (int i = 0; i < filterArguments.length; i++) {
            filterType.get(i).filter(toFilter, filterArguments[i]);
        }
    }
}
