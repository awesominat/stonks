package use_case.CacheStockInformation;

import entity.CacheStockInformation;
import entity.StockInformation;
import entity.User;
import use_case.APIAccessInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class CacheStockInformationInteractor implements CacheStockInformationInputBoundary {
    final CacheStockInformationDataAccessInterface userDataAccessObject;
    final CacheStockInformation cacheStockInformation;
    APIAccessInterface driverAPI;

    /**
     * Initializer for the cache interactor.
     *
     * @param userDataAccessObject      uses this to check which stocks are in the user portfolio
     * @param cacheStockInformation     updates this with new price info from API
     * @param driverAPI                 uses this to make API calls and grab updated stock prices
     */
    public CacheStockInformationInteractor(
            CacheStockInformationDataAccessInterface userDataAccessObject,
            CacheStockInformation cacheStockInformation,
            APIAccessInterface driverAPI
    ) {
        this.userDataAccessObject = userDataAccessObject;
        this.driverAPI = driverAPI;
        this.cacheStockInformation = cacheStockInformation;
    }

    /**
     * Has no input parameters, since it simply has to refresh the stock which is an attribute of
     * this class using the driverAPI, which is also an attribute.
     * Returns nothing and simply sets the stock information map in the cache. This triggers a
     * firePropertyChanged call.
     */
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
        cacheStockInformation.setStockInformationMap(stockInformationMap);
    }
}
