package interface_adapter.ResetBalance;

import interface_adapter.Dashboard.DashboardState;
import interface_adapter.Dashboard.DashboardViewModel;
import interface_adapter.ViewManagerModel;
import use_case.ResetBalance.ResetBalanceOutputData;
import use_case.ResetBalance.ResetBalanceOutputBoundary;

public class ResetBalancePresenter implements ResetBalanceOutputBoundary{
    private ViewManagerModel viewManagerModel;
    private final DashboardViewModel dashboardViewModel;

    public ResetBalancePresenter(
            ViewManagerModel viewManagerModel,
            DashboardViewModel dashboardViewModel
    ) {
        this.viewManagerModel = viewManagerModel;
        this.dashboardViewModel = dashboardViewModel;
    }
    @Override
    public void prepareSuccessView(ResetBalanceOutputData response) {
        if (response.isResetPressed()){
            DashboardState state = this.dashboardViewModel.getState();
            state.setResetPressed(response.isResetPressed());
        }
    }

}
