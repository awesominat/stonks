package app;

//import data_access.FileUserDataAccessObject;
import driver.Finnhub;
//import entity.CommonUserFactory;
import interface_adapter.Dashboard.DashboardViewModel;
import interface_adapter.GetNews.GetNewsController;
import interface_adapter.GetNews.GetNewsPresenter;
import interface_adapter.GetNews.GetNewsViewModel;
import interface_adapter.ViewManagerModel;
import use_case.APIAccessInterface;
import use_case.GetNews.GetNewsInteractor;
import use_case.GetNews.GetNewsOutputBoundary;
import view.GetNewsView;
import view.ViewManager;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class testGetNewsView {

    public static void main(String[] args) throws IOException {
        JFrame application = new JFrame("Get News View");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        CardLayout cardLayout = new CardLayout();

        JPanel views = new JPanel(cardLayout);
        application.add(views);

        ViewManagerModel viewManagerModel = new ViewManagerModel();
        new ViewManager(views, cardLayout, viewManagerModel);
        GetNewsViewModel getNewsViewModel = new GetNewsViewModel();


        APIAccessInterface driverAPI = new Finnhub();

        GetNewsOutputBoundary getNewsPresenter = new GetNewsPresenter(viewManagerModel, getNewsViewModel);

        GetNewsInteractor getNewsInteractor = new GetNewsInteractor(getNewsPresenter, driverAPI);

        GetNewsController getNewsController = new GetNewsController(getNewsInteractor);

        GetNewsView getNewsView = new GetNewsView(
                getNewsViewModel,
                getNewsController,
                viewManagerModel,
                new DashboardViewModel()
        );

        views.add(getNewsView, getNewsView.viewName);

        viewManagerModel.setActiveView(getNewsView.viewName);
        viewManagerModel.firePropertyChanged();

        application.pack();
        application.setVisible(true);
    }

}
