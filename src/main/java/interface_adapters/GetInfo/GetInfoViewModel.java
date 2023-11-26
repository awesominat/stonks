package interface_adapters.GetInfo;

import interface_adapters.ViewModel;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class GetInfoViewModel extends ViewModel {

    public final String TITLE_LABEL = "Get Company Information";

    private GetInfoState state = new GetInfoState();

    public GetInfoViewModel() {super("information");}

    public void setState(GetInfoState state) {
        this.state = state;
    }

    public GetInfoState getState() {
        return state;
    }

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    public void firePropertyChanged() {
        support.firePropertyChange("state", null, this.state);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

}
