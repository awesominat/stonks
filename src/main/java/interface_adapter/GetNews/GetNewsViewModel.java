package interface_adapter.GetNews;

import interface_adapter.ViewModel;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class GetNewsViewModel extends ViewModel {
    public final String TITLE_LABEL = "Get Company News";
    public final String SEARCH_BUTTON_LABEL = "Search";
    public final String BACK_BUTTON_LABEL = "Back";
    public final String SEARCH_BAR_LABEL = "Stock Ticker";

    private GetNewsState state = new GetNewsState();

    public GetNewsViewModel() {super("news");}

    public void setState(GetNewsState state) {
        this.state = state;
    }

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    public void firePropertyChanged() {
        support.firePropertyChange("state", null, this.state);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public GetNewsState getState() {
        return state;
    }
}
