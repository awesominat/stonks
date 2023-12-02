package use_case.CacheStockInformation;

import entity.StockInformation;
import entity.User;
import use_case.APIAccessInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class CacheStockInformationInteractor implements CacheStockInformationInputBoundary {
    final CacheStockInformationDataAccessInterface userDataAccessObject;
    CacheStockInformationOutputBoundary cacheStockInformationPresenter;
    APIAccessInterface driverAPI;

    public CacheStockInformationInteractor(
            CacheStockInformationDataAccessInterface userDataAccessObject,
            CacheStockInformationOutputBoundary cacheStockInformationPresenter,
            APIAccessInterface driverAPI
    ) {
        this.userDataAccessObject = userDataAccessObject;
        this.driverAPI = driverAPI;
        this.cacheStockInformationPresenter = cacheStockInformationPresenter;
    }

    @Override
    public void execute() {
        User user = userDataAccessObject.get();
        Set<String> stocksOwned = user.getPortfolio().keySet();
        HashMap<String, List<Double>> stockInformationMap = new HashMap<>();
        for (String stock: stocksOwned) {
            StockInformation stockInformation = null;
            try {
                stockInformation = driverAPI.getCurrentStockInformation(stock);
            } catch (APIAccessInterface.TickerNotFoundException ex) {
                user.removeFromPortfolio(stock);
                continue;
            }
            List<Double> stockInformationArrayList = new ArrayList<>();
            stockInformationArrayList.add(stockInformation.getCurrentPrice());
            stockInformationArrayList.add(stockInformation.getPriceChange());
            stockInformationArrayList.add(stockInformation.getPercentChange());
            stockInformationMap.put(
                    stock, stockInformationArrayList
            );
        }
        CacheStockInformationOutputData result = new CacheStockInformationOutputData(stockInformationMap);
        cacheStockInformationPresenter.updateCacheSuccess(result);

    }
}
