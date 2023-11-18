package interface_adapters.Dashboard;

import interface_adapters.ViewManagerModel;
import use_cases.Sell.SellOutputData;

public class DashboardPresenter implements DashboardOutputBoundary {

    private final DashboardViewModel sellViewModel;
    private final DashboardViewModel dashboardViewModel;
    private final ViewManagerModel viewManagerModel;

    public DashboardPresenter(ViewManagerModel viewManagerModel,
                              DashboardViewModel sellViewModel, DashboardViewModel dashboardViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.sellViewModel = sellViewModel;
        this.dashboardViewModel = dashboardViewModel;
    }

    @Override
    public void prepareSuccessView(SellOutputData response) {
        dashboardViewModel.firePropertyChanged();
        viewManagerModel.setActiveView(dashboardViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String error) {
        DashboardState sellState = sellViewModel.getState();
        sellState.setAmountError(error);
        sellViewModel.firePropertyChanged();
    }
}
