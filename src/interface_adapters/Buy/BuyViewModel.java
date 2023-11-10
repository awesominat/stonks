package interface_adapters.Buy;

import interface_adapters.ViewModel;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class BuyViewModel extends ViewModel {
// TODO: Ricky
    public final String TITLE_LABEL = "Buy Stock";
    public final String AMOUNT_LABEL = "Enter amount";
    public final String PURCHASE_BUTTON_LABEL = "Purchase";
    public final String CANCEL_BUTTON_LABEL = "Cancel";

    private BuyState state = new BuyState();

    public BuyViewModel() {
        super("Buy Stock");
    }

    public void setState(BuyState state) {
        this.state = state;
    }

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    public void firePropertyChanged() {
        support.firePropertyChange("state", null, this.state);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public BuyState getState() {
        return state;
    }
}
