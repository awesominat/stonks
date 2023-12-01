package interface_adapter.Dashboard;

import interface_adapter.ViewModel;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class DashboardViewModel extends ViewModel {
    public final String PURCHASE_BUTTON_LABEL = "Buy";
    public final String SELL_BUTTON_LABEL = "Sell";
    public final String NEWS_BUTTON_LABEL = "News";
    public final String RESET_BUTTON_LABEL = "Reset";
    public final String TRANSACTION_HISTORY_BUTTON_LABEL = "View Transaction History";
    public final String REFRESH_BUTTON_LABEL = "Refresh";

    private DashboardState state = new DashboardState();

    public DashboardViewModel() {super("dashboard");}

    public void setState(DashboardState state) {
        this.state = state;
    }

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    public void firePropertyChanged() {
        support.firePropertyChange("state", null, this.state);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public DashboardState getState() {
        return state;
    }
}
