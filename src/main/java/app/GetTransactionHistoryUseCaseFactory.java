package app;

import interface_adapters.Buy.BuyViewModel;
import interface_adapters.Dashboard.DashboardViewModel;
import interface_adapters.GetTransactionHistory.GetTransactionHistoryController;
import interface_adapters.GetTransactionHistory.GetTransactionHistoryPresenter;
import interface_adapters.GetTransactionHistory.GetTransactionHistoryViewModel;
import interface_adapters.Sell.SellController;
import interface_adapters.Sell.SellPresenter;
import interface_adapters.Sell.SellViewModel;
import interface_adapters.ViewManagerModel;
import use_cases.APIAccessInterface;
import use_cases.Buy.BuyDataAccessInterface;
import use_cases.GetTransactionHistory.GetTransactionHistoryDataAccessInterface;
import use_cases.GetTransactionHistory.GetTransactionHistoryInputBoundary;
import use_cases.GetTransactionHistory.GetTransactionHistoryInteractor;
import use_cases.GetTransactionHistory.GetTransactionHistoryOutputBoundary;
import use_cases.Sell.SellDataAccessInterface;
import use_cases.Sell.SellInputBoundary;
import use_cases.Sell.SellInteractor;
import use_cases.Sell.SellOutputBoundary;
import view.BuyView;
import view.SellView;
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

