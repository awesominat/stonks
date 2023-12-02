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
    private final ViewManagerModel viewManagerModel;

    public SellPresenter(
            ViewManagerModel viewManagerModel,
            SellViewModel sellViewModel,
            DashboardViewModel dashboardViewModel
    ) {
        this.viewManagerModel = viewManagerModel;
        this.sellViewModel = sellViewModel;
        this.dashboardViewModel = dashboardViewModel;
    }

    @Override
    public void prepareSuccessView(SellOutputData response) {
        SellState sellState = sellViewModel.getState();

        if (response.isExecuteTypeSell()) {
            String sellSuccess = String.format("Congratulations! Sold %.2f stocks of ", response.getAmount()) + response.getTicker();
            sellState.setSellSuccess(sellSuccess);
        } else {
            DashboardState dashboardState = dashboardViewModel.getState();
            HashMap<String, List<Double>> currentPriceStats = dashboardState.getStocksPriceInformationTable();
            HashMap<String, Double> dashboardOwnedTickers = dashboardState.getOwnedStocksTable();

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

    @Override
    public void prepareFailView(String error) {
        SellState sellState = sellViewModel.getState();
        sellState.setAmountError(error);
    }
}
