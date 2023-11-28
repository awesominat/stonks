package interface_adapters.Dashboard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DashboardState {
    private HashMap<String, Double> userStats;
    private List<String> ownedTickers;
    private List<Double> ownedAmounts;
    private List<List<Double>> currentPriceStats;
    private Boolean refreshPressed;

    public DashboardState(
            HashMap<String, Double> userStats,
            List<String> ownedTickers,
            List<Double> ownedAmounts,
            List<List<Double>> currentPriceStats,
            Boolean refreshPressed
    ) {
        this.userStats = userStats;
        this.ownedTickers = ownedTickers;
        this.ownedAmounts = ownedAmounts;
        this.currentPriceStats = currentPriceStats;
        this.refreshPressed = refreshPressed;
    }

    public List<Double> getOwnedAmounts() {
        return ownedAmounts;
    }

    public void setOwnedAmounts(List<Double> ownedAmounts) {
        this.ownedAmounts = ownedAmounts;
    }

    public List<String> getOwnedTickers() {
        return ownedTickers;
    }

    public void setOwnedTickers(List<String> ownedTickers) {
        this.ownedTickers = ownedTickers;
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

    public List<List<Double>> getCurrentPriceStats() {
        return currentPriceStats;
    }

    public void setCurrentPriceStats(List<List<Double>> currentPriceStats) {
        this.currentPriceStats = currentPriceStats;
    }

    public DashboardState() {
        this.userStats = new HashMap<>();
        this.ownedAmounts = new ArrayList<>();
        this.ownedTickers = new ArrayList<>();
        this.currentPriceStats = new ArrayList<>();
        this.refreshPressed = false;
    }

}
