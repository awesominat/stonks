package interface_adapter.GetNews;

import interface_adapter.ViewModel;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class GetNewsViewModel extends ViewModel {

    /** The label for the title of the view. */
    public final String TITLE_LABEL = "Get Company News";

    /** The label for the search button. */
    public final String SEARCH_BUTTON_LABEL = "Search";

    /** The label for the back button. */
    public final String BACK_BUTTON_LABEL = "Back";

    /** The label for the search bar. */
    public final String SEARCH_BAR_LABEL = "Stock Ticker";

    /** The state of the GetNews use case. */
    private GetNewsState state = new GetNewsState();

    /**
     * Constructs a new GetNewsViewModel using the super constructor from ViewModel.
     */
    public GetNewsViewModel() {
        super("news");
    }

    /**
     * Sets the state of the GetNews use case.
     *
     * @param state  The new state of the GetNews use case.
     */
    public void setState(GetNewsState state) {
        this.state = state;
    }

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    /**
     * Notifies registered property change listeners about a change in the state of this use case.
     */
    public void firePropertyChanged() {
        support.firePropertyChange("state", null, this.state);
    }

    /**
     * Adds a property change listener to this view model.
     *
     * @param listener The listener to be added.
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    /**
     * Gets the current state of the GetNews use case.
     *
     * @return The current state of the GetNews use case.
     */
    public GetNewsState getState() {
        return state;
    }

}
