package app;

import data_access.FileUserDataAccessObject;
import drivers.Finnhub;
import entities.CommonUserFactory;
import interface_adapters.Buy.BuyViewModel;
import interface_adapters.Dashboard.DashboardController;
import interface_adapters.Dashboard.DashboardPresenter;
import interface_adapters.Dashboard.DashboardViewModel;
import interface_adapters.GetNews.GetNewsViewModel;
import interface_adapters.GetTransactionHistory.GetTransactionHistoryViewModel;
import interface_adapters.ResetBalance.ResetBalanceController;
import interface_adapters.ResetBalance.ResetBalancePresenter;
import interface_adapters.Sell.SellViewModel;
import interface_adapters.ViewManagerModel;
import use_cases.APIAccessInterface;
import use_cases.Dashboard.DashboardInteractor;
import use_cases.Dashboard.DashboardOutputBoundary;
import use_cases.ResetBalance.ResetBalanceInteractor;
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

        // Initialize all the necessary objects to simulate the DashboardView.
        DashboardOutputBoundary dashboardPresenter = new DashboardPresenter(viewManagerModel, dashboardViewModel);
        DashboardInteractor dashboardInteractor = new DashboardInteractor(userDataAccessObject, dashboardPresenter, driverAPI);
        DashboardController dashboardController = new DashboardController(dashboardInteractor);

        // DashboardView requires a ResetBalanceController, the simulation of which requires a bunch of other objects.
        ResetBalancePresenter resetBalancePresenter = new ResetBalancePresenter(viewManagerModel);
        ResetBalanceInteractor resetBalanceInteractor = new ResetBalanceInteractor(userDataAccessObject, resetBalancePresenter, driverAPI);
        ResetBalanceController resetBalanceController = new ResetBalanceController(resetBalanceInteractor);

        // Initialize necessary ViewModels.
        BuyViewModel buyViewModel = new BuyViewModel();
        SellViewModel sellViewModel = new SellViewModel();
        GetNewsViewModel getNewsViewModel = new GetNewsViewModel();
        GetTransactionHistoryViewModel getTransactionHistoryViewModel = new GetTransactionHistoryViewModel();

        // Create the DashboardView to be simulated.
        DashboardView dashboardView = new DashboardView(
                dashboardViewModel,
                dashboardController,
                buyViewModel,
                sellViewModel,
                getNewsViewModel,
                resetBalanceController,
                getTransactionHistoryViewModel,
                viewManagerModel
        );

        views.add(dashboardView, dashboardView.viewName);

        viewManagerModel.setActiveView(dashboardView.viewName);
        viewManagerModel.firePropertyChanged();

        application.pack();
        application.setVisible(true);
    }

}
