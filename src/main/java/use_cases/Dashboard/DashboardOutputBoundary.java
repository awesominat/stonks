package use_cases.Dashboard;

public interface DashboardOutputBoundary {

    void prepareSuccessView(DashboardOutputData response);

    void prepareFailView(String error);

}
