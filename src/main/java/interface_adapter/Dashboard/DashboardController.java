package interface_adapter.Dashboard;
import use_case.Dashboard.DashboardInputBoundary;
import use_case.Dashboard.DashboardInputData;

public class DashboardController {
    final private DashboardInputBoundary dashboardInteractor;

    public DashboardController(DashboardInputBoundary dashboardInteractor) {
        this.dashboardInteractor = dashboardInteractor;
    }

    public void execute(Boolean refreshPressed) {
        dashboardInteractor.execute(new DashboardInputData(refreshPressed));
    }
}
