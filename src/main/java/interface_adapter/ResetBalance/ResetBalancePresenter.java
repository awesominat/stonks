package interface_adapter.ResetBalance;

import interface_adapter.Dashboard.DashboardState;
import interface_adapter.Dashboard.DashboardViewModel;
import interface_adapter.ViewManagerModel;
import use_case.ResetBalance.ResetBalanceOutputData;
import use_case.ResetBalance.ResetBalanceOutputBoundary;

public class ResetBalancePresenter implements ResetBalanceOutputBoundary{
    private ViewManagerModel viewManagerModel;
    private final DashboardViewModel dashboardViewModel;

    /**
     * Constructor for the reset balance presenter
     *
     * @param viewManagerModel      view manager model
     * @param dashboardViewModel    the dashboard view model. This is required because the reset use case
     *                              has no view, so it mutates the dashboard state to indicate when the reset
     *                              is successful and the reset popup needs to be shown.
     */
    public ResetBalancePresenter(
            ViewManagerModel viewManagerModel,
            DashboardViewModel dashboardViewModel
    ) {
        this.viewManagerModel = viewManagerModel;
        this.dashboardViewModel = dashboardViewModel;
    }

    /**
     * The prepare success view method, which accesses the dashboard state from the
     * dashboard view model and mutates it to indicate that a reset has occurred.
     *
     * @param response      an instance of dashboard output data, which contains whether
     *                      the reset button has been pressed
     */
    @Override
    public void prepareSuccessView(ResetBalanceOutputData response) {
        if (response.isResetPressed()){
            DashboardState state = this.dashboardViewModel.getState();
            state.setResetPressed(response.isResetPressed());
        }
    }
}
