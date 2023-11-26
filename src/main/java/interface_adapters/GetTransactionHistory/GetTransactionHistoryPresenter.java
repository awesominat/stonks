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

    public GetTransactionHistoryPresenter(ViewManagerModel viewManagerModel,
                                          GetTransactionHistoryViewModel getTransactionHistoryViewModel,
                                          DashboardViewModel dashboardViewModel){

        this.getTransactionHistoryViewModel = getTransactionHistoryViewModel;
        this.dashboardViewModel = dashboardViewModel;
        this.viewManagerModel = viewManagerModel;

    }
    @Override
    public void prepareSuccessView(GetTransactionHistoryOutputData getTransactionHistoryOutputData) {
        // Get the current getTransactionHistoryViewModel's state
        GetTransactionHistoryState state = getTransactionHistoryViewModel.getState();
        // Alter the state such that it updates to the new UserRecord
        state.setUserRecord(getTransactionHistoryOutputData.getUserRecord());
        // fire the property changed for getTransactionHistory view model
        getTransactionHistoryViewModel.firePropertyChanged();

        // set active view to be transaction history
        viewManagerModel.setActiveView(getTransactionHistoryViewModel.getViewName());
        // fire the property changed for view manager model such that the view changes to
        // transaction history
        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String error) {

    }
}
