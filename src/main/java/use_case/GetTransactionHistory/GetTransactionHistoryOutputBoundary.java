package use_case.GetTransactionHistory;


public interface GetTransactionHistoryOutputBoundary {
    void prepareSuccessView(GetTransactionHistoryOutputData getTransactionHistoryOutputData);

    void prepareFailView(String error);
}
