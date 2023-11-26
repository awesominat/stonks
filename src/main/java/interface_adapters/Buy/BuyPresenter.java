package interface_adapters.Buy;

import interface_adapters.ViewManagerModel;
import use_cases.Buy.BuyOutputBoundary;
import use_cases.Buy.BuyOutputData;
import use_cases.Buy.BuySearchOutputData;

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
        BuyState state = buyViewModel.getState();

        state.setRenderNewInfo(true);
        state.setCurBalance(response.getNewBalance());
        state.setBoughtStock(true);
        state.setAmount(String.valueOf(response.getAmount()));
        state.setTicker(response.getTicker());

        buyViewModel.firePropertyChanged();
    }
    @Override
    public void prepareSuccessView(BuySearchOutputData response) {
        BuyState state = buyViewModel.getState();

        state.setRenderNewInfo(true);
        state.setCurBalance(response.getCurBalance());
        state.setStockInfo(response.getStringMap());
        state.setTicker(response.getTicker());

        buyViewModel.firePropertyChanged();
//        viewManagerModel.setActiveView(buyViewModel.getViewName());
//        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String error) {
        BuyState state = buyViewModel.getState();
        state.setAmountError(error);
        buyViewModel.firePropertyChanged();
    }
}
