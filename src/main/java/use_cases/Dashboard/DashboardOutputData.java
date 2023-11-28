package use_cases.Dashboard;

import entities.PortfolioInformation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DashboardOutputData {
    private HashMap<String, Double> userStats;
    private List<PortfolioInformation> portfolioInformations;

<<<<<<< HEAD
    public DashboardOutputData(HashMap<String, Double> userStats, List<PortfolioInformation> portfolioInformations) {
        this.userStats = userStats;
        this.portfolioInformations = portfolioInformations;
=======
    public DashboardOutputData(
            HashMap<String, Double> userstats,
            List<PortfolioInformation> portfolioInformation
    ) {
        this.userStats = userstats;
        this.portfolioInformation = portfolioInformation;
>>>>>>> origin
    }

    public HashMap<String, Double> getUserStats() {
        return this.userStats;
    }

    public List<String> getTickerInformation() {
        List<String> tickers = new ArrayList<>();

        for (PortfolioInformation portfolioInformation : portfolioInformations) {
            tickers.add(portfolioInformation.getTicker());
        }
        return tickers;
    }

    public List<String> getFullNamesInformation() {
        List<String> fullNames = new ArrayList<>();

        for (PortfolioInformation portfolioInformation: portfolioInformations) {
            fullNames.add(portfolioInformation.getFullName());
        }
        return fullNames;
    }

    public List<Double> getAmountInformation() {
        List<Double> amounts = new ArrayList<>();

        for (PortfolioInformation portfolioInformation: portfolioInformations) {
            amounts.add(portfolioInformation.getAmount());
        }
        return amounts;
    }

    public List<Double> getPriceInformation() {
        List<Double> prices = new ArrayList<>();

        for (PortfolioInformation portfolioInformation : portfolioInformations) {
            prices.add(portfolioInformation.getPrice());
        }
        return prices;
    }

}
