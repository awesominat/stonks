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

    public DashboardPresenter(
            ViewManagerModel viewManagerModel,
            DashboardViewModel dashboardViewModel,
            SellViewModel sellViewModel
    ) {
        this.viewManagerModel = viewManagerModel;
        this.dashboardViewModel = dashboardViewModel;
        this.sellViewModel =  sellViewModel;
    }

    @Override
    public void prepareSuccessView(DashboardOutputData response) {
        DashboardState state = dashboardViewModel.getState();
        if (response.getRefreshPressed()) {
            state.setStocksPriceInformationTable(response.getStockPriceInformationTable());
            dashboardViewModel.setState(state);
            dashboardViewModel.firePropertyChanged();
            sellViewModel.firePropertyChanged();
        } else {
            state.setUserStats(response.getUserStats());
            HashMap<String, Double> ownedStocksTable = response.getOwnedStocksInformationTable();
            state.setOwnedStocksTable(ownedStocksTable);
            HashMap<String, List<Double>> stateStockPriceInfoTable = state.getStocksPriceInformationTable();

            Set<String> ownedTickers = ownedStocksTable.keySet();
            Set<String> stateTickers = stateStockPriceInfoTable.keySet();

            for (String stateTicker: stateTickers) {
                if (!ownedTickers.contains(stateTicker)) {
                    stateStockPriceInfoTable.remove(stateTicker);
                }
            }

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

    @Override
    public void prepareFailView(String error) {
    }
}
