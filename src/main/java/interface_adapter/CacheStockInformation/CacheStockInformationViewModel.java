package interface_adapter.CacheStockInformation;

import interface_adapter.ViewModel;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class CacheStockInformationViewModel extends ViewModel {
    private CacheStockInformationState state = new CacheStockInformationState();

    public CacheStockInformationViewModel() {
        super("cache stock information");
    }

    public void setState(CacheStockInformationState state) {
        this.state = state;
    }

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    public void firePropertyChanged() {
        support.firePropertyChange("state", null, this.state);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public CacheStockInformationState getState() {
        return state;
    }
}
