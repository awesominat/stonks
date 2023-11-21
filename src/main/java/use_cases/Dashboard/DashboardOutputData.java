package use_cases.Dashboard;

import entities.PortfolioInformation;

import java.util.HashMap;
import java.util.List;

public class DashboardOutputData {
    private HashMap<String, Double> userStats;
    private List<PortfolioInformation> portfolioInformation;

    public DashboardOutputData(HashMap<String, Double> userstats, List<PortfolioInformation> portfolioInformation) {
        this.userStats = userstats;
        this.portfolioInformation = portfolioInformation;
    }

    public HashMap<String, Double> getUserStats() {
        return this.userStats;
    }

    public List<PortfolioInformation> getPortfolioInformation() {
        return this.portfolioInformation;
    }
}
