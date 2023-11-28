package use_cases.Dashboard;

public class DashboardInputData {
    private Boolean apiRefresh;

    public DashboardInputData(Boolean apiRefresh) {
        this.apiRefresh = apiRefresh;
    }
    public DashboardInputData() {
        this.apiRefresh = false;
    }

    public Boolean getApiRefresh() {
        return apiRefresh;
    }

    public void setApiRefresh(Boolean apiRefresh) {
        this.apiRefresh = apiRefresh;
    }
}
