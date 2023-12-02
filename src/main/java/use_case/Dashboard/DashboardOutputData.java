package use_case.Dashboard;

import java.util.HashMap;
import java.util.List;

public class DashboardOutputData {
    HashMap<String, Double> userStats;
    List<String> ownedTickers;
    List<Double> ownedAmounts;
    List<List<Double>> currentPriceStats;
    Boolean refreshPressed;

    public DashboardOutputData(List<List<Double>> currentPriceStats) {
        this.currentPriceStats = currentPriceStats;
        this.refreshPressed = true;
        this.userStats = null;
        this.ownedTickers = null;
        this.ownedAmounts = null;
    }

    public DashboardOutputData(
            HashMap<String, Double> userStats,
            List<String> ownedTickers,
            List<Double> ownedAmounts
    ) {
        this.userStats = userStats;
        this.ownedTickers = ownedTickers;
        this.ownedAmounts = ownedAmounts;
        this.refreshPressed = false;
        this.currentPriceStats = null;
    }

    public Boolean getRefreshPressed() {
        return refreshPressed;
    }

    public List<List<Double>> getCurrentPriceStats() {
        return currentPriceStats;
    }

    public HashMap<String, Double> getUserStats() {
        return userStats;
    }

    public List<String> getOwnedTickers() {
        return ownedTickers;
    }

    public List<Double> getOwnedAmounts() {
        return ownedAmounts;
    }
}
