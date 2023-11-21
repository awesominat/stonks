package use_cases.Dashboard;

import entities.PortfolioInformation;

import java.util.HashMap;
import java.util.List;

public class DashboardOutputData {
    private HashMap<String, Double> userstats;
    private List<PortfolioInformation> portfolioInformations;

    public DashboardOutputData(HashMap<String, Double> userstats, List<PortfolioInformation> portfolioInformations) {
        this.userstats = userstats;
        this.portfolioInformations = portfolioInformations;
    }
}
