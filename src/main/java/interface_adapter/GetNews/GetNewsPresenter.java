package interface_adapter.GetNews;

import interface_adapter.ViewManagerModel;
import use_case.GetNews.GetNewsOutputBoundary;
import use_case.GetNews.GetNewsOutputData;

public class GetNewsPresenter implements GetNewsOutputBoundary {
    private final GetNewsViewModel getNewsViewModel;
    private ViewManagerModel viewManagerModel;

    public GetNewsPresenter(ViewManagerModel viewManagerModel, GetNewsViewModel getNewsViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.getNewsViewModel = getNewsViewModel;
    }

    @Override
    public void prepareSuccessView(GetNewsOutputData response) {
        // TODO: On success, show articles in a list (aka. table)
        GetNewsState state = getNewsViewModel.getState();

        state.setRenderNewInfo(true);
        state.setNewsItems(response.getNewsItems());
        state.setTicker(response.getTicker());

        getNewsViewModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String error) {
        GetNewsState state = getNewsViewModel.getState();
        state.setTickerError(error);
        getNewsViewModel.firePropertyChanged();
    }

}
