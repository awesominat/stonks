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
            CacheStockInformationOutputBoundary cacheStockInformationPresenter,
            CacheStockInformationDataAccessInterface userDataAccessObject,
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
            try {
                StockInformation stockInformation = driverAPI.getCurrentStockInformation(stock);
                List<Double> stockInformationArrayList = new ArrayList<>();
                stockInformationArrayList.add(stockInformation.getCurrentPrice());
                stockInformationArrayList.add(stockInformation.getPriceChange());
                stockInformationArrayList.add(stockInformation.getPercentChange());
                stockInformationMap.put(
                        stock, stockInformationArrayList
                );
            } catch (APIAccessInterface.TickerNotFoundException ex) {
                user.removeFromPortfolio(stock);
            }
        }
        CacheStockInformationOutputData result = new CacheStockInformationOutputData(stockInformationMap);
        cacheStockInformationPresenter.updateCacheSuccess(result);

    }
}
