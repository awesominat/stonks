package interface_adapter.GetNews;

import interface_adapter.ViewManagerModel;
import use_case.GetNews.GetNewsOutputBoundary;
import use_case.GetNews.GetNewsOutputData;

/**
 * Serves as the presenter for the GetNews use case.
 * Implements the output boundary interface of the GetNews use case and is responsible for handling
 * the output of this use case by updating the associated ViewModel and triggering
 * necessary view updates through the ViewManagerModel.
 */
public class GetNewsPresenter implements GetNewsOutputBoundary {
    private final GetNewsViewModel getNewsViewModel;
    private ViewManagerModel viewManagerModel;

    /**
     * Constructs a new GetNewsPresenter with the specified ViewManagerModel and GetNewsViewModel.
     *
     * @param viewManagerModel  The model responsible for managing views in this application.
     * @param getNewsViewModel  The ViewModel associated with the GetNews use case.
     */
    public GetNewsPresenter(ViewManagerModel viewManagerModel, GetNewsViewModel getNewsViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.getNewsViewModel = getNewsViewModel;
    }

    /**
     * Handles the successful execution of the GetNews use case by updating the ViewModel
     * and triggering view updates through the ViewManagerModel.
     *
     * @param response The output data containing the given company ticker and news items associated with that company.
     */
    @Override
    public void prepareSuccessView(GetNewsOutputData response) {
        GetNewsState state = getNewsViewModel.getState();

        state.setRenderNewInfo(true);
        state.setNewsItems(response.getNewsItems());
        state.setTicker(response.getTicker());

        getNewsViewModel.firePropertyChanged();
    }

    /**
     * Handles the failure scenario of the GetNews use case by updating the ViewModel
     * with the error information and triggering view updates through the ViewManagerModel.
     *
     * @param error The error message indicating the cause of the failure.
     */
    @Override
    public void prepareFailView(String error) {
        GetNewsState state = getNewsViewModel.getState();
        state.setTickerError(error);
        getNewsViewModel.firePropertyChanged();
    }

}
