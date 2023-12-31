package app;

import data_access.FileUserDataAccessObject;
import driver.Finnhub;
import entity.CacheStockInformation;
import entity.CommonUserFactory;
import interface_adapter.Buy.BuyViewModel;
import interface_adapter.CacheStockInformation.CacheStockInformationController;
import interface_adapter.GetNews.GetNewsViewModel;
import interface_adapter.GetTransactionHistory.GetTransactionHistoryViewModel;
import interface_adapter.ResetBalance.ResetBalanceController;
import interface_adapter.ResetBalance.ResetBalancePresenter;
import interface_adapter.Sell.SellViewModel;
import interface_adapter.Dashboard.DashboardViewModel;
import interface_adapter.ViewManagerModel;
import use_case.APIAccessInterface;
import use_case.ResetBalance.ResetBalanceInteractor;
import view.*;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public class Main {

    public static void main(String[] args) throws IOException {
        JFrame application = new JFrame("Stonks");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        CardLayout cardLayout = new CardLayout();
        JPanel views = new JPanel(cardLayout);
        application.add(views);
        ViewManagerModel viewManagerModel = new ViewManagerModel();
        new ViewManager(views, cardLayout, viewManagerModel);

        APIAccessInterface apiAccessInterface = new Finnhub();
        FileUserDataAccessObject fileUserDataAccessObject = new FileUserDataAccessObject(
                "./user.json",
                new CommonUserFactory()
        );
        fileUserDataAccessObject.save();
        CacheStockInformation cacheStockInformation = new CacheStockInformation();

        DashboardViewModel dashboardViewModel = new DashboardViewModel();
        BuyViewModel buyViewModel = new BuyViewModel();
        SellViewModel sellViewModel = new SellViewModel();
        GetNewsViewModel getNewsViewModel = new GetNewsViewModel();
        GetTransactionHistoryViewModel getTransactionHistoryViewModel = new GetTransactionHistoryViewModel();

        ResetBalancePresenter resetBalancePresenter = new ResetBalancePresenter(
                viewManagerModel,
                dashboardViewModel
        );
        ResetBalanceInteractor resetBalanceInteractor = new ResetBalanceInteractor(
                fileUserDataAccessObject,
                resetBalancePresenter,
                apiAccessInterface
        );
        ResetBalanceController resetBalanceController = new ResetBalanceController(resetBalanceInteractor);

        DashboardView dashboardView = DashboardUseCaseFactory.create(
                viewManagerModel,
                dashboardViewModel,
                buyViewModel,
                sellViewModel,
                getNewsViewModel,
                resetBalanceController,
                getTransactionHistoryViewModel,
                fileUserDataAccessObject,
                cacheStockInformation,
                apiAccessInterface
        );
        views.add(dashboardView, dashboardView.viewName);

        BuyView buyView = BuyUseCaseFactory.create(
                viewManagerModel,
                buyViewModel,
                dashboardViewModel,
                fileUserDataAccessObject,
                apiAccessInterface
        );
        views.add(buyView, buyView.viewName);

        SellView sellView = SellUseCaseFactory.create(
                viewManagerModel,
                sellViewModel,
                dashboardViewModel,
                fileUserDataAccessObject,
                apiAccessInterface
        );
        views.add(sellView, sellView.viewName);

        GetNewsView getNewsView = GetNewsUseCaseFactory.create(
                viewManagerModel,
                getNewsViewModel,
                dashboardViewModel,
                apiAccessInterface
        );
        views.add(getNewsView, getNewsView.viewName);

        TransactionHistoryView transactionHistoryView = GetTransactionHistoryUseCaseFactory.create(
                viewManagerModel,
                getTransactionHistoryViewModel,
                dashboardViewModel,
                fileUserDataAccessObject,
                apiAccessInterface
        );
        views.add(transactionHistoryView, transactionHistoryView.viewName);

        CacheStockInformationController cacheController = CacheInformationUseCaseFactory.createCacheStockInformationUseCase(
                fileUserDataAccessObject,
                cacheStockInformation,
                apiAccessInterface
        );

        // Async function call for CacheStockInformation to begin
        CompletableFuture<Void> beginAsyncCaching = CompletableFuture.runAsync(() -> {
            try{
                while (true) {
                    Thread.sleep(15000);
                    cacheController.execute();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        viewManagerModel.setActiveView(dashboardViewModel.getViewName());
        dashboardViewModel.firePropertyChanged();
        viewManagerModel.firePropertyChanged();

        application.pack();
        application.setVisible(true);
    }

}
