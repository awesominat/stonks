package use_case.Dashboard;

import entity.*;
import use_case.APIAccessInterface;
import use_case.BaseStockInteractor;

import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalDate;
import java.util.*;

import static java.time.temporal.ChronoUnit.DAYS;

public class DashboardInteractor extends BaseStockInteractor implements DashboardInputBoundary, PropertyChangeListener {
    final DashboardDataAccessInterface userDataAccessObject;
    final CacheStockInformation cacheStockInformation;
    DashboardOutputBoundary dashboardPresenter;
    APIAccessInterface driverAPI;

    public DashboardInteractor(
            DashboardDataAccessInterface userDataAccessInterface,
            CacheStockInformation cacheStockInformation,
            DashboardOutputBoundary dashboardPresenter,
            APIAccessInterface driverAPI
    ) {
        super(driverAPI);
        this.userDataAccessObject = userDataAccessInterface;
        this.dashboardPresenter = dashboardPresenter;
        this.driverAPI = driverAPI;
        this.cacheStockInformation = cacheStockInformation;
        this.cacheStockInformation.addPropertyChangeListener(this);
    }

    @Override
    public void execute(DashboardInputData dashboardInputData) {
        Boolean refreshPressed = dashboardInputData.getRefreshPressed();
        User user = userDataAccessObject.get();
        HashMap<String, Double> portfolio = user.getPortfolio();

        // If the refresh button is pressed, we want to re fetch stock price information so that displayed
        // tables are up-to-date. Pressing of refresh is a manual user action, so API calls are made.
        if (refreshPressed) {
            HashMap<String, List<Double>> stockPriceInformationMap = new HashMap<>();
            for (String ticker : portfolio.keySet()) {
                // if we ever reach a point where a ticker in the portfolio has no information
                // this is only recoverable if the ticker is completely deleted from the portfolio
                StockInformation stockInformation = null;
                try {
                    stockInformation = driverAPI.getCurrentStockInformation(ticker);
                } catch (APIAccessInterface.TickerNotFoundException e) {
                    portfolio.remove(ticker);
                    continue;
                }
                List<Double> stockInfo = Arrays.asList(
                        stockInformation.getCurrentPrice(),
                        stockInformation.getPriceChange(),
                        stockInformation.getPercentChange()
                );
                stockPriceInformationMap.put(ticker, stockInfo);
            }
            dashboardPresenter.prepareSuccessView(new DashboardOutputData(stockPriceInformationMap));
        } else {
            // If refresh is not pressed, the user has navigated to the dashboard using the back button from
            // another page. In this case, refresh everything EXCEPT the price stats, which will remain as they are
            // until the refresh button is pressed.
            HashMap<String, Double> userStats = buildUserStats(user);
            dashboardPresenter.prepareSuccessView(new DashboardOutputData(userStats, portfolio));
        }
    }

    public HashMap<String, Double> buildUserStats(User user) {
        HashMap<String, Double> userStats = new HashMap<String, Double>();
        userStats.put("balance", user.getBalance());

        double portfolioNetWorth = 0.0;
        HashMap<String, TransactionHistory> history = user.getHistory();
        for (Map.Entry<String, Double> entry: user.getPortfolio().entrySet()) {
            String ticker = entry.getKey();
            Double amount = entry.getValue();

            TransactionHistory stockHistory = history.get(ticker);
            Double lastPrice = stockHistory.getStock().getLastSeenPrice();
            portfolioNetWorth += lastPrice * amount;
        }
        userStats.put("Portfolio net worth", portfolioNetWorth);
        userStats.put("Net worth", user.getBalance() + portfolioNetWorth);

        double daysSinceLastTopup = -1.0;
        LocalDate now = LocalDate.now();
        for (Map.Entry<String, TransactionHistory> entry: history.entrySet()) {
            TransactionHistory stockHistory = entry.getValue();
            for (Transaction transaction: stockHistory) {
                if (transaction.getType() == TransactionType.TOPUP) {
                    PricePoint pp = transaction.getPricePoint();
                    double daysSince = DAYS.between(now, pp.getTimeStamp());
                    if (daysSinceLastTopup == -1) {
                        daysSinceLastTopup = daysSince;
                    } else {
                        daysSinceLastTopup = Math.min(daysSince, daysSinceLastTopup);
                    }
                }
            }
        }
        userStats.put("Days since last reset", daysSinceLastTopup);
        return userStats;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        CacheStockInformation cacheStockInformation = (CacheStockInformation) evt.getNewValue();
        HashMap<String, List<Double>> stockPriceInformationMap = cacheStockInformation.getStockInformationMap();
        DashboardOutputData dashboardOutputData = new DashboardOutputData(stockPriceInformationMap);
        dashboardPresenter.prepareSuccessView(dashboardOutputData);
    }
}