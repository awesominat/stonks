package interface_adapters.Dashboard.Sell;

import use_cases.Sell.SellOutputData;

public interface DashboardOutputBoundary {
    void prepareSuccessView(SellOutputData result);
    void prepareFailView(String error);
}
