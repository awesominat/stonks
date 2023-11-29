package app;

import data_access.FileUserDataAccessObject;
import driver.Finnhub;
import entity.CommonUserFactory;
import entity.User;
import interface_adapter.Buy.BuyController;
import interface_adapter.Buy.BuyPresenter;
import interface_adapter.Buy.BuyViewModel;
import interface_adapter.Dashboard.DashboardViewModel;
import interface_adapter.Sell.SellPresenter;
import interface_adapter.Sell.SellViewModel;
import interface_adapter.ViewManagerModel;
import use_case.APIAccessInterface;
import use_case.Buy.BuyInputData;
import use_case.Buy.BuyInteractor;
import use_case.Buy.BuyOutputBoundary;
import use_case.Sell.SellInputData;
import use_case.Sell.SellInteractor;
import use_case.Sell.SellOutputBoundary;
import view.BuyView;

import java.io.IOException;

public class buySellTesting {
    public static void main(String[] args) throws IOException, InterruptedException {
        FileUserDataAccessObject userDataAccessObject = new FileUserDataAccessObject("./user.json", new CommonUserFactory());
        User newUser = userDataAccessObject.get();

        BuyInputData buyInputData = new BuyInputData(10.0, "AAPL");


        BuyViewModel buyViewModel = new BuyViewModel();
        BuyOutputBoundary buyPresenter = new BuyPresenter(new ViewManagerModel(), new BuyViewModel());
        APIAccessInterface Finnhub = new Finnhub();
        BuyInteractor buyInteractor = new BuyInteractor(userDataAccessObject, buyPresenter, Finnhub);
        BuyController buyController = new BuyController(buyInteractor);

        ViewManagerModel viewManagerModel = new ViewManagerModel();
        DashboardViewModel dashboardViewModel = new DashboardViewModel();

        BuyView buyView = new BuyView(buyController, buyViewModel, viewManagerModel, dashboardViewModel);

        SellOutputBoundary sellPresenter = new SellPresenter(new ViewManagerModel(), new SellViewModel(), new DashboardViewModel());
        buyInteractor.execute(buyInputData);

        System.out.println(newUser.getBalance());
        System.out.println(newUser.getHistory());

        SellInputData sellInputData = new SellInputData(5.0, "AAPL");
        SellInteractor sellInteractor = new SellInteractor(userDataAccessObject, sellPresenter, Finnhub);
        sellInteractor.execute(sellInputData);

        System.out.println(newUser.getBalance());
        System.out.println(newUser.getHistory());

        SellInputData sellInputData2 = new SellInputData(5.0, "AAPL");
        SellInteractor sellInteractor2 = new SellInteractor(userDataAccessObject, sellPresenter, Finnhub);
        sellInteractor2.execute(sellInputData2);

        System.out.println(newUser.getBalance());
        System.out.println(newUser.getHistory());
    }
}
