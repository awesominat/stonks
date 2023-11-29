package app;

import interface_adapter.Dashboard.DashboardViewModel;
import interface_adapter.GetTransactionHistory.GetTransactionHistoryController;
import interface_adapter.GetTransactionHistory.GetTransactionHistoryPresenter;
import interface_adapter.GetTransactionHistory.GetTransactionHistoryViewModel;
import interface_adapter.ViewManagerModel;
import use_case.APIAccessInterface;
import use_case.GetTransactionHistory.GetTransactionHistoryDataAccessInterface;
import use_case.GetTransactionHistory.GetTransactionHistoryInputBoundary;
import use_case.GetTransactionHistory.GetTransactionHistoryInteractor;
import use_case.GetTransactionHistory.GetTransactionHistoryOutputBoundary;
import view.TransactionHistoryView;

public class GetTransactionHistoryUseCaseFactory {

    private GetTransactionHistoryUseCaseFactory() {
    }

    public static TransactionHistoryView create(
            ViewManagerModel viewManagerModel,
            GetTransactionHistoryViewModel getTransactionHistoryViewModel,
            DashboardViewModel dashboardViewModel,
            GetTransactionHistoryDataAccessInterface getTransactionHistoryDataAccessInterface,
            APIAccessInterface apiAccessInterface
    ) {
        GetTransactionHistoryController getTransactionHistoryController = createGetTransactionHistoryUseCase(
                viewManagerModel,
                getTransactionHistoryViewModel,
                dashboardViewModel,
                getTransactionHistoryDataAccessInterface
        );
            return new TransactionHistoryView(
                    getTransactionHistoryController,
                    getTransactionHistoryViewModel,
                    viewManagerModel,
                    dashboardViewModel
            );

    }
    private static GetTransactionHistoryController createGetTransactionHistoryUseCase(
            ViewManagerModel viewManagerModel,
            GetTransactionHistoryViewModel getTransactionHistoryViewModel,
            DashboardViewModel dashboardViewModel,
            GetTransactionHistoryDataAccessInterface getTransactionHistoryDataAccessInterface
    ) {
        GetTransactionHistoryOutputBoundary getTransactionHistoryOutputPresenter = new GetTransactionHistoryPresenter(
                viewManagerModel,
                getTransactionHistoryViewModel,
                dashboardViewModel
        );

        GetTransactionHistoryInputBoundary getTransactionHistoryInteractor = new GetTransactionHistoryInteractor(
                getTransactionHistoryDataAccessInterface,
                getTransactionHistoryOutputPresenter
        );

        return new GetTransactionHistoryController(getTransactionHistoryInteractor);
    }
}

