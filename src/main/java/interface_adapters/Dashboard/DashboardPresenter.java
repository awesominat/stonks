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
        // Alter the state such that it updates to the new ticker, amount, and full name info
        // and user stats
        dashboardState.setOwnedAmounts(dashboardOutputData.getAmountInformation());
        dashboardState.setOwnedTickers(dashboardOutputData.getTickerInformation());
        dashboardState.setOwnedFullNames(dashboardOutputData.getFullNamesInformation());
        dashboardState.setUserStats(dashboardOutputData.getUserStats());

        // below code should not be necessary, since all switches to dashboard view are followed by a viewManagerModel call
        // set active view to be dashboard
//        viewManagerModel.setActiveView(dashboardViewModel.getViewName());
        // fire the property changed for view manager model such that the view changes to dashboard
//        viewManagerModel.firePropertyChanged();
    }
}
