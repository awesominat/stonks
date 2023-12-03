package use_case.Dashboard;

public class DashboardInputData {
    private Boolean refreshPressed;

    public DashboardInputData(Boolean refreshPressed) {
        this.refreshPressed = refreshPressed;
    }

    public Boolean getRefreshPressed() {
        return refreshPressed;
    }

}
