package interface_adapters.Dashboard;

import entities.PortfolioInformation;
import java.util.HashMap;
import java.util.List;

public class DashboardState {
    private HashMap<String, Double> userStats;
    // change owned stocks data type
    private List<PortfolioInformation> ownedStocks;

    public DashboardState(HashMap<String, Double> userStats, List<PortfolioInformation> ownedStocks) {
        this.userStats = userStats;
        this.ownedStocks = ownedStocks;
    }

    public List<PortfolioInformation> getOwnedStocks() {
        return this.ownedStocks;
    }

    public HashMap<String, Double> getUserStats() {
        return this.userStats;
    }

    public void setUserStats(HashMap<String, Double> userStats) {
        this.userStats = userStats;
    }

    public void setOwnedStocks(List<PortfolioInformation> ownedStocks) {
        this.ownedStocks = ownedStocks;
    }

    // Because of the previous copy constructor, the default constructor must be explicit. Hence overloading.
    public DashboardState() {

    }

}
