package interface_adapter.Dashboard;
import use_case.Dashboard.DashboardInputBoundary;
import use_case.Dashboard.DashboardInputData;

public class DashboardController {
    final private DashboardInputBoundary dashboardInteractor;

    /**
     * Constructor for the controller
     *
     * @param dashboardInteractor       dashboard use case interactor
     */
    public DashboardController(DashboardInputBoundary dashboardInteractor) {
        this.dashboardInteractor = dashboardInteractor;
    }

    /**
     * There are two execution cases, when we want to refresh and when we want to use previous
     * stock prices.
     *
     * @param refreshPressed    boolean that indicated which of the two execution cases
     *                          is run in the interactor
     */
    public void execute(Boolean refreshPressed) {
        dashboardInteractor.execute(new DashboardInputData(refreshPressed));
    }
}
