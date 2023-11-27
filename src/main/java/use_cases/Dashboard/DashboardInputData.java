package use_cases.Dashboard;

public class DashboardInputData {
    private Boolean aoiRefresh;

    public DashboardInputData(Boolean aoiRefresh) {
        this.aoiRefresh = aoiRefresh;
    }
    public DashboardInputData() {
        this.aoiRefresh = false;
    }

    public Boolean getAoiRefresh() {
        return aoiRefresh;
    }

    public void setAoiRefresh(Boolean aoiRefresh) {
        this.aoiRefresh = aoiRefresh;
    }
}
