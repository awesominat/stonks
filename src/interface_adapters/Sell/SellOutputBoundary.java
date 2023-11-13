package interface_adapters.Sell;

import use_cases.Sell.SellOutputData;

public interface SellOutputBoundary {
    void prepareSuccessView(SellOutputData result);
    void prepareFailView(String error);
}
