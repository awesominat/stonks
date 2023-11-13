package interface_adapters.Buy;

import use_cases.Buy.BuyOutputData;

public interface BuyOutputBoundary {
    // TODO Ricky
    void prepareSuccessView(BuyOutputData result);

    void prepareFailView(String error);
}