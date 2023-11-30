package app;


import interface_adapter.Dashboard.DashboardViewModel;
import interface_adapter.GetNews.GetNewsController;
import interface_adapter.GetNews.GetNewsPresenter;
import interface_adapter.GetNews.GetNewsViewModel;
import interface_adapter.ViewManagerModel;
import use_case.APIAccessInterface;
import use_case.GetNews.*;
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
