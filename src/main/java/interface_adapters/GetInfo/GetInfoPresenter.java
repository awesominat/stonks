package interface_adapters.GetInfo;

import interface_adapters.ViewManagerModel;
import use_cases.GetInfo.GetInfoOutputBoundary;
import use_cases.GetInfo.GetInfoOutputData;

public class GetInfoPresenter implements GetInfoOutputBoundary {

    private final GetInfoViewModel getInfoViewModel;
    private final ViewManagerModel viewManagerModel;

    public GetInfoPresenter(GetInfoViewModel getInfoViewModel, ViewManagerModel viewManagerModel) {
        this.viewManagerModel = viewManagerModel;
        this.getInfoViewModel = getInfoViewModel;
    }

    @Override
    public void prepareSuccessView(GetInfoOutputData response) {
        // TODO: Is this supposed to actually display anything upon succeeding or is it a behind-the-scenes use case?
    }

    @Override
    public void prepareFailView(String error) {
        // TODO
        GetInfoState state = getInfoViewModel.getState();
        state.setTickerError(error);
        getInfoViewModel.firePropertyChanged();
    }

}
