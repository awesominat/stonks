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
    public void prepareSuccessView(DashboardOutputData response) {
        // Get the current dashboardViewModel's state
        DashboardState state = dashboardViewModel.getState();

        // Let the DashboardState know that there are new things to render.
        state.setRenderNewInfo(true);

        // Alter the state such that it updates to the new amounts, tickers, full names, prices, and user stats info
        state.setOwnedAmounts(response.getAmountInformation());
        state.setOwnedTickers(response.getTickerInformation());
        state.setOwnedFullNames(response.getFullNamesInformation());
        state.setPrices(response.getPriceInformation());
        state.setUserStats(response.getUserStats());
    }

}
