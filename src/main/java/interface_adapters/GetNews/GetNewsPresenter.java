package interface_adapters.GetNews;

import interface_adapters.ViewManagerModel;
import use_cases.GetNews.GetNewsOutputBoundary;
import use_cases.GetNews.GetNewsOutputData;

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
