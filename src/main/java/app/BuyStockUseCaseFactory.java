package app;

import interface_adapters.Buy.BuyController;
import interface_adapters.Buy.BuyPresenter;
import interface_adapters.Buy.BuyViewModel;
import interface_adapters.Dashboard.DashboardViewModel;
import interface_adapters.ViewManagerModel;
import use_cases.APIAccessInterface;
import use_cases.Buy.BuyDataAccessInterface;
import use_cases.Buy.BuyInputBoundary;
import use_cases.Buy.BuyInteractor;
import use_cases.Buy.BuyOutputBoundary;
import view.BuyView;

public class BuyStockUseCaseFactory {

    // Prevent instantiation
    private BuyStockUseCaseFactory() {}

    public static BuyView create(
            ViewManagerModel viewManagerModel,
            BuyViewModel buyViewModel,
            DashboardViewModel dashboardViewModel,
            BuyDataAccessInterface buyDataAccessInterface,
            APIAccessInterface apiAccessInterface
    ) {
        BuyController buyController = createBuyStockUseCase(
                viewManagerModel,
                buyViewModel,
                buyDataAccessInterface,
                apiAccessInterface
        );
        return new BuyView(
                buyController,
                buyViewModel,
                viewManagerModel,
                dashboardViewModel
        );
    }

    private static BuyController createBuyStockUseCase(
            ViewManagerModel viewManagerModel,
            BuyViewModel buyViewModel,
            BuyDataAccessInterface buyDataAccessInterface,
            APIAccessInterface apiAccessInterface
    ) {
        BuyOutputBoundary buyPresenter = new BuyPresenter(viewManagerModel, buyViewModel);
        BuyInputBoundary buyInteractor = new BuyInteractor(
                buyDataAccessInterface,
                buyPresenter,
                apiAccessInterface
        );
        return new BuyController(buyInteractor);
    }

}
