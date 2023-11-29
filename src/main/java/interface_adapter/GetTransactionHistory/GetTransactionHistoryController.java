package interface_adapter.GetTransactionHistory;

import use_case.GetTransactionHistory.GetTransactionHistoryInputBoundary;

public class GetTransactionHistoryController {
    final private GetTransactionHistoryInputBoundary getTransactionHistoryInteractor;

    public GetTransactionHistoryController(GetTransactionHistoryInputBoundary getTransactionHistoryInteractor){
        this.getTransactionHistoryInteractor = getTransactionHistoryInteractor;
    }

    public void execute(){
        getTransactionHistoryInteractor.execute();
    }


}
