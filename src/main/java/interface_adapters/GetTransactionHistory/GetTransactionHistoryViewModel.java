package interface_adapters.GetTransactionHistory;

import interface_adapters.Dashboard.DashboardState;
import interface_adapters.ViewModel;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class GetTransactionHistoryViewModel extends ViewModel {
    public final String TITLE_LABEL = "Transaction History";

    public final String STOCK_NAME = "Stock";

    public final String TRANSACTIONS = "Transaction Record";

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

    @Override
    public void firePropertyChanged() {
        support.firePropertyChange("state",null, this.state);
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
}
