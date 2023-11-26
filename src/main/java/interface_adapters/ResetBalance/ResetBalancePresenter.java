package interface_adapters.ResetBalance;

import interface_adapters.ViewManagerModel;
import use_cases.ResetBalance.ResetBalanceOutputData;
import use_cases.ResetBalance.ResetBalanceOutputBoundary;
import view.ResetBalanceView;

public class ResetBalancePresenter implements ResetBalanceOutputBoundary{
    private final ResetBalanceViewModel resetBalanceViewModel;
    private ViewManagerModel viewManagerModel;

    public ResetBalancePresenter(ViewManagerModel viewManagerModel,
                                 ResetBalanceViewModel resetBalanceViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.resetBalanceViewModel = resetBalanceViewModel;
    }
    @Override
    public void prepareSuccessView(ResetBalanceOutputData response) {
        // TODO
        // On Success switch to Portfolio View
    }

    @Override
    public void prepareFailView(String error) {
        // TODO
//        BuyState buyState = buyViewModel.getState();
//        BuyState.setProcessError(error);
//        buyViewModel.firePropertyChanged();
    }
}
