package interface_adapter.ResetBalance;

import interface_adapter.ViewManagerModel;
import use_case.ResetBalance.ResetBalanceOutputData;
import use_case.ResetBalance.ResetBalanceOutputBoundary;

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
