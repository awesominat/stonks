package interface_adapters.Dashboard;

import interface_adapters.ViewManagerModel;
import use_cases.Dashboard.DashboardOutputBoundary;
import use_cases.Dashboard.DashboardOutputData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DashboardPresenter implements DashboardOutputBoundary {

    private final DashboardViewModel dashboardViewModel;
    private final ViewManagerModel viewManagerModel;

    public DashboardPresenter(ViewManagerModel viewManagerModel, DashboardViewModel dashboardViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.dashboardViewModel = dashboardViewModel;
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
                    stateCurrentPrices.add(Arrays.asList(0.0, 0.0, 0.0));
                }
            }

            List<Integer> idxToRemove = new ArrayList<>();
            for (int i = stateTickers.size() - 1; i >= 0; i--) {
                String ticker = stateTickers.get(i);
                if (!responseTickers.contains(ticker)) {
                    idxToRemove.add(i);
                }
            }

            System.out.println(idxToRemove);
            for (int i: idxToRemove) {
                stateTickers.remove(i);
                stateAmounts.remove(i);
                stateCurrentPrices.remove(i);
            }

            System.out.println(stateTickers);

            state.setCurrentPriceStats(stateCurrentPrices);
            state.setOwnedAmounts(stateAmounts);
            state.setOwnedTickers(stateTickers);
        }
        dashboardViewModel.setState(state);
    }

    @Override
    public void prepareFailView(String error) {
    }

}
