package app;

import data_access.FileUserDataAccessObject;
import drivers.Finnhub;
import entities.CommonUser;
import entities.CommonUserFactory;
import entities.User;
import interface_adapters.Buy.BuyController;
import interface_adapters.Buy.BuyPresenter;
import interface_adapters.Buy.BuyViewModel;
import interface_adapters.Dashboard.DashboardPresenter;
import interface_adapters.Dashboard.DashboardViewModel;
import interface_adapters.Sell.SellViewModel;
import interface_adapters.ViewManagerModel;
import use_cases.APIAccessInterface;
import use_cases.Buy.BuyInputData;
import use_cases.Buy.BuyInteractor;
import use_cases.Buy.BuyOutputBoundary;
import use_cases.Dashboard.DashboardInputData;
import use_cases.Dashboard.DashboardInteractor;
import use_cases.Dashboard.DashboardOutputBoundary;
import view.SellView;
import view.ViewManager;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class testSellView {
    public static void main(String[] args) throws IOException {
        // Java swing init stuff
        JFrame application = new JFrame("Sell View");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        CardLayout cardLayout = new CardLayout();
        JPanel views = new JPanel(cardLayout);
        application.add(views);

        // SellView init stuff
        ViewManagerModel viewManagerModel = new ViewManagerModel();
        SellViewModel sellViewModel = new SellViewModel();
        DashboardViewModel dashboardViewModel = new DashboardViewModel();

        FileUserDataAccessObject userDataAccessObject = new FileUserDataAccessObject("./user.json", new CommonUserFactory());
        User newUser = new CommonUser();
        userDataAccessObject.save();

        APIAccessInterface driverAPI = new Finnhub();
        SellView sellView = SellStockUseCaseFactory.create(
                viewManagerModel,
                sellViewModel,
                dashboardViewModel,
                userDataAccessObject,
                driverAPI
        );
        new ViewManager(views, cardLayout, viewManagerModel);

        // initialize buy use case stuff for testing purposes
        BuyViewModel buyViewModel = new BuyViewModel();
        BuyOutputBoundary buyPresenter = new BuyPresenter(viewManagerModel, buyViewModel);
        BuyInteractor buyInteractor = new BuyInteractor(userDataAccessObject, buyPresenter, driverAPI);
        BuyController buyController = new BuyController(buyInteractor);

        // simulating buying two stocks
        BuyInputData buyInputDataAPPL = new BuyInputData(10.0, "AAPL");
        BuyInputData buyInputDataMSFT = new BuyInputData(10.0, "MSFT");
        buyInteractor.execute(buyInputDataAPPL);
        buyInteractor.execute(buyInputDataMSFT);


        // Initialize dashboard use case stuff because dashboard state must be updated for stocks to show up on sell page
        DashboardOutputBoundary dashboardPresenter = new DashboardPresenter(viewManagerModel, dashboardViewModel);
        DashboardInteractor dashboardInteractor = new DashboardInteractor(userDataAccessObject, dashboardPresenter, driverAPI);
        dashboardInteractor.execute(new DashboardInputData());

        views.add(sellView, sellView.viewName);
        viewManagerModel.setActiveView(sellView.viewName);
        sellViewModel.firePropertyChanged();
        viewManagerModel.firePropertyChanged();

        application.pack();
        application.setVisible(true);
    }
}