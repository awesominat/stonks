package entity;

import java.util.HashMap;
import java.util.List;
import java.beans.PropertyChangeSupport;
import java.beans.PropertyChangeListener;

public class CacheStockInformation {
    private HashMap<String, List<Double>> stockInformationMap;
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    public CacheStockInformation() {
        this.stockInformationMap = new HashMap<>();
    }

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

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
}
