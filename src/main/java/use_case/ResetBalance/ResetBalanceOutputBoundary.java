package use_case.ResetBalance;

public interface ResetBalanceOutputBoundary {
    void prepareSuccessView(ResetBalanceOutputData result);

    void prepareFailView(String error);
}