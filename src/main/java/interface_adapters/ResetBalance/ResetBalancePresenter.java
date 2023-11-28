package interface_adapters.ResetBalance;

import interface_adapters.ViewManagerModel;
import use_cases.ResetBalance.ResetBalanceOutputData;
import use_cases.ResetBalance.ResetBalanceOutputBoundary;

public class ResetBalancePresenter implements ResetBalanceOutputBoundary{
    private ViewManagerModel viewManagerModel;

    public ResetBalancePresenter(ViewManagerModel viewManagerModel) {
        this.viewManagerModel = viewManagerModel;
    }
    @Override
    public void prepareSuccessView(ResetBalanceOutputData response) {
    }

    @Override
    public void prepareFailView(String error) {
    }
}
