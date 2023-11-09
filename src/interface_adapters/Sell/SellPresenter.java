package interface_adapters.Sell;

import interface_adapters.ViewManagerModel;
import use_cases.Sell.SellOutputData;

public class SellPresenter implements SellOutputBoundary {

    private final SellViewModel sellViewModel;
    private final PortfolioViewModel portfolioViewModel;
    private final ViewManagerModel viewManagerModel;

    public SellPresenter(ViewManagerModel viewManagerModel,
                         SellViewModel sellViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.sellViewModel = sellViewModel;
    }

    @Override
    public void prepareSuccessView(SellOutputData response) {
        portfolioViewModel.firePropertyChanged();
        viewManagerModel.setActiveView(portfolioViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String error) {
        SellState sellState = sellViewModel.getState();
        sellState.setAmountError(error);
        sellViewModel.firePropertyChanged();
    }
}
