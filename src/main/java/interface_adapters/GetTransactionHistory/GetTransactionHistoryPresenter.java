package interface_adapters.GetTransactionHistory;

import interface_adapters.Dashboard.DashboardViewModel;
import interface_adapters.ViewManagerModel;
import use_cases.GetTransactionHistory.GetTransactionHistoryOutputBoundary;
import use_cases.GetTransactionHistory.GetTransactionHistoryOutputData;

import java.util.Vector;

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
