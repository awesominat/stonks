package interface_adapter.Buy;

import interface_adapter.ViewManagerModel;
import use_case.Buy.BuyOutputBoundary;
import use_case.Buy.BuyOutputData;
import use_case.Buy.BuySearchOutputData;

/**
 * Encapusaltes methods related to the presenter for BuyViewModel
 */
public class BuyPresenter implements BuyOutputBoundary {

    private final BuyViewModel buyViewModel;
    private ViewManagerModel viewManagerModel;

    /**
     * @param viewManagerModel The model for the view manager
     * @param buyViewModel The view model for the BuyView
     */
    public BuyPresenter(
            ViewManagerModel viewManagerModel,
            BuyViewModel buyViewModel
    ) {
        this.viewManagerModel = viewManagerModel;
        this.buyViewModel = buyViewModel;
    }

    /**
     * @param response The output from the use case when the interactor executed a purchase (or failure thereof).
     */
    @Override
    public void prepareSuccessView(BuyOutputData response) {
        BuyState state = buyViewModel.getState();

        state.setCurBalance(response.getNewBalance());
        if (response.getExecutedPurchase()) {
            state.setRenderNewInfo(true);
            state.setBoughtStock(true);
            buyViewModel.firePropertyChanged();
        }
    }

    /**
     * @param response The output from the use case when the interactor executed a search.
     */
    @Override
    public void prepareSuccessView(BuySearchOutputData response) {
        BuyState state = buyViewModel.getState();

        state.setRenderNewInfo(true);
        state.setCurBalance(response.getCurBalance());
        state.setStockInfo(response.getStringMap());
        state.setTicker(response.getTicker());

        buyViewModel.firePropertyChanged();
    }

    /**
     * @param error The error message from the use case when the interactor failed to execute a purchase or a search.
     */
    @Override
    public void prepareFailView(String error) {
        BuyState state = buyViewModel.getState();
        state.setAmountError(error);
        buyViewModel.firePropertyChanged();
    }
}
