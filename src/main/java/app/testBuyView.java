package app;

import data_access.FileUserDataAccessObject;
import drivers.Finnhub;
import entities.CommonUserFactory;
import interface_adapters.Buy.BuyController;
import interface_adapters.Buy.BuyPresenter;
import interface_adapters.Buy.BuyViewModel;
import interface_adapters.Dashboard.DashboardViewModel;
import interface_adapters.ViewManagerModel;
import use_cases.APIAccessInterface;
import use_cases.Buy.BuyInteractor;
import use_cases.Buy.BuyOutputBoundary;
import view.BuyView;
import view.ViewManager;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class testBuyView {
    public static void main(String[] args) throws IOException {
        JFrame application = new JFrame("Buy Stock");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        CardLayout cardLayout = new CardLayout();

        JPanel views = new JPanel(cardLayout);
        application.add(views);

        ViewManagerModel viewManagerModel = new ViewManagerModel();
        new ViewManager(views, cardLayout, viewManagerModel);
        BuyViewModel buyViewModel = new BuyViewModel();
        DashboardViewModel dashboardViewModel = new DashboardViewModel();

        FileUserDataAccessObject userDataAccessObject = new FileUserDataAccessObject("./user.json", new CommonUserFactory());
        userDataAccessObject.save();

        APIAccessInterface driverAPI = new Finnhub();

        BuyOutputBoundary buyPresenter = new BuyPresenter(viewManagerModel, buyViewModel);

        BuyInteractor buyInteractor = new BuyInteractor(userDataAccessObject, buyPresenter, driverAPI);

        BuyController buyController = new BuyController(buyInteractor);

        BuyView buyView = new BuyView(buyController, buyViewModel, viewManagerModel, dashboardViewModel);

        views.add(buyView, buyView.viewName);

        viewManagerModel.setActiveView(buyView.viewName);
        viewManagerModel.firePropertyChanged();

        application.pack();
        application.setVisible(true);
    }
}
