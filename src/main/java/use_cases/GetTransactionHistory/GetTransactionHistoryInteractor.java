package use_cases.GetTransactionHistory;

import entities.TransactionHistory;
import use_cases.BaseStockInteractor;
import use_cases.Dashboard.DashboardDataAccessInterface;
import use_cases.Dashboard.DashboardOutputBoundary;
import entities.User;

import java.util.HashMap;

public class GetTransactionHistoryInteractor implements GetTransactionHistoryInputBoundary{
    final GetTransactionHistoryDataAccessInterface userDataAccessObject;
    GetTransactionHistoryOutputBoundary getTransactionHistoryPresenter;

    public GetTransactionHistoryInteractor(GetTransactionHistoryDataAccessInterface userDataAccessObject,
                                           GetTransactionHistoryOutputBoundary getTransactionHistoryPresenter) {
        this.userDataAccessObject = userDataAccessObject;
        this.getTransactionHistoryPresenter = getTransactionHistoryPresenter;
    }

    @Override
    public void execute() {
        User user = userDataAccessObject.get();
        HashMap<String, TransactionHistory> userHistory =  user.getHistory();
        GetTransactionHistoryOutputData transactionHistory = new GetTransactionHistoryOutputData(userHistory);
        getTransactionHistoryPresenter.prepareSuccessView(transactionHistory);


    }
}
