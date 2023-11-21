package interface_adapters.GetNews;

import interface_adapters.ViewManagerModel;
import use_cases.GetNews.GetNewsOutputBoundary;
import use_cases.GetNews.GetNewsOutputData;
import view.GetNewsView;

public class GetNewsPresenter implements GetNewsOutputBoundary {
    private final GetNewsView getNewsViewModel;
    private ViewManagerModel viewManagerModel;

    public GetNewsPresenter(ViewManagerModel viewManagerModel, GetNewsView getNewsViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.getNewsViewModel = getNewsViewModel;
    }

    @Override
    public void prepareSuccessView(GetNewsOutputData response) {
        // TODO: On Success switch to Dashboard View
        // Figure out how to make this switch
    }

    @Override
    public void prepareFailView(String error) {
        // TODO
//        GetNewsState getNewsState = getNewsViewModel.getState();
//        GetNewsState.setProcessError(error);
//        getNewsViewModel.firePropertyChanged();
    }
}
