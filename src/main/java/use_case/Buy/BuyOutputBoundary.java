package use_case.Buy;

public interface BuyOutputBoundary {
    void prepareSuccessView(BuyOutputData result);
    void prepareSuccessView(BuySearchOutputData result);

    void prepareFailView(String error);
}