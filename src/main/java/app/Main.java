package app;

import data_access.FileUserDataAccessObject;
import drivers.Finnhub;
import entities.CommonUser;
import entities.CommonUserFactory;
import entities.User;
import interface_adapters.Buy.BuyViewModel;
import interface_adapters.GetNews.GetNewsViewModel;
import interface_adapters.GetTransactionHistory.GetTransactionHistoryViewModel;
import interface_adapters.ResetBalance.ResetBalanceController;
import interface_adapters.Sell.SellViewModel;
import interface_adapters.Dashboard.DashboardViewModel;
import interface_adapters.ViewManagerModel;
import use_cases.APIAccessInterface;
import view.*;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        // Java swing setup code
        JFrame application = new JFrame("Stonks");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        CardLayout cardLayout = new CardLayout();
        JPanel views = new JPanel(cardLayout);
        application.add(views);

        // Common stuff
        ViewManagerModel viewManagerModel = new ViewManagerModel();
        new ViewManager(views, cardLayout, viewManagerModel);

        APIAccessInterface apiAccessInterface = new Finnhub();

        FileUserDataAccessObject fileUserDataAccessObject = new FileUserDataAccessObject(
                "./user.json",
                new CommonUserFactory()
        );
        User user = new CommonUser();
        fileUserDataAccessObject.save();

        // All view models (since multiple views can reference view models that do not belong to them
        DashboardViewModel dashboardViewModel = new DashboardViewModel();
        BuyViewModel buyViewModel = new BuyViewModel();
        SellViewModel sellViewModel = new SellViewModel();
        GetNewsViewModel getNewsViewModel = new GetNewsViewModel();
        ResetBalanceController resetBalanceController = new ResetBalanceController();
        GetTransactionHistoryViewModel getTransactionHistoryViewModel = new GetTransactionHistoryViewModel();


        // Dashboard view init
        DashboardView dashboardView = DashboardUseCaseFactory.create(
                viewManagerModel,
                dashboardViewModel,
                buyViewModel,
                sellViewModel,
                getNewsViewModel,
                resetBalanceController,
                getTransactionHistoryViewModel,
                fileUserDataAccessObject,
                apiAccessInterface
        );
        views.add(dashboardView, dashboardView.viewName);

        // Buy view init
        BuyView buyView = BuyStockUseCaseFactory.create(
                viewManagerModel,
                buyViewModel,
                dashboardViewModel,
                fileUserDataAccessObject,
                apiAccessInterface
        );
        views.add(buyView, buyView.viewName);

        // Sell view init
        SellView sellView = SellStockUseCaseFactory.create(
                viewManagerModel,
                sellViewModel,
                dashboardViewModel,
                fileUserDataAccessObject,
                apiAccessInterface
        );
        views.add(sellView, sellView.viewName);

        // Get News view init
        GetNewsView getNewsView = GetNewsUseCaseFactory.create(
                viewManagerModel,
                getNewsViewModel,
                dashboardViewModel,
                fileUserDataAccessObject,
                apiAccessInterface
        );
        views.add(getNewsView, getNewsView.viewName);

        // Set start screen to dashboard and display application
        viewManagerModel.setActiveView(dashboardViewModel.getViewName());
        dashboardViewModel.firePropertyChanged();
        viewManagerModel.firePropertyChanged();

        application.pack();
        application.setVisible(true);
    }
}
