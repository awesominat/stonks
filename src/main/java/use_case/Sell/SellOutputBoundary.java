package use_case.Sell;

public interface SellOutputBoundary {
    void prepareSuccessView(SellOutputData result);
    void prepareFailView(String error);
}
