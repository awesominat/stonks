package interface_adapter.Sell;
import interface_adapter.ViewModel;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class SellViewModel extends ViewModel {
    public final String TITLE_LABEL = "Sell Stock";
    public final String AMOUNT_LABEL = "Enter amount";
    public final String PURCHASE_BUTTON_LABEL = "Sell";
    public final String CANCEL_BUTTON_LABEL = "Cancel";

    private SellState state = new SellState();
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    /**
     * Constructor for the sell view model
     */
    public SellViewModel() {
        super("sell");
    }

    public void setState(SellState state) {
        this.state = state;
    }

    public SellState getState() {
        return state;
    }

    /**
     * Fired whenever parts of the sell state are updated.
     * The view observed this class for property changes, updating itself when necessary.
     */
    public void firePropertyChanged() {
        support.firePropertyChange("state", null, this.state);
    }

    /**
     * Allows us to add the view as an observer to this class.
     *
     * @param listener  the view, which we add as an observer to this class
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
}
