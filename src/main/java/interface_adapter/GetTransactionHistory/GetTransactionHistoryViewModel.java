package interface_adapter.GetTransactionHistory;

import interface_adapter.ViewModel;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class GetTransactionHistoryViewModel extends ViewModel {
    public final String TITLE_LABEL = "Transaction History";

    public final String STOCK_NAME = "Stock";

    public final String TRANSACTIONS = "Transaction Record";

    public final String BACK_BUTTON_LABEL = "Back";

    private GetTransactionHistoryState state = new GetTransactionHistoryState();

    public GetTransactionHistoryViewModel() {
        super("transactionHistory");
    }

    public void setState(GetTransactionHistoryState state) {
        this.state = state;
    }

    public GetTransactionHistoryState getState(){
        return this.state;
    }

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    /**
     * Notifies registered property change listeners that the state of the
     * {@code GetTransactionHistoryViewModel} has been updated. This method should
     * be called whenever a change occurs in the underlying state that requires
     * observers to be notified.
     */
    public void firePropertyChanged() {
        support.firePropertyChange("state",null, this.state);
    }

    /**
     * Adds a {@code PropertyChangeListener} to the list of listeners.
     * The listener will be notified whenever the value of the "state" property
     * changes in the {@code GetTransactionHistoryViewModel}.
     *
     * @param listener the {@code PropertyChangeListener} to be added
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
}
