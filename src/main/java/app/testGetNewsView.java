package app;

//import data_access.FileUserDataAccessObject;
import drivers.Finnhub;
//import entities.CommonUserFactory;
import interface_adapters.Dashboard.DashboardViewModel;
import interface_adapters.GetNews.GetNewsController;
import interface_adapters.GetNews.GetNewsPresenter;
import interface_adapters.GetNews.GetNewsViewModel;
import interface_adapters.ViewManagerModel;
import use_cases.APIAccessInterface;
import use_cases.GetNews.GetNewsInteractor;
import use_cases.GetNews.GetNewsOutputBoundary;
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

//        FileUserDataAccessObject userDataAccessObject = new FileUserDataAccessObject("./user.json", new CommonUserFactory());
//        userDataAccessObject.save();

        APIAccessInterface driverAPI = new Finnhub();

        // The error indicated below stems from something that is fixed by PR #105.
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
