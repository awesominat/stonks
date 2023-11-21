package interface_adapters.Dashboard;

import use_cases.Dashboard.DashboardOutputData;

public interface DashboardOutputBoundary {
    void prepareSuccessView(DashboardOutputData dashboardOutputData);
}
