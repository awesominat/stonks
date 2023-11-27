package interface_adapters.Dashboard;

import entities.PortfolioInformation;
import java.util.HashMap;
import java.util.List;

public class DashboardState {
    private HashMap<String, Double> userStats;
    private List<String> ownedTickers;
    private List<String> ownedFullNames;
    private List<Double> ownedAmounts;

    public DashboardState(
            HashMap<String, Double> userStats,
            List<String> ownedTickers,
            List<String> ownedFullNames,
            List<Double> ownedAmounts
    ) {
        this.userStats = userStats;
        this.ownedTickers = ownedTickers;
        this.ownedAmounts = ownedAmounts;
        this.ownedFullNames = ownedFullNames;
    }

    public List<Double> getOwnedAmounts() {
        return this.ownedAmounts;
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

    public List<String> getOwnedFullNames() {
        return ownedFullNames;
    }

    public void setOwnedFullNames(List<String> ownedFullNames) {
        this.ownedFullNames = ownedFullNames;
    }

    public HashMap<String, Double> getUserStats() {
        return this.userStats;
    }

    public void setUserStats(HashMap<String, Double> userStats) {
        this.userStats = userStats;
    }


    public DashboardState() {

    }

}
