package use_cases.Dashboard;

import entities.*;
import use_cases.APIAccessInterface;
import use_cases.BaseStockInteractor;

import java.time.LocalDate;
import java.util.*;

import static java.time.temporal.ChronoUnit.DAYS;

public class DashboardInteractor extends BaseStockInteractor implements DashboardInputBoundary {
    final DashboardDataAccessInterface userDataAccessObject;
    DashboardOutputBoundary dashboardPresenter;
    APIAccessInterface driverAPI;

    public DashboardInteractor(
            DashboardDataAccessInterface userDataAccessInterface,
            DashboardOutputBoundary dashboardPresenter,
            APIAccessInterface driverAPI
    ) {
        super(driverAPI);
        this.userDataAccessObject = userDataAccessInterface;
        this.dashboardPresenter = dashboardPresenter;
        this.driverAPI = driverAPI;
    }

    @Override
    public void execute(DashboardInputData dashboardInputData) {
        Boolean refreshPressed = dashboardInputData.getRefreshPressed();
        User user = userDataAccessObject.get();
        HashMap<String, Double> portfolio = user.getPortfolio();
        System.out.println(portfolio);

        // If the refresh button is pressed, we want to re fetch stock price information so that displayed
        // tables are up-to-date. Pressing of refresh is a manual user action, so API calls are made.
        if (refreshPressed) {
            List<List<Double>> priceStats = new ArrayList<List<Double>>();
            for (String ticker : portfolio.keySet()) {
                StockInformation stockInformation = driverAPI.getCurrentStockInformation(ticker);
                List<Double> stockInfo = Arrays.asList(
                        stockInformation.getCurrentPrice(),
                        stockInformation.getPriceChange(),
                        stockInformation.getPercentChange()
                );
                priceStats.add(stockInfo);
            }
            dashboardPresenter.prepareSuccessView(new DashboardOutputData(priceStats));
        } else {
            // If refresh is not pressed, the user has navigated to the dashboard using the back button from
            // another page. In this case, refresh everything EXCEPT the price stats, which will remain as they are
            // until the refresh button is pressed.
            List<String> ownedTickers = new ArrayList<String>();
            ownedTickers.addAll(portfolio.keySet());

            List<Double> ownedAmounts = new ArrayList<Double>();
            ownedAmounts.addAll(portfolio.values());

            HashMap<String, Double> userStats = buildUserStats(user);
            dashboardPresenter.prepareSuccessView(new DashboardOutputData(userStats, ownedTickers, ownedAmounts));
        }
    }

    public HashMap<String, Double> buildUserStats(User user) {
        HashMap<String, Double> userStats = new HashMap<String, Double>();
        userStats.put("balance", user.getBalance());
        return userStats;
    }
}
//        List<PortfolioInformation> portfolioInformations = new ArrayList<>();
//
//        Double accountBalance = user.getBalance();
//        HashMap<String, TransactionHistory> history = user.getHistory();
//        double totalAssets = 0;
//
//        HashMap<String, Double> prices = new HashMap<>();
//
//        for (Map.Entry<String, Double> entry : portfolio.entrySet()) {
//            String key = entry.getKey();
//            Double value = entry.getValue();
//            prices.put(key, history.get(key).getStock().getLastSeenPrice());
//            totalAssets += value * prices.get(key);
//            PortfolioInformation portfolioInformation = new PortfolioInformation();
//            portfolioInformation.setAmount(value);
//            portfolioInformation.setTicker(key);
//            portfolioInformation.setFullName(history.get(key).getStock().getFullName());
//            portfolioInformation.setPrice(prices.get(key));
//            portfolioInformations.add(portfolioInformation);
//        }
//
//        double daysSinceLastTopup = -1;
//        double totalProfit = 0;
//        double sumAggregateVolume = 0;
//
//        LocalDate now = LocalDate.now();
//
//        for (Map.Entry<String, TransactionHistory> entry : history.entrySet()) {
//            String key = entry.getKey();
//            TransactionHistory value = entry.getValue();
//
//            Stock stock = value.getStock();
//            List<Transaction> transactions = value.getTransactions();
//
//            for (Transaction transaction : transactions) {
//                sumAggregateVolume += transaction.getAmount();
//                if (transaction.getType() == TransactionType.BUY) {
//                    if (!prices.containsKey(key)) {
//                        prices.put(key, driverAPI.getCurrentPrice(key).getPrice());
//                    }
//                    Double currentPrice = transaction.getAmount() * prices.get(key);
//                    Double buyPrice = transaction.getPricePoint().getPrice();
//
//                    totalProfit += currentPrice - buyPrice;
//                } else if (transaction.getType() == TransactionType.TOPUP) {
//                    PricePoint pricePoint = transaction.getPricePoint();
//                    double daysSince = DAYS.between(now, pricePoint.getTimeStamp());
//                    if (daysSinceLastTopup == -1) {
//                        daysSinceLastTopup = daysSince;
//                    } else {
//                        daysSinceLastTopup = Math.min(daysSince, daysSinceLastTopup);
//                    }
//                }
//            }
//        }
//
//        HashMap<String, Double> userStats = new HashMap<>();


//        User user = userDataAccessObject.get();
//
//
//        DashboardOutputData result = new DashboardOutputData(userStats, portfolioInformations);
//        dashboardPresenter.prepareSuccessView(result);