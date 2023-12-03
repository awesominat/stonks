package interface_adapter.Sell;

import interface_adapter.Dashboard.DashboardViewModel;
import interface_adapter.ViewManagerModel;
import use_case.Sell.SellOutputData;
import use_case.Sell.SellOutputBoundary;
import interface_adapter.Dashboard.DashboardState;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SellPresenter implements SellOutputBoundary {

    private final SellViewModel sellViewModel;
    private final DashboardViewModel dashboardViewModel;

    /**
     * Constructor for the sell presenter
     *
     * @param sellViewModel         sell view model required for fetching and updating current sell state
     * @param dashboardViewModel    dashboard view model used to grab current stock price
     *                              information from dashboard state (to minimize API calls)
     */
    public SellPresenter(
            SellViewModel sellViewModel,
            DashboardViewModel dashboardViewModel
    ) {
        this.sellViewModel = sellViewModel;
        this.dashboardViewModel = dashboardViewModel;
    }

    /**
     * Prepares the success view for the sell use case. There are two cases.
     *      1. When the user navigates to the sell page and the sell table needs to be updated
     *         with the current owned stocks. Also occurs when the auto cache refresh occurs and the stocks
     *         table needs to be updated with new prices.
     *      2. When the user presses the sell stocks button to sell stocks.
     * We decide which case by looking at the executeTypeSell attribute of the response from the interactor
     *
     * @param response  An instance of SellOutputData containing necessary information to execute the
     *                  above two cases and differentiate between them
     */
    @Override
    public void prepareSuccessView(SellOutputData response) {
        SellState sellState = sellViewModel.getState();

        if (response.isExecuteTypeSell()) {
            String sellSuccess = String.format("Congratulations! Sold %.2f stocks of ", response.getAmount()) + response.getTicker();
            sellState.setSellSuccess(sellSuccess);
        } else {
            DashboardState dashboardState = dashboardViewModel.getState();
            HashMap<String, List<Double>> currentPriceStats = dashboardState.getStocksPriceInformationTable();

            List<Double> currentPrices = new ArrayList<>();
            for (String sellOwnedTicker: response.getOwnedStocks()) {
                List<Double> priceInfo = currentPriceStats.get(sellOwnedTicker);
                currentPrices.add(priceInfo.get(0));
            }
            sellState.setBalance(response.getBalance());
            sellState.setOwnedStocks(response.getOwnedStocks());
            sellState.setOwnedAmounts(response.getOwnedAmounts());
            sellState.setSellAmounts(currentPrices);
        }
        sellViewModel.setState(sellState);
    }

    /**
     * The failure case occurs when either the use enters an incorrect argument type of the amount
     * of stocks to be sold (negative values or non-numeric values).
     * It also occurs if the user tries to sell more of a stock than they own.
     * In either case, the respective error message is passed along to the sell state.
     *
     * @param error     error message detailing why selling of stocks failed
     */
    @Override
    public void prepareFailView(String error) {
        SellState sellState = sellViewModel.getState();
        sellState.setAmountError(error);
    }
}
