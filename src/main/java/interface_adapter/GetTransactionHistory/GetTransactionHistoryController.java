package interface_adapter.GetTransactionHistory;

import use_case.GetTransactionHistory.GetTransactionHistoryInputBoundary;

public class GetTransactionHistoryController {
    final private GetTransactionHistoryInputBoundary getTransactionHistoryInteractor;

    /**
     * Constructor for the transaction history controller
     *
     * @param getTransactionHistoryInteractor   the transaction history use case interactor
     */
    public GetTransactionHistoryController(GetTransactionHistoryInputBoundary getTransactionHistoryInteractor){
        this.getTransactionHistoryInteractor = getTransactionHistoryInteractor;
    }

    /**
     * The execute method for the transaction history controller.
     * There is no input data, since the use simply navigates to this screen and the screen
     * displays the transaction history.
     *
     * The filters applied are considered to be viewing strategies, so we do not
     * take the filter input values here
     */
    public void execute(){
        getTransactionHistoryInteractor.execute();
    }


}
