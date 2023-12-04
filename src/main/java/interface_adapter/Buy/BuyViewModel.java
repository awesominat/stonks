package interface_adapter.Buy;

import interface_adapter.ViewModel;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * ViewModel for BuyView
 */
public class BuyViewModel extends ViewModel {

    /**
     * Labels for the view
     */
    public final String TITLE_LABEL = "Buy Stock";
    public final String AMOUNT_LABEL = "Enter amount";
    public final String BACK_BUTTON_LABEL = "Back";
    public final String SEARCH_BUTTON_LABEL = "Search";
    public final String PURCHASE_BUTTON_LABEL = "Purchase";
    public final String CANCEL_BUTTON_LABEL = "Cancel";
    public final String JUST_BOUGHT_MESSAGE = "Congratulations, you have just bought %s stock(s) of %s!";

    private BuyState state = new BuyState();

    /**
     * Constructor
     */
    public BuyViewModel() {
        super("buy");
    }

    /**
     * Set the state of the view model
     * @param state the new state
     */
    public void setState(BuyState state) {
        this.state = state;
    }

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    /**
     * Fires a property change event
     */
    public void firePropertyChanged() {
        support.firePropertyChange("state", null, this.state);
    }

    /**
     * Adds a property change listener
     * @param listener the listener to add
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    /**
     * @return the current state of the view model
     */
    public BuyState getState() {
        return state;
    }

}
