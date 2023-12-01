package interface_adapter.Dashboard;

import interface_adapter.CacheStockInformation.CacheStockInformationState;
import interface_adapter.CacheStockInformation.CacheStockInformationViewModel;
import interface_adapter.Sell.SellViewModel;
import interface_adapter.ViewManagerModel;
import use_case.Dashboard.DashboardOutputBoundary;
import use_case.Dashboard.DashboardOutputData;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class DashboardPresenter implements DashboardOutputBoundary, PropertyChangeListener {

    private final DashboardViewModel dashboardViewModel;
    private final SellViewModel sellViewModel;
    private final CacheStockInformationViewModel cacheStockInformationViewModel;
    private final ViewManagerModel viewManagerModel;

    public DashboardPresenter(
            ViewManagerModel viewManagerModel,
            DashboardViewModel dashboardViewModel,
            SellViewModel sellViewModel,
            CacheStockInformationViewModel cacheStockInformationViewModel
    ) {
        this.viewManagerModel = viewManagerModel;
        this.dashboardViewModel = dashboardViewModel;
        this.cacheStockInformationViewModel = cacheStockInformationViewModel;
        this.sellViewModel = sellViewModel;
        cacheStockInformationViewModel.addPropertyChangeListener(this);
    }

    @Override
    public void prepareSuccessView(DashboardOutputData response) {
        DashboardState state = dashboardViewModel.getState();
        if (response.getRefreshPressed()) {
            state.setCurrentPriceStats(response.getCurrentPriceStats());
        } else {
            state.setUserStats(response.getUserStats());

            List<String> responseTickers = response.getOwnedTickers();
            List<Double> responseAmounts = response.getOwnedAmounts();
            List<String> stateTickers = state.getOwnedTickers();
            List<Double> stateAmounts = state.getOwnedAmounts();
            List<List<Double>> stateCurrentPrices = state.getCurrentPriceStats();

            for (int i = 0; i < responseTickers.size(); i++) {
                String ticker = responseTickers.get(i);
                int idx = stateTickers.indexOf(ticker);
                if (idx >= 0) {
                    // ticker already exists in state tickers
                    stateAmounts.set(idx, responseAmounts.get(i));
                } else {
                    stateTickers.add(ticker);
                    stateAmounts.add(responseAmounts.get(i));
                    stateCurrentPrices.add(Arrays.asList(-1.0, -1.0, -1.0));
                }
            }

            List<Integer> idxToRemove = new ArrayList<>();
            for (int i = stateTickers.size() - 1; i >= 0; i--) {
                String ticker = stateTickers.get(i);
                if (!responseTickers.contains(ticker)) {
                    idxToRemove.add(i);
                }
            }

            for (int i: idxToRemove) {
                stateTickers.remove(i);
                stateAmounts.remove(i);
                stateCurrentPrices.remove(i);
            }

            state.setCurrentPriceStats(stateCurrentPrices);
            state.setOwnedAmounts(stateAmounts);
            state.setOwnedTickers(stateTickers);
        }
        dashboardViewModel.setState(state);
    }

    @Override
    public void prepareFailView(String error) {
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        DashboardState dashboardState = dashboardViewModel.getState();
        CacheStockInformationState state = (CacheStockInformationState) evt.getNewValue();
        HashMap<String, List<Double>> cachedStockInformationMap = state.getStockInformationMap();

        List<String> ownedTickers = dashboardState.getOwnedTickers();
        List<List<Double>> currentPriceInfo = new ArrayList<>();
        for (String ticker: ownedTickers) {
            currentPriceInfo.add(cachedStockInformationMap.get(ticker));
        }
        dashboardState.setCurrentPriceStats(currentPriceInfo);
        dashboardViewModel.setState(dashboardState);
        dashboardViewModel.firePropertyChanged();
        sellViewModel.firePropertyChanged();
    }
}
