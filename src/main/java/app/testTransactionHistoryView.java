package app;

import data_access.FileUserDataAccessObject;
import drivers.Finnhub;
import entities.CommonUser;
import entities.CommonUserFactory;
import entities.TransactionHistory;
import entities.User;
import interface_adapters.Buy.BuyController;
import interface_adapters.Buy.BuyPresenter;
import interface_adapters.Buy.BuyViewModel;
import interface_adapters.Dashboard.DashboardPresenter;
import interface_adapters.Dashboard.DashboardViewModel;
import interface_adapters.GetTransactionHistory.GetTransactionHistoryController;
import interface_adapters.GetTransactionHistory.GetTransactionHistoryPresenter;
import interface_adapters.GetTransactionHistory.GetTransactionHistoryViewModel;
import interface_adapters.Sell.SellController;
import interface_adapters.Sell.SellPresenter;
import interface_adapters.Sell.SellViewModel;
import interface_adapters.ViewManagerModel;
import use_cases.APIAccessInterface;
import use_cases.Buy.BuyInputData;
import use_cases.Buy.BuyInteractor;
import use_cases.Buy.BuyOutputBoundary;
import use_cases.Dashboard.DashboardInteractor;
import use_cases.Dashboard.DashboardOutputBoundary;
import use_cases.GetTransactionHistory.GetTransactionHistoryInputBoundary;
import use_cases.GetTransactionHistory.GetTransactionHistoryInteractor;
import use_cases.GetTransactionHistory.GetTransactionHistoryOutputBoundary;
import use_cases.Sell.SellInputBoundary;
import use_cases.Sell.SellInteractor;
import use_cases.Sell.SellOutputBoundary;
import view.BuyView;
import view.SellView;
import view.TransactionHistoryView;
import view.ViewManager;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class testTransactionHistoryView {
    public static void main(String[] args) throws IOException {
        // Build the main program window, the main panel containing the
        // various cards, and the layout, and stitch them together.

        // The main application window.
        JFrame application = new JFrame("View Transaction History");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


        CardLayout cardLayout = new CardLayout();

        JPanel views = new JPanel(cardLayout);
        application.add(views);

        ViewManagerModel viewManagerModel = new ViewManagerModel();
        new ViewManager(views, cardLayout, viewManagerModel);
        DashboardViewModel dashboardViewModel = new DashboardViewModel();

        FileUserDataAccessObject userDataAccessObject = new FileUserDataAccessObject(
                "./user.json",
                new CommonUserFactory()
        );
        userDataAccessObject.save();


        GetTransactionHistoryViewModel getTransactionHistoryViewModel = new GetTransactionHistoryViewModel();

        GetTransactionHistoryPresenter getTransactionHistoryPresenter = new GetTransactionHistoryPresenter(
                viewManagerModel,
                getTransactionHistoryViewModel,
                dashboardViewModel
        );

        GetTransactionHistoryInteractor getTransactionHistoryInteractor = new GetTransactionHistoryInteractor(
                userDataAccessObject,
                getTransactionHistoryPresenter
        );

        GetTransactionHistoryController getTransactionHistoryController = new GetTransactionHistoryController(
                getTransactionHistoryInteractor
        );

        TransactionHistoryView transactionHistoryView = new TransactionHistoryView(
                getTransactionHistoryController,
                getTransactionHistoryViewModel,
                viewManagerModel,
                dashboardViewModel
        );

        String viewName = transactionHistoryView.viewName;

        views.add(transactionHistoryView, viewName);

        viewManagerModel.setActiveView(viewName);

        viewManagerModel.firePropertyChanged();

        application.pack();
        application.setVisible(true);
    }
}

