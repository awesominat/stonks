package app;

import interface_adapters.Buy.BuyViewModel;
import interface_adapters.Dashboard.DashboardController;
import interface_adapters.Dashboard.DashboardPresenter;
import interface_adapters.Dashboard.DashboardViewModel;
import interface_adapters.GetNews.GetNewsViewModel;
import interface_adapters.ResetBalance.ResetBalanceViewModel;
import interface_adapters.Sell.SellViewModel;
import interface_adapters.ViewManagerModel;
import use_cases.APIAccessInterface;
import use_cases.Dashboard.DashboardDataAccessInterface;
import use_cases.Dashboard.DashboardInputBoundary;
import use_cases.Dashboard.DashboardInteractor;
import use_cases.Dashboard.DashboardOutputBoundary;
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
            ResetBalanceViewModel resetBalanceViewModel,
            DashboardDataAccessInterface dashboardDataAccessInterface,
            APIAccessInterface apiAccessInterface
    ) {
        DashboardController dashboardController = createDashboardUseCase(
                viewManagerModel,
                dashboardViewModel,
                dashboardDataAccessInterface,
                apiAccessInterface
        );
        return new DashboardView(
                dashboardViewModel,
                dashboardController,
                buyViewModel,
                sellViewModel,
                getNewsViewModel,
                resetBalanceViewModel,
                viewManagerModel
        );
    }

    private static DashboardController createDashboardUseCase(
            ViewManagerModel viewManagerModel,
            DashboardViewModel dashboardViewModel,
            DashboardDataAccessInterface dashboardDataAccessInterface,
            APIAccessInterface apiAccessInterface
    ) {
        DashboardOutputBoundary dashboardPresenter = new DashboardPresenter(viewManagerModel, dashboardViewModel);
        DashboardInputBoundary dashboardInteractor = new DashboardInteractor(
                dashboardDataAccessInterface,
                dashboardPresenter,
                apiAccessInterface
        );
        return new DashboardController(dashboardInteractor);
    }

}
