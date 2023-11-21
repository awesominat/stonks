package interface_adapters.Dashboard;

import interface_adapters.ViewManagerModel;
import use_cases.Dashboard.DashboardOutputBoundary;
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
        // Get the current dashboardViewModel's state
        DashboardState dashboardState = dashboardViewModel.getState();
        // Alter the state such that it updates to the new portfolioInformation and user stats
        dashboardState.setOwnedStocks(dashboardOutputData.getPortfolioInformation());
        dashboardState.setUserStats(dashboardOutputData.getUserStats());
        // fire the property changed for dashboard view model
        dashboardViewModel.firePropertyChanged();

        // set active view to be dashboard
        viewManagerModel.setActiveView(dashboardViewModel.getViewName());
        // fire the property changed for view manager model such that the view changes to dashboard
        viewManagerModel.firePropertyChanged();
    }

}
