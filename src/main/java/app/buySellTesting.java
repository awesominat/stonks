package app;

import data_access.FileUserDataAccessObject;
import drivers.Finnhub;
import entities.CommonUser;
import entities.CommonUserFactory;
import entities.User;
import use_cases.Buy.BuyOutputBoundary;
import interface_adapters.Buy.BuyPresenter;
import interface_adapters.Buy.BuyViewModel;
import interface_adapters.Dashboard.DashboardViewModel;
import use_cases.Sell.SellOutputBoundary;
import interface_adapters.Sell.SellPresenter;
import interface_adapters.Sell.SellViewModel;
import interface_adapters.ViewManagerModel;
import use_cases.APIAccessInterface;
import use_cases.Buy.BuyInputData;
import use_cases.Buy.BuyInteractor;
import use_cases.Sell.SellInputData;
import use_cases.Sell.SellInteractor;
import view.BuyView;

import java.io.IOException;

public class buySellTesting {
    public static void main(String[] args) throws IOException, InterruptedException {
        FileUserDataAccessObject userDataAccessObject = new FileUserDataAccessObject("./user.json", new CommonUserFactory());

        // this code should purchase 10 pieces of apple stock, ticker AAPL
        // then it sells 5 as one transaction
        // and sells off the final 5, since this selling was all done instantaneously, the price of AAPL didn't change
        // so you're back at 10k, your starting balance
        User newUser = new CommonUser();
        userDataAccessObject.save();

        BuyInputData buyInputData = new BuyInputData(10.0, "AAPL", "zain");
        BuyOutputBoundary buyPresenter = new BuyPresenter(new ViewManagerModel(), new BuyView(new BuyViewModel()));
        SellOutputBoundary sellPresenter = new SellPresenter(new ViewManagerModel(), new SellViewModel(), new DashboardViewModel());
        APIAccessInterface Finnhub = new Finnhub();


        BuyInteractor buyInteractor = new BuyInteractor(userDataAccessObject, buyPresenter, Finnhub);
        buyInteractor.execute(buyInputData);

        System.out.println(newUser.getBalance());
        System.out.println(newUser.getHistory());

        SellInputData sellInputData = new SellInputData(5.0, "AAPL", "zain");
        SellInteractor sellInteractor = new SellInteractor(userDataAccessObject, sellPresenter, Finnhub);
        sellInteractor.execute(sellInputData);

        System.out.println(newUser.getBalance());
        System.out.println(newUser.getHistory());

        SellInputData sellInputData2 = new SellInputData(5.0, "AAPL", "zain");
        SellInteractor sellInteractor2 = new SellInteractor(userDataAccessObject, sellPresenter, Finnhub);
        sellInteractor2.execute(sellInputData2);

        System.out.println(newUser.getBalance());
        System.out.println(newUser.getHistory());
    }
}
