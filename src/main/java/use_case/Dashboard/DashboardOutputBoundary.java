package use_case.Dashboard;

public interface DashboardOutputBoundary {

    void prepareSuccessView(DashboardOutputData response);

    void prepareFailView(String error);
}
