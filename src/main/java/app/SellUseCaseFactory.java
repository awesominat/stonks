package app;

import interface_adapter.Dashboard.DashboardViewModel;
import interface_adapter.Sell.SellController;
import interface_adapter.Sell.SellPresenter;
import interface_adapter.Sell.SellViewModel;
import interface_adapter.ViewManagerModel;
import use_case.APIAccessInterface;
import use_case.Sell.SellDataAccessInterface;
import use_case.Sell.SellInputBoundary;
import use_case.Sell.SellInteractor;
import use_case.Sell.SellOutputBoundary;
import view.SellView;

public class SellUseCaseFactory {

    // Prevent instantiation
    private SellUseCaseFactory() {}

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
