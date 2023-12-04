package interface_adapter.Dashboard;

import interface_adapter.Sell.SellViewModel;
import interface_adapter.ViewManagerModel;
import use_case.Dashboard.DashboardOutputBoundary;
import use_case.Dashboard.DashboardOutputData;

import java.util.*;

public class DashboardPresenter implements DashboardOutputBoundary  {

    private final DashboardViewModel dashboardViewModel;
    private final SellViewModel sellViewModel;
    private final ViewManagerModel viewManagerModel;

    /**
     * Constructor for the dashboard presenter
     *
     * @param viewManagerModel      view manager model that allows for switching to other screens
     * @param dashboardViewModel    dashboard view model which is used to fetch and update dashboard state
     * @param sellViewModel         sell view model, which also accesses current stock prices from the dashboard
     *                              state (this was done to minimize API calls)
     */
    public DashboardPresenter(
            ViewManagerModel viewManagerModel,
            DashboardViewModel dashboardViewModel,
            SellViewModel sellViewModel
    ) {
        this.viewManagerModel = viewManagerModel;
        this.dashboardViewModel = dashboardViewModel;
        this.sellViewModel =  sellViewModel;
    }

    /**
     * Checks if the refreshPressed attribute in the response is true or false
     * and then divides into two execution cases
     *      1. This is the case where the refresh button is pressed or when the cache
     *         updates. There is only one method for both cases because the operations performed
     *         are identical. Grabs the updated stock price information table from the response
     *         and passes it to the dashboard state
     *      2. This is the case where the refresh button is not pressed. Here, we update
     *         everything except the current price information. i.e. if a user buys a stock and
     *         returns to the dashboard, they should see the new stock in the table and an updated
     *         current balance, but the actual price of the stock is only filled in the table when
     *         refresh is pressed or when the cache auto updates (every ~15 seconds)
     *
     * @param response      contains information that needs to be displayed on the dashboard
     *                      such as the user stats table and owned stocks table contents as well as
     *                      a refreshPressed attributes that allows us to decide which execution
     *                      case we are in.
     */
    @Override
    public void prepareSuccessView(DashboardOutputData response) {
        DashboardState state = dashboardViewModel.getState();
        if (response.getRefreshPressed()) {
            // Refresh pressed execution case
            // Here, the most updated stock prices are grabbed from the response and placed in the dashboard state
            // The sell view model is also alerted so that it can update itself with new stock prices
            state.setStocksPriceInformationTable(response.getStockPriceInformationTable());
            dashboardViewModel.setState(state);
            dashboardViewModel.firePropertyChanged();
            sellViewModel.firePropertyChanged();
        } else {
            // Refresh not pressed execution case
            // In this case, all other attributes of the page, like the user stats table,
            // owned stock information (not the price information) are updated
            state.setUserStats(response.getUserStats());
            HashMap<String, Double> ownedStocksTable = response.getOwnedStocksInformationTable();
            state.setOwnedStocksTable(ownedStocksTable);
            HashMap<String, List<Double>> stateStockPriceInfoTable = state.getStocksPriceInformationTable();

            Set<String> ownedTickers = ownedStocksTable.keySet();
            Set<String> stateTickers = stateStockPriceInfoTable.keySet();

            stateStockPriceInfoTable.keySet().removeIf(stateTicker -> !ownedTickers.contains(stateTicker));

            for (String ownedTicker: ownedTickers) {
                if (!stateTickers.contains(ownedTicker)) {
                    stateStockPriceInfoTable.put(
                            ownedTicker,
                            Arrays.asList(-1.0, -1.0, -1.0)
                    );
                }
            }

            state.setStocksPriceInformationTable(stateStockPriceInfoTable);
            dashboardViewModel.setState(state);
        }
    }
    public void prepareFailView(String error){

    }

}
