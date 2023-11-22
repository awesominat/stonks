package interface_adapters.ResetBalance;

import interface_adapters.ViewModel;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class ResetBalanceViewModel extends ViewModel {
    public final String TITLE_LABEL = "Reset Balance";
    public final String RESET_BUTTON_LABEL = "Reset";

    private ResetBalanceState state = new ResetBalanceState();

    public ResetBalanceViewModel() {
        super("reset");
    }

    public void setState(ResetBalanceState state) {
        this.state = state;
    }

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    public void firePropertyChanged() {
        support.firePropertyChange("state", null, this.state);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public ResetBalanceState getState() {
        return state;
    }
}
