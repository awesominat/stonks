package interface_adapter.GetTransactionHistory;

import interface_adapter.Dashboard.DashboardViewModel;
import interface_adapter.ViewManagerModel;
import use_case.GetTransactionHistory.GetTransactionHistoryOutputBoundary;
import use_case.GetTransactionHistory.GetTransactionHistoryOutputData;

public class GetTransactionHistoryPresenter implements GetTransactionHistoryOutputBoundary {
    private final GetTransactionHistoryViewModel getTransactionHistoryViewModel;
    private final DashboardViewModel dashboardViewModel;
    private final ViewManagerModel viewManagerModel;

    public GetTransactionHistoryPresenter(
            ViewManagerModel viewManagerModel,
            GetTransactionHistoryViewModel getTransactionHistoryViewModel,
            DashboardViewModel dashboardViewModel
    ){

        this.getTransactionHistoryViewModel = getTransactionHistoryViewModel;
        this.dashboardViewModel = dashboardViewModel;
        this.viewManagerModel = viewManagerModel;

    }
    @Override
    public void prepareSuccessView(GetTransactionHistoryOutputData getTransactionHistoryOutputData) {

        GetTransactionHistoryState state = getTransactionHistoryViewModel.getState();

        state.setUserRecord(getTransactionHistoryOutputData.getUserRecord());
        getTransactionHistoryViewModel.setState(state);

        viewManagerModel.setActiveView(getTransactionHistoryViewModel.getViewName());

        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String error) {

    }
}
