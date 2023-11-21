package interface_adapters.Dashboard;

import interface_adapters.ViewManagerModel;
import use_cases.Dashboard.DashboardOutputData;

public class DashboardPresenter implements DashboardOutputBoundary {

    private final DashboardViewModel dashboardViewModel;
    private final ViewManagerModel viewManagerModel;

    public DashboardPresenter(ViewManagerModel viewManagerModel, DashboardViewModel dashboardViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.dashboardViewModel = dashboardViewModel;
    }

    @Override
    public void prepareSuccessView(DashboardOutputData dashboardOutputData) {
        DashboardState dashboardState = dashboardViewModel.getState();
        dashboardViewModel.firePropertyChanged();
        viewManagerModel.setActiveView(dashboardViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

}
