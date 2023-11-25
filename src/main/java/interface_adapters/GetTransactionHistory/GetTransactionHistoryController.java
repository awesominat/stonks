package interface_adapters.GetTransactionHistory;

import use_cases.Dashboard.DashboardInputBoundary;
import use_cases.GetTransactionHistory.GetTransactionHistoryInputBoundary;

public class GetTransactionHistoryController {
    final private GetTransactionHistoryInputBoundary getTransactionHistoryInteractor;

    public GetTransactionHistoryController(GetTransactionHistoryInputBoundary getTransactionHistoryInteractor){
        this.getTransactionHistoryInteractor = getTransactionHistoryInteractor;
    }

    // Executing the controller should call the Interactor's execute method and
    // no data is getting passed to the interactor as it's just a button press.
    // Thus, it would be handled in the view.
    public void execute(){
        getTransactionHistoryInteractor.execute();
    }


}
