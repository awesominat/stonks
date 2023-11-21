package interface_adapters.GetNews;

import interface_adapters.ViewModel;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class GetNewsViewModel extends ViewModel {
    public final String TITLE_LABEL = "Get Company News";
    public final String NEWS_BUTTON_LABEL = "Get News";

    private GetNewsState state = new GetNewsState();

    public GetNewsViewModel() {super("Get Company News");}

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
