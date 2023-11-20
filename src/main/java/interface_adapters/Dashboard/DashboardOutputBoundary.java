package interface_adapters.Dashboard;

import use_cases.Dashboard.DashboardOutputData;

public interface DashboardOutputBoundary {
    void prepareSuccessView(DashboardOutputData result);
    void prepareFailView(String error);
}
