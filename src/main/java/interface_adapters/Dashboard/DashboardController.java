package interface_adapters.Dashboard;
import use_cases.Dashboard.DashboardInputBoundary;
import use_cases.Dashboard.DashboardInputData;

public class DashboardController {
    final private DashboardInputBoundary dashboardInteractor;

    public DashboardController(DashboardInputBoundary dashboardInteractor) {
        this.dashboardInteractor = dashboardInteractor;
    }

    public void execute(Boolean refreshPressed) {
        dashboardInteractor.execute(new DashboardInputData(refreshPressed));
    }
}
