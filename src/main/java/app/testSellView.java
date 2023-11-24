package app;

import data_access.FileUserDataAccessObject;
import drivers.Finnhub;
import entities.CommonUser;
import entities.CommonUserFactory;
import interface_adapters.Buy.BuyPresenter;
import interface_adapters.Buy.BuyState;
import interface_adapters.Buy.BuyViewModel;
import interface_adapters.Dashboard.DashboardPresenter;
import interface_adapters.Dashboard.DashboardViewModel;
import interface_adapters.Sell.SellController;
import use_cases.Buy.BuyInputData;
import use_cases.Buy.BuyInteractor;
import use_cases.Buy.BuyOutputBoundary;
import use_cases.Dashboard.DashboardDataAccessInterface;
import use_cases.Dashboard.DashboardInteractor;
import use_cases.Dashboard.DashboardOutputBoundary;
import use_cases.Sell.SellOutputBoundary;
import interface_adapters.Sell.SellPresenter;
import interface_adapters.Sell.SellViewModel;
import interface_adapters.ViewManagerModel;
import use_cases.APIAccessInterface;
import use_cases.Sell.SellInputBoundary;
import use_cases.Sell.SellInteractor;
import view.BuyView;
import view.ViewManager;
import javax.swing.*;
import java.awt.*;
import view.SellView;
import entities.User;
import java.io.IOException;

public class testSellView {
    public static void main(String[] args) throws IOException {
        // Build the main program window, the main panel containing the
        // various cards, and the layout, and stitch them together.

        // The main application window.
        JFrame application = new JFrame("Sell View");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        CardLayout cardLayout = new CardLayout();

        // The various View objects. Only one view is visible at a time.
        JPanel views = new JPanel(cardLayout);
        application.add(views);

        // This keeps track of and manages which view is currently showing.
        ViewManagerModel viewManagerModel = new ViewManagerModel();
        new ViewManager(views, cardLayout, viewManagerModel);

        // The data for the views, such as username and password, are in the ViewModels.
        // This information will be changed by a presenter object that is reporting the
        // results from the use case. The ViewModels are observable, and will
        // be observed by the Views.
        SellViewModel sellViewModel = new SellViewModel();

        FileUserDataAccessObject userDataAccessObject = new FileUserDataAccessObject("./user.json", new CommonUserFactory());
        User newUser = new CommonUser();
        userDataAccessObject.save();


        APIAccessInterface driverAPI = new Finnhub();

        BuyInputData buyInputData = new BuyInputData(10.0, "AAPL");
        BuyInputData buyInputData2 = new BuyInputData(10.0, "MSFT");
        BuyViewModel buyViewModel = new BuyViewModel();
        BuyView buyView = new BuyView(buyViewModel);
        BuyOutputBoundary buyPresenter = new BuyPresenter(viewManagerModel, buyView);
        BuyInteractor buyInteractor = new BuyInteractor(userDataAccessObject, buyPresenter, driverAPI);
        buyInteractor.execute(buyInputData);
        buyInteractor.execute(buyInputData2);

        DashboardViewModel dashboardViewModel = new DashboardViewModel();
        DashboardOutputBoundary dashboardPresenter = new DashboardPresenter(viewManagerModel, dashboardViewModel);
        DashboardInteractor dashboardInteractor = new DashboardInteractor(userDataAccessObject, dashboardPresenter, driverAPI);
        dashboardInteractor.execute();

        SellOutputBoundary sellPresenter = new SellPresenter(viewManagerModel, sellViewModel, dashboardViewModel);
        SellInputBoundary sellInteractor = new SellInteractor(userDataAccessObject, sellPresenter, driverAPI);
        SellController sellController = new SellController(sellInteractor);

        SellView sellView = new SellView(sellViewModel, sellController, viewManagerModel, dashboardViewModel);
        views.add(sellView, sellView.viewName);

        viewManagerModel.setActiveView(sellView.viewName);
        viewManagerModel.firePropertyChanged();

        application.pack();
        application.setVisible(true);
    }
}
