package interface_adapter.Buy;

import interface_adapter.ViewManagerModel;
import use_case.Buy.BuyOutputBoundary;
import use_case.Buy.BuyOutputData;
import use_case.Buy.BuySearchOutputData;

public class BuyPresenter implements BuyOutputBoundary {

    private final BuyViewModel buyViewModel;
    private ViewManagerModel viewManagerModel;

    public BuyPresenter(
            ViewManagerModel viewManagerModel,
            BuyViewModel buyViewModel
    ) {
        this.viewManagerModel = viewManagerModel;
        this.buyViewModel = buyViewModel;
    }

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
    @Override
    public void prepareSuccessView(BuySearchOutputData response) {
        BuyState state = buyViewModel.getState();

        state.setRenderNewInfo(true);
        state.setCurBalance(response.getCurBalance());
        state.setStockInfo(response.getStringMap());
        state.setTicker(response.getTicker());

        buyViewModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String error) {
        BuyState state = buyViewModel.getState();
        state.setAmountError(error);
        buyViewModel.firePropertyChanged();
    }
}
