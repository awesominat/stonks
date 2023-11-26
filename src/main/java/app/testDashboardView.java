package app;

import data_access.FileUserDataAccessObject;
import drivers.Finnhub;
import entities.CommonUserFactory;
import interface_adapters.Buy.BuyViewModel;
import interface_adapters.Dashboard.DashboardController;
import interface_adapters.Dashboard.DashboardPresenter;
import interface_adapters.Dashboard.DashboardViewModel;
import interface_adapters.GetNews.GetNewsViewModel;
import interface_adapters.ResetBalance.ResetBalanceViewModel;
import interface_adapters.Sell.SellViewModel;
import interface_adapters.ViewManagerModel;
import use_cases.APIAccessInterface;
import use_cases.Buy.BuyInteractor;
import use_cases.Dashboard.DashboardInteractor;
import use_cases.Dashboard.DashboardOutputBoundary;
import view.DashboardView;
import view.ViewManager;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class testDashboardView {
    public static void main(String[] args) throws IOException {
        JFrame application = new JFrame("Dashboard View");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        CardLayout cardLayout = new CardLayout();

        JPanel views = new JPanel(cardLayout);
        application.add(views);

        ViewManagerModel viewManagerModel = new ViewManagerModel();
        new ViewManager(views, cardLayout, viewManagerModel);
        DashboardViewModel dashboardViewModel = new DashboardViewModel();

        FileUserDataAccessObject userDataAccessObject = new FileUserDataAccessObject("./user.json", new CommonUserFactory());
        userDataAccessObject.save();

        APIAccessInterface driverAPI = new Finnhub();

        DashboardOutputBoundary dashboardPresenter = new DashboardPresenter(viewManagerModel, dashboardViewModel);

        DashboardInteractor dashboardInteractor = new DashboardInteractor(userDataAccessObject, dashboardPresenter, driverAPI);

        DashboardController dashboardController = new DashboardController(dashboardInteractor);

        DashboardView dashboardView = new DashboardView(
                dashboardViewModel,
                dashboardController,
                new BuyViewModel(),
                new SellViewModel(),
                new GetNewsViewModel(),
                new ResetBalanceViewModel(),
                viewManagerModel
        );

        views.add(dashboardView, dashboardView.viewName);

        viewManagerModel.setActiveView(dashboardView.viewName);
        viewManagerModel.firePropertyChanged();

        application.pack();
        application.setVisible(true);
    }
}
