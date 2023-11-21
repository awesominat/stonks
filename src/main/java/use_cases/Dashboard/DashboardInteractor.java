package use_cases.Dashboard;

import entities.*;
import interface_adapters.Dashboard.DashboardOutputBoundary;
import use_cases.APIAccessInterface;
import use_cases.BaseStockInteractor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.time.temporal.ChronoUnit.DAYS;

public class DashboardInteractor extends BaseStockInteractor implements DashboardInputBoundary {
    final DashboardDataAccessInterface userDataAccessObject;
    DashboardOutputBoundary dashboardPresenter;
    APIAccessInterface driverAPI;

    public DashboardInteractor(DashboardDataAccessInterface userDataAccessInterface,
                               DashboardOutputBoundary dashboardPresenter, APIAccessInterface driverAPI) {
        super(driverAPI);
        this.userDataAccessObject = userDataAccessInterface;
        this.dashboardPresenter = dashboardPresenter;
        this.driverAPI = driverAPI;
    }

    @Override
    public void execute() {
        User user = userDataAccessObject.get();

        HashMap<String, Double> portfolio = user.getPortfolio();
        List<PortfolioInformation> portfolioInformations = new ArrayList<>();

        Double accountBalance = user.getBalance();
        double totalAssets = 0;

        HashMap<String, Double> prices = new HashMap<>();

        for (Map.Entry<String, Double> entry : portfolio.entrySet()) {
            String key = entry.getKey();
            Double value = entry.getValue();

            prices.put(key, driverAPI.getCurrentPrice(key).getPrice());
            totalAssets += value * prices.get(key);

            CompanyInformation companyInformation = driverAPI.getCompanyProfile(key);

            PortfolioInformation emptyInfo = new PortfolioInformation();
            emptyInfo.setAmount(value);
            emptyInfo.setTicker(key);
            emptyInfo.setFullName(companyInformation.getName());

            portfolioInformations.add(emptyInfo);
        }

        double daysSinceLastTopup = -1;
        double totalProfit = 0;
        double sumAggregateVolume = 0;

        HashMap<String, TransactionHistory> history = user.getHistory();
        LocalDate now = LocalDate.now();

        for (Map.Entry<String, TransactionHistory> entry : history.entrySet()) {
            String key = entry.getKey();
            TransactionHistory value = entry.getValue();

            Stock stock = value.getStock();
            List<Transaction> transactions = value.getTransactions();

            for (Transaction transaction : transactions) {
                sumAggregateVolume += transaction.getAmount();
                if (transaction.getType() == TransactionType.BUY) {
                    if (!prices.containsKey(key)) {
                        prices.put(key, driverAPI.getCurrentPrice(key).getPrice());
                    }
                    Double currentPrice = transaction.getAmount() * prices.get(key);
                    Double buyPrice = transaction.getPricePoint().getPrice();

                    totalProfit += currentPrice - buyPrice;
                } else if (transaction.getType() == TransactionType.TOPUP) {
                    PricePoint pricePoint = transaction.getPricePoint();
                    double daysSince = DAYS.between(now, pricePoint.getTimeStamp());
                    if (daysSinceLastTopup == -1) {
                        daysSinceLastTopup = daysSince;
                    } else {
                        daysSinceLastTopup = Math.min(daysSince, daysSinceLastTopup);
                    }
                }
            }
        }
//
//        liquid money (balance)
//                total assets (balance + worth of stock)
//        days since last top up
//        total profit
//        aggregate trading volume (sum the amount of money spent buying and amount of money gained when selling)
        HashMap<String, Double> userStats = new HashMap<>();

        userStats.put("balance", accountBalance);
        userStats.put("totalAssets", totalAssets);
        userStats.put("daysSinceLastTopup", daysSinceLastTopup);
        userStats.put("totalProfit", totalProfit);
        userStats.put("aggregateVolume", sumAggregateVolume);

        DashboardOutputData result = new DashboardOutputData(userStats, portfolioInformations);
        dashboardPresenter.prepareSuccessView(result);
    }
}
