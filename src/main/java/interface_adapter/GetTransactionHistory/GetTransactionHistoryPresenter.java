package interface_adapter.GetTransactionHistory;

import interface_adapter.Dashboard.DashboardViewModel;
import interface_adapter.ViewManagerModel;
import use_case.GetTransactionHistory.GetTransactionHistoryOutputBoundary;
import use_case.GetTransactionHistory.GetTransactionHistoryOutputData;

/**
 * The {@code GetTransactionHistoryPresenter} class implements the
 * {@code GetTransactionHistoryOutputBoundary} interface and is responsible for
 * preparing the success view based on the output data from the
 * {@code GetTransactionHistoryUseCase}. It updates the associated
 * {@code GetTransactionHistoryViewModel}, sets the active view in the
 * {@code ViewManagerModel}, and notifies observers of the view change.
 */
public class GetTransactionHistoryPresenter implements GetTransactionHistoryOutputBoundary {
    private final GetTransactionHistoryViewModel getTransactionHistoryViewModel;
    private final DashboardViewModel dashboardViewModel;
    private final ViewManagerModel viewManagerModel;

    /**
     * Constructs a {@code GetTransactionHistoryPresenter} with the specified
     * dependencies.
     *
     * @param viewManagerModel           the {@code ViewManagerModel} for managing views
     * @param getTransactionHistoryViewModel the {@code GetTransactionHistoryViewModel}
     *                                     associated with this presenter
     * @param dashboardViewModel         the {@code DashboardViewModel} for dashboard-related data
     */
    public GetTransactionHistoryPresenter(
            ViewManagerModel viewManagerModel,
            GetTransactionHistoryViewModel getTransactionHistoryViewModel,
            DashboardViewModel dashboardViewModel
    ){
        this.getTransactionHistoryViewModel = getTransactionHistoryViewModel;
        this.dashboardViewModel = dashboardViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    /**
     * Prepares the success view by updating the state of the
     * {@code GetTransactionHistoryViewModel} with the user record data
     * from the output data. It sets the active view in the
     * {@code ViewManagerModel} and notifies observers of the view change.
     *
     * @param getTransactionHistoryOutputData the output data from the
     *                                        {@code GetTransactionHistoryUseCase}
     */
    @Override
    public void prepareSuccessView(GetTransactionHistoryOutputData getTransactionHistoryOutputData) {
        GetTransactionHistoryState state = getTransactionHistoryViewModel.getState();
        state.setUserRecord(getTransactionHistoryOutputData.getUserRecord());
        getTransactionHistoryViewModel.setState(state);
        viewManagerModel.setActiveView(getTransactionHistoryViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

}
