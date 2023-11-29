package interface_adapter.Buy;

import interface_adapter.ViewModel;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class BuyViewModel extends ViewModel {
    public final String TITLE_LABEL = "Buy Stock";
    public final String AMOUNT_LABEL = "Enter amount";
    public final String BACK_BUTTON_LABEL = "Back";
    public final String SEARCH_BUTTON_LABEL = "Search";
    public final String PURCHASE_BUTTON_LABEL = "Purchase";
    public final String CANCEL_BUTTON_LABEL = "Cancel";
    public final String JUST_BOUGHT_MESSAGE = "Congratulations, you have just bought %s stock(s) of %s!";

    private BuyState state = new BuyState();

    public BuyViewModel() {
        super("buy");
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
