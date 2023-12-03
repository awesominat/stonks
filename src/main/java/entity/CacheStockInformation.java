package entity;

import java.util.HashMap;
import java.util.List;
import java.beans.PropertyChangeSupport;
import java.beans.PropertyChangeListener;

public class CacheStockInformation {
    private HashMap<String, List<Double>> stockInformationMap;
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    /**
     * Initializes with an empty hashmap. This is not a problem, since a refresh is always
     * called right at the start of execution, which replaces this hashmap with a filled one
     * if the user owns any stocks.
     */
    public CacheStockInformation() {
        this.stockInformationMap = new HashMap<>();
    }

    /**
     * Updates the stockInformationMap stored in the Cache with a new information map.
     * Also fires property changed to alert the observer that a change has occurred.
     *
     * @param stockInformationMap   Maps a ticker name to a list
     *                              containing [current price, price change, percent change]
     */
    public void setStockInformationMap(HashMap<String, List<Double>> stockInformationMap) {
        this.stockInformationMap = stockInformationMap;
        firePropertyChanged();
    }

    public HashMap<String, List<Double>> getStockInformationMap() {
        return stockInformationMap;
    }

    private void firePropertyChanged() {
        support.firePropertyChange("stockInformationMap", null, this);
    }

    /**
     * Allows us to add any observers to this object. This can allow us to make multiple interactors
     * observe the cache if necessary.
     *
     * @param listener  Observer to add
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
}
