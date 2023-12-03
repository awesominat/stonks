package use_case.Dashboard;

import java.util.HashMap;
import java.util.List;

public class DashboardOutputData {
    HashMap<String, Double> userStats;
    HashMap<String, List<Double>> stockPriceInformationTable;
    HashMap<String, Double> ownedStocksInformationTable;
    Boolean refreshPressed;

    /**
     * First constructor for dashboard output data. This is to be used when
     * the execution type is refresh
     *
     * @param stockPriceInformationTable    a map from stock tickers to current price information
     *                                      of those stocks
     */
    public DashboardOutputData(HashMap<String, List<Double>> stockPriceInformationTable) {
        this.stockPriceInformationTable = stockPriceInformationTable;
        this.refreshPressed = true;
        this.userStats = null;
        this.ownedStocksInformationTable = null;
    }

    /**
     * Second constructor for the dashboard output data. This is to be used when the
     * execution type is not refresh.
     *
     * @param userStats                     the current user stats table
     * @param ownedStocksInformationTable   a map from the ticker to the amount of the stock owned by the user
     */
    public DashboardOutputData(
            HashMap<String, Double> userStats,
            HashMap<String, Double> ownedStocksInformationTable
    ) {
        this.userStats = userStats;
        this.ownedStocksInformationTable = ownedStocksInformationTable;
        this.refreshPressed = false;
        this.stockPriceInformationTable = null;
    }

    public Boolean getRefreshPressed() {
        return refreshPressed;
    }

    public HashMap<String, Double> getOwnedStocksInformationTable() {
        return ownedStocksInformationTable;
    }

    public HashMap<String, List<Double>> getStockPriceInformationTable() {
        return stockPriceInformationTable;
    }

    public HashMap<String, Double> getUserStats() {
        return userStats;
    }
}
