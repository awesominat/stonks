package interface_adapters.Dashboard;
import use_cases.Dashboard.DashboardInputBoundary;

public class DashboardController {
    final private DashboardInputBoundary dashboardInteractor;

    public DashboardController(DashboardInputBoundary dashboardInteractor) {
        this.dashboardInteractor = dashboardInteractor;
    }

    public void execute() {
        sellInteractor.execute();
    }
}
