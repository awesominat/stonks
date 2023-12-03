package interface_adapter.Dashboard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DashboardState {
    private HashMap<String, Double> userStats;
    private HashMap<String, Double> ownedStocksTable;
    private HashMap<String, List<Double>> stocksPriceInformationTable;
    private Boolean refreshPressed;
    private Boolean resetPressed;

//    public DashboardState(
//            HashMap<String, Double> userStats,
//            HashMap<String, Double> ownedStocksTable,
//            HashMap<String, List<Double>> stocksPriceInformationTable,
//            Boolean refreshPressed,
//            Boolean resetPressed
//    ) {
//        this.userStats = userStats;
//        this.ownedStocksTable = ownedStocksTable;
//        this.stocksPriceInformationTable = stocksPriceInformationTable;
//        this.refreshPressed = refreshPressed;
//        this.resetPressed = resetPressed;
//    }

    public HashMap<String, Double> getOwnedStocksTable() {
        return ownedStocksTable;
    }

    public void setOwnedStocksTable(HashMap<String, Double> ownedStocksTable) {
        this.ownedStocksTable = ownedStocksTable;
    }

    public HashMap<String, List<Double>> getStocksPriceInformationTable() {
        return stocksPriceInformationTable;
    }

    public void setStocksPriceInformationTable(HashMap<String, List<Double>> stocksPriceInformationTable) {
        this.stocksPriceInformationTable = stocksPriceInformationTable;
    }

    public HashMap<String, Double> getUserStats() {
        return userStats;
    }

    public void setUserStats(HashMap<String, Double> userStats) {
        this.userStats = userStats;
    }

    public Boolean getRefreshPressed() {
        return refreshPressed;
    }

    public void setRefreshPressed(Boolean refreshPressed) {
        this.refreshPressed = refreshPressed;
    }

    public Boolean getResetPressed() {
        return resetPressed;
    }

    public void setResetPressed(Boolean resetPressed) {
        this.resetPressed = resetPressed;
    }

    public DashboardState() {
        this.userStats = new HashMap<>();
        this.ownedStocksTable = new HashMap<>();
        this.stocksPriceInformationTable = new HashMap<>();
        this.refreshPressed = false;
        this.resetPressed = false;
    }

}
