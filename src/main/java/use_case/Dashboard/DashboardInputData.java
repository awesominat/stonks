package use_case.Dashboard;

public class DashboardInputData {
    private Boolean refreshPressed;

    /**
     * Constructor for dashboard input data.
     *
     * @param refreshPressed    is true if we are running a refresh execution case
     */
    public DashboardInputData(Boolean refreshPressed) {
        this.refreshPressed = refreshPressed;
    }

    public Boolean getRefreshPressed() {
        return refreshPressed;
    }

}
