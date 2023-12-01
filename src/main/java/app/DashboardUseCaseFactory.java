package app;

import interface_adapter.Buy.BuyViewModel;
import interface_adapter.CacheStockInformation.CacheStockInformationViewModel;
import interface_adapter.Dashboard.DashboardController;
import interface_adapter.Dashboard.DashboardPresenter;
import interface_adapter.Dashboard.DashboardViewModel;
import interface_adapter.GetNews.GetNewsViewModel;
import interface_adapter.GetTransactionHistory.GetTransactionHistoryViewModel;
import interface_adapter.ResetBalance.ResetBalanceController;
import interface_adapter.Sell.SellViewModel;
import interface_adapter.ViewManagerModel;
import use_case.APIAccessInterface;
import use_case.Dashboard.DashboardDataAccessInterface;
import use_case.Dashboard.DashboardInputBoundary;
import use_case.Dashboard.DashboardInteractor;
import use_case.Dashboard.DashboardOutputBoundary;
import view.DashboardView;

public class DashboardUseCaseFactory {

    // Prevent instantiation
    private DashboardUseCaseFactory() {}

    public static DashboardView create(
            ViewManagerModel viewManagerModel,
            DashboardViewModel dashboardViewModel,
            BuyViewModel buyViewModel,
            SellViewModel sellViewModel,
            GetNewsViewModel getNewsViewModel,
            CacheStockInformationViewModel cacheStockInformationViewModel,
            ResetBalanceController resetBalanceController,
            GetTransactionHistoryViewModel getTransactionHistoryViewModel,
            DashboardDataAccessInterface dashboardDataAccessInterface,
            APIAccessInterface apiAccessInterface
    ) {
        DashboardController dashboardController = createDashboardUseCase(
                viewManagerModel,
                dashboardViewModel,
                sellViewModel,
                cacheStockInformationViewModel,
                dashboardDataAccessInterface,
                apiAccessInterface
        );
        dashboardController.execute(true);
        return new DashboardView(
                dashboardViewModel,
                dashboardController,
                buyViewModel,
                sellViewModel,
                getNewsViewModel,
                resetBalanceController,
                getTransactionHistoryViewModel,
                viewManagerModel
        );
    }

    private static DashboardController createDashboardUseCase(
            ViewManagerModel viewManagerModel,
            DashboardViewModel dashboardViewModel,
            SellViewModel sellViewModel,
            CacheStockInformationViewModel cacheStockInformationViewModel,
            DashboardDataAccessInterface dashboardDataAccessInterface,
            APIAccessInterface apiAccessInterface
    ) {
        DashboardOutputBoundary dashboardPresenter = new DashboardPresenter(
                viewManagerModel,
                dashboardViewModel,
                sellViewModel,
                cacheStockInformationViewModel
        );
        DashboardInputBoundary dashboardInteractor = new DashboardInteractor(
                dashboardDataAccessInterface,
                dashboardPresenter,
                apiAccessInterface
        );
        return new DashboardController(dashboardInteractor);
    }

}
