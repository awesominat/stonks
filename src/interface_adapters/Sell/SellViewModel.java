package interface_adapters.Sell;

import interface_adapters.ViewModel;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class SellViewModel extends ViewModel {
    // TODO: Ricky
    public final String TITLE_LABEL = "Sell Stock";
    public final String AMOUNT_LABEL = "Enter amount";
    public final String PURCHASE_BUTTON_LABEL = "Sell";
    public final String CANCEL_BUTTON_LABEL = "Cancel";

    private SellState state = new SellState();

    public SellViewModel() {
        super("Sell Stock");
    }

    public void setState(SellState state) {
        this.state = state;
    }

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    public void firePropertyChanged() {
        support.firePropertyChange("state", null, this.state);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public SellState getState() {
        return state;
    }
}
