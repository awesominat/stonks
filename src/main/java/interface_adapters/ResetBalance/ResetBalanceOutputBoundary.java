package interface_adapters.ResetBalance;

import use_cases.ResetBalance.ResetBalanceOutputData;

public interface ResetBalanceOutputBoundary {
    void prepareSuccessView(ResetBalanceOutputData result);

    void prepareFailView(String error);
}
