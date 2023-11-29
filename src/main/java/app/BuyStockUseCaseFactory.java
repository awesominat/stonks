package app;

import interface_adapter.Buy.BuyController;
import interface_adapter.Buy.BuyPresenter;
import interface_adapter.Buy.BuyViewModel;
import interface_adapter.Dashboard.DashboardViewModel;
import interface_adapter.ViewManagerModel;
import use_case.APIAccessInterface;
import use_case.Buy.BuyDataAccessInterface;
import use_case.Buy.BuyInputBoundary;
import use_case.Buy.BuyInteractor;
import use_case.Buy.BuyOutputBoundary;
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
