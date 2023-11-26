package app;

import interface_adapters.Dashboard.DashboardViewModel;
import interface_adapters.Sell.SellController;
import interface_adapters.Sell.SellPresenter;
import interface_adapters.Sell.SellViewModel;
import interface_adapters.ViewManagerModel;
import use_cases.APIAccessInterface;
import use_cases.Sell.SellDataAccessInterface;
import use_cases.Sell.SellInputBoundary;
import use_cases.Sell.SellInteractor;
import use_cases.Sell.SellOutputBoundary;
import view.SellView;

public class SellStockUseCaseFactory {

    // Prevent instantiation
    private SellStockUseCaseFactory() {}

    public static SellView create(
            ViewManagerModel viewManagerModel,
            SellViewModel sellViewModel,
            DashboardViewModel dashboardViewModel,
            SellDataAccessInterface sellDataAccessInterface,
            APIAccessInterface apiAccessInterface
    ) {
        SellController sellController = createSellStockUseCase(
                viewManagerModel, sellViewModel, dashboardViewModel,
                sellDataAccessInterface, apiAccessInterface
        );
        return new SellView(sellViewModel, sellController, viewManagerModel, dashboardViewModel);
    }

    private static SellController createSellStockUseCase(
            ViewManagerModel viewManagerModel,
            SellViewModel sellViewModel,
            DashboardViewModel dashboardViewModel,
            SellDataAccessInterface sellDataAccessInterface,
            APIAccessInterface apiAccessInterface
    ) {
        SellOutputBoundary sellPresenter = new SellPresenter(viewManagerModel, sellViewModel, dashboardViewModel);
        SellInputBoundary sellInteractor = new SellInteractor(sellDataAccessInterface, sellPresenter, apiAccessInterface);
        return new SellController(sellInteractor);
    }

}
