package interface_adapters.GetTransactionHistory;

import interface_adapters.ViewManagerModel;
import use_cases.GetTransactionHistory.GetTransactionHistoryOutputBoundary;
import use_cases.GetTransactionHistory.GetTransactionHistoryOutputData;

public class GetTransactionHistoryPresenter implements GetTransactionHistoryOutputBoundary {
    private final GetTransactionHistoryViewModel getTransactionHistoryViewModel;
    private final ViewManagerModel viewManagerModel;

    public GetTransactionHistoryPresenter(GetTransactionHistoryViewModel getTransactionHistoryViewModel,
                                          ViewManagerModel viewManagerModel){

        this.getTransactionHistoryViewModel = getTransactionHistoryViewModel;
        this.viewManagerModel = viewManagerModel;

    }
    @Override
    public void prepareSuccessView(GetTransactionHistoryOutputData getTransactionHistoryOutputData) {
        GetTransactionHistoryState getTransactionHistoryState = getTransactionHistoryViewModel.getState();
    }

    @Override
    public void prepareFailView(String error) {

    }
}
