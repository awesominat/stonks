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
        BuyViewModel buyViewModel = new BuyViewModel();
        DashboardViewModel dashboardViewModel = new DashboardViewModel();

        FileUserDataAccessObject userDataAccessObject = new FileUserDataAccessObject(
                "./user.json",
                new CommonUserFactory()
        );
        userDataAccessObject.save();

        APIAccessInterface driverAPI = new Finnhub();

        BuyOutputBoundary buyPresenter = new BuyPresenter(viewManagerModel, buyViewModel);

        BuyInteractor buyInteractor = new BuyInteractor(userDataAccessObject,
                buyPresenter,
                driverAPI);

        BuyController buyController = new BuyController(buyInteractor);
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

//        GetTransactionHistoryViewModel getTransactionHistoryViewModel,
//        GetTransactionHistoryController getTransactionHistoryController,
//        ViewManagerModel viewManagerModel, DashboardViewModel dashboardViewModel

        TransactionHistoryView buyView = new TransactionHistoryView(
                getTransactionHistoryController,
                getTransactionHistoryViewModel,
                viewManagerModel,
                dashboardViewModel
        );

        views.add(buyView, buyView.viewName);

        viewManagerModel.setActiveView(buyView.viewName);
        viewManagerModel.firePropertyChanged();

        application.pack();
        application.setVisible(true);

//        CardLayout cardLayout = new CardLayout();
//
//        // The various View objects. Only one view is visible at a time.
//        JPanel views = new JPanel(cardLayout);
//        application.add(views);
//
//        // This keeps track of and manages which view is currently showing.
//        ViewManagerModel viewManagerModel = new ViewManagerModel();
//        new ViewManager(views, cardLayout, viewManagerModel);
//
//        // The data for the views, such as username and password, are in the ViewModels.
//        // This information will be changed by a presenter object that is reporting the
//        // results from the use case. The ViewModels are observable, and will
//        // be observed by the Views.
//        GetTransactionHistoryViewModel transactionHistoryViewModel = new GetTransactionHistoryViewModel();
//
//        FileUserDataAccessObject userDataAccessObject = new FileUserDataAccessObject("./user.json", new CommonUserFactory());
//        User newUser = new CommonUser();
//        userDataAccessObject.save();
//
//
//        APIAccessInterface driverAPI = new Finnhub();
//
//        BuyInputData buyInputData = new BuyInputData(10.0, "AAPL");
//        BuyInputData buyInputData2 = new BuyInputData(10.0, "MSFT");
//        BuyViewModel buyViewModel = new BuyViewModel();
//
//
//        BuyOutputBoundary buyPresenter = new BuyPresenter(viewManagerModel, buyViewModel);
//        BuyInteractor buyInteractor = new BuyInteractor(userDataAccessObject, buyPresenter, driverAPI);
//        BuyController buyController = new BuyController(buyInteractor);
//
//        buyInteractor.execute(buyInputData);
//        buyInteractor.execute(buyInputData2);
//
//        DashboardViewModel dashboardViewModel = new DashboardViewModel();
//        DashboardOutputBoundary dashboardPresenter = new DashboardPresenter(viewManagerModel, dashboardViewModel);
//        DashboardInteractor dashboardInteractor = new DashboardInteractor(userDataAccessObject, dashboardPresenter, driverAPI);
//        dashboardInteractor.execute();
//
////        SellOutputBoundary sellPresenter = new SellPresenter(viewManagerModel, sellViewModel, dashboardViewModel);
////        SellInputBoundary sellInteractor = new SellInteractor(userDataAccessObject, sellPresenter, driverAPI);
////        SellController sellController = new SellController(sellInteractor);
//
//        GetTransactionHistoryOutputBoundary getTransactionHistoryPresenter = new GetTransactionHistoryPresenter(viewManagerModel,
//                transactionHistoryViewModel, dashboardViewModel);
//        GetTransactionHistoryInputBoundary getTransactionHistoryInteractor = new GetTransactionHistoryInteractor(
//                userDataAccessObject, getTransactionHistoryPresenter);
//        GetTransactionHistoryController getTransactionHistoryController = new GetTransactionHistoryController(getTransactionHistoryInteractor);
//
//
////        SellView sellView = new SellView(sellViewModel, sellController, viewManagerModel, dashboardViewModel);
////        views.add(sellView, sellView.viewName);
//
//        TransactionHistoryView historyView = new TransactionHistoryView(transactionHistoryViewModel, getTransactionHistoryController, viewManagerModel, dashboardViewModel);
//        views.add(historyView ,historyView.viewName);
//        viewManagerModel.setActiveView(historyView.viewName);
//        viewManagerModel.firePropertyChanged();
//
//        application.pack();
//        application.setVisible(true);
    }
}

