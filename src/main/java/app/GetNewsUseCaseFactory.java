package app;

import interface_adapters.Buy.BuyViewModel;
import interface_adapters.Dashboard.DashboardController;
import interface_adapters.Dashboard.DashboardPresenter;
import interface_adapters.Dashboard.DashboardViewModel;
import interface_adapters.GetNews.GetNewsController;
import interface_adapters.GetNews.GetNewsPresenter;
import interface_adapters.GetNews.GetNewsViewModel;
import interface_adapters.ResetBalance.ResetBalanceViewModel;
import interface_adapters.Sell.SellViewModel;
import interface_adapters.ViewManagerModel;
import use_cases.APIAccessInterface;
import use_cases.Dashboard.DashboardDataAccessInterface;
import use_cases.Dashboard.DashboardInputBoundary;
import use_cases.Dashboard.DashboardInteractor;
import use_cases.Dashboard.DashboardOutputBoundary;
import use_cases.GetNews.*;
import view.DashboardView;
import view.GetNewsView;

public class GetNewsUseCaseFactory {

    // Prevent instantiation
    private GetNewsUseCaseFactory() {}

    public static GetNewsView create(
            ViewManagerModel viewManagerModel,
            GetNewsViewModel getNewsViewModel,
            DashboardViewModel dashboardViewModel,
            GetNewsDataAccessInterface getNewsDataAccessInterface,
            APIAccessInterface apiAccessInterface
    ) {
        GetNewsController getNewsController = createGetNewsUseCase(
                viewManagerModel,
                getNewsViewModel,
                getNewsDataAccessInterface,
                apiAccessInterface
        );
        return new GetNewsView(
                getNewsViewModel,
                getNewsController,
                viewManagerModel,
                dashboardViewModel
        );
    }

    private static GetNewsController createGetNewsUseCase(
            ViewManagerModel viewManagerModel,
            GetNewsViewModel getNewsViewModel,
            GetNewsDataAccessInterface getNewsDataAccessInterface,
            APIAccessInterface apiAccessInterface
    ) {
        GetNewsOutputBoundary getNewsPresenter = new GetNewsPresenter(viewManagerModel, getNewsViewModel);
        GetNewsInputBoundary getNewsInteractor = new GetNewsInteractor(
                getNewsPresenter,
                apiAccessInterface
        );
        return new GetNewsController(getNewsInteractor);
    }

}
