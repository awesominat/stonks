package use_case.Dashboard;

import entity.*;
import use_case.APIAccessInterface;
import use_case.BaseStockInteractor;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalDateTime;
import java.util.*;

import static java.time.temporal.ChronoUnit.DAYS;

public class DashboardInteractor extends BaseStockInteractor implements DashboardInputBoundary, PropertyChangeListener {
    final DashboardDataAccessInterface userDataAccessObject;
    final CacheStockInformation cacheStockInformation;
    DashboardOutputBoundary dashboardPresenter;
    APIAccessInterface driverAPI;

    /**
     * Constructor for the dashboard use case interactor
     *
     * @param userDataAccessInterface   allows us to manipulate the user entity
     * @param cacheStockInformation     allows us to grab cached stock price information
     * @param dashboardPresenter        allows us to update the dashboard state
     * @param driverAPI                 allows us to grab new stock prices when refresh is pressed
     */
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

    /**
     * The execute function that handles the two execution cases;
     *      1. When we want to perform API calls to get new stock price information
     *      2. When we do not want to perform API calls and want to use stock price
     *         information already stored in the state.
     * See the comments below for more information
     *
     * @param dashboardInputData    contains whether the execution case should be refresh or not
     */
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

    /**
     * Constructs the user stats hashmap which is used to populate the user stats table
     *
     * @param user      The user object which allows access to transaction history and
     *                  the portfolio, from which we can calculate statistics about the
     *                  users performance
     * @return          A Hashmap that represents the user stats table
     */
    public HashMap<String, Double> buildUserStats(User user) {
        HashMap<String, Double> userStats = new HashMap<>();
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
        LocalDateTime now = LocalDateTime.now();
        for (Map.Entry<String, TransactionHistory> entry: history.entrySet()) {
            TransactionHistory stockHistory = entry.getValue();
            for (Transaction transaction: stockHistory) {
                if (transaction.getType() == TransactionType.TOPUP) {
                    PricePoint pp = transaction.getPricePoint();
                    double daysSince = DAYS.between(pp.getTimeStamp(), now);
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

    /**
     * The propertyChange function of the dashboard interactor. This function grabs
     * updated stock prices from the CacheStockInformation and sends them to the presenter,
     * which can then use this information to update the dashboard state
     * This is run whenever the CacheStockInformation entity is updated with new stock prices.
     *
     * @param evt A PropertyChangeEvent object describing the event source
     *          and the property that has changed.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        CacheStockInformation cacheStockInformation = (CacheStockInformation) evt.getNewValue();
        HashMap<String, List<Double>> stockPriceInformationMap = cacheStockInformation.getStockInformationMap();
        DashboardOutputData dashboardOutputData = new DashboardOutputData(stockPriceInformationMap);
        dashboardPresenter.prepareSuccessView(dashboardOutputData);
    }
}