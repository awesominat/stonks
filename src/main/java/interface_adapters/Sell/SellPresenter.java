package interface_adapters.Sell;

import interface_adapters.Dashboard.DashboardViewModel;
import interface_adapters.ViewManagerModel;
import use_cases.Sell.SellOutputData;
import use_cases.Sell.SellOutputBoundary;

public class SellPresenter implements SellOutputBoundary {

    private final SellViewModel sellViewModel;
    private final DashboardViewModel dashboardViewModel;
    private final ViewManagerModel viewManagerModel;

    public SellPresenter(
            ViewManagerModel viewManagerModel,
            SellViewModel sellViewModel,
            DashboardViewModel dashboardViewModel
    ) {
        this.viewManagerModel = viewManagerModel;
        this.sellViewModel = sellViewModel;
        this.dashboardViewModel = dashboardViewModel;
    }

    @Override
    public void prepareSuccessView(SellOutputData response) {
        SellState sellState = sellViewModel.getState();
        if (response.isExecuteTypeSell()) {
            String sellSuccess = String.format("Congratulations! Sold %.2f stocks of ", response.getAmount()) + response.getTicker();
            sellState.setSellSuccess(sellSuccess);
        } else {
            sellState.setBalance(response.getBalance());
            sellState.setOwnedStocks(response.getOwnedStocks());
            sellState.setOwnedAmounts(response.getOwnedAmounts());
            sellState.setSellAmounts(response.getSellAmounts());
        }
        sellViewModel.setState(sellState);
//        sellViewModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String error) {
        SellState sellState = sellViewModel.getState();
        sellState.setAmountError(error);
//        sellViewModel.firePropertyChanged();
    }
}
