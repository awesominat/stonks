package use_cases.Dashboard;

import entities.PortfolioInformation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DashboardOutputData {
    private HashMap<String, Double> userStats;
    private List<PortfolioInformation> portfolioInformation;

    public DashboardOutputData(
            HashMap<String, Double> userstats,
            List<PortfolioInformation> portfolioInformation
    ) {
        this.userStats = userstats;
        this.portfolioInformation = portfolioInformation;
    }

    public HashMap<String, Double> getUserStats() {
        return this.userStats;
    }

    public List<String> getTickerInformation() {
        List<String> tickers = new ArrayList<String>();

        for (PortfolioInformation pdata: portfolioInformation) {
            tickers.add(pdata.getTicker());
        }
        return tickers;
    }


    public List<String> getFullNamesInformation() {
        List<String> fullNames = new ArrayList<String>();

        for (PortfolioInformation pdata: portfolioInformation) {
            fullNames.add(pdata.getFullName());
        }
        return fullNames;
    }

    public List<Double> getAmountInformation() {
        List<Double> amount = new ArrayList<Double>();

        for (PortfolioInformation pdata: portfolioInformation) {
            amount.add(pdata.getAmount());
        }
        return amount;
    }
}
