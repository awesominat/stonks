package interface_adapters.Buy;

import interface_adapters.ViewManagerModel;
import use_cases.Buy.BuyOutputBoundary;
import use_cases.Buy.BuyOutputData;

public class BuyPresenter implements BuyOutputBoundary {

    private final BuyViewModel buyViewModel;
    private ViewManagerModel viewManagerModel;

    public BuyPresenter(ViewManagerModel viewManagerModel,
                           BuyViewModel buyViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.buyViewModel = buyViewModel;
    }

    @Override
    public void prepareSuccessView(BuyOutputData response) {
        // TODO: Ricky
        // On Success switch to Portfolio View
    }

    @Override
    public void prepareFailView(String error) {
        // TODO: Ricky
//        BuyState buyState = buyViewModel.getState();
//        BuyState.setProcessError(error);
//        buyViewModel.firePropertyChanged();
    }
}
