package use_cases.Buy;

import use_cases.Buy.BuyOutputData;

public interface BuyOutputBoundary {
    void prepareSuccessView(BuyOutputData result);

    void prepareFailView(String error);
}