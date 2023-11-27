package interface_adapters.GetTransactionHistory;

import use_cases.Dashboard.DashboardInputBoundary;
import use_cases.GetTransactionHistory.GetTransactionHistoryInputBoundary;

public class GetTransactionHistoryController {
    final private GetTransactionHistoryInputBoundary getTransactionHistoryInteractor;

    public GetTransactionHistoryController(GetTransactionHistoryInputBoundary getTransactionHistoryInteractor){
        this.getTransactionHistoryInteractor = getTransactionHistoryInteractor;
    }

    public void execute(){
        getTransactionHistoryInteractor.execute();
    }


}
