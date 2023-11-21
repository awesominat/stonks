package use_cases.GetTransactionHistory;


public interface GetTransactionHistoryOutputBoundary {
    void prepareSuccessView(GetTransactionHistoryOutputData getTransactionHistoryOutputData);

    void prepareFailView(String error);
}
