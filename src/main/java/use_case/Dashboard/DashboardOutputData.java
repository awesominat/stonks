package use_case.Dashboard;

import java.util.HashMap;
import java.util.List;

public class DashboardOutputData {
    HashMap<String, Double> userStats;
    HashMap<String, List<Double>> stockPriceInformationTable;
    HashMap<String, Double> ownedStocksInformationTable;
    Boolean refreshPressed;

    public DashboardOutputData(HashMap<String, List<Double>> stockPriceInformationTable) {
        this.stockPriceInformationTable = stockPriceInformationTable;
        this.refreshPressed = true;
        this.userStats = null;
        this.ownedStocksInformationTable = null;
    }

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

    public void setRefreshPressed(Boolean refreshPressed) {
        this.refreshPressed = refreshPressed;
    }


    public HashMap<String, Double> getOwnedStocksInformationTable() {
        return ownedStocksInformationTable;
    }

    public void setOwnedStocksInformationTable(HashMap<String, Double> ownedStocksInformationTable) {
        this.ownedStocksInformationTable = ownedStocksInformationTable;
    }

    public HashMap<String, List<Double>> getStockPriceInformationTable() {
        return stockPriceInformationTable;
    }

    public void setStockPriceInformationTable(HashMap<String, List<Double>> stockPriceInformationTable) {
        this.stockPriceInformationTable = stockPriceInformationTable;
    }

    public HashMap<String, Double> getUserStats() {
        return userStats;
    }

    public void setUserStats(HashMap<String, Double> userStats) {
        this.userStats = userStats;
    }
}
