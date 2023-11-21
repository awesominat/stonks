package interface_adapters.Dashboard;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DashboardState {
    private HashMap<String, Float> userStats;
    // change owned stocks data type
    private HashMap<String, List> ownedStocks;

    public DashboardState(HashMap<String, Float> userStats, HashMap<String, List> ownedStocks) {
        this.userStats = userStats;
        this.ownedStocks = ownedStocks;
    }

    public HashMap<String, List> getOwnedStocks() {
        return this.ownedStocks;
    }

    public HashMap<String, Float> getUserStats() {
        return this.userStats;
    }

    public void setUserStats(HashMap<String, Float> userStats) {
        this.userStats = userStats;
    }

    public void setOwnedStocks(HashMap<String, List> ownedStocks) {
        this.ownedStocks = ownedStocks;
    }

    // Because of the previous copy constructor, the default constructor must be explicit. Hence overloading.
    public DashboardState() {

    }

}
