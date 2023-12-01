package use_case.GetTransactionHistory;

import entity.Transaction;
import entity.TransactionHistory;
import entity.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetTransactionHistoryInteractor implements GetTransactionHistoryInputBoundary{
    final GetTransactionHistoryDataAccessInterface userDataAccessObject;
    GetTransactionHistoryOutputBoundary getTransactionHistoryPresenter;

    public GetTransactionHistoryInteractor(
            GetTransactionHistoryDataAccessInterface userDataAccessObject,
            GetTransactionHistoryOutputBoundary getTransactionHistoryPresenter) {
        this.userDataAccessObject = userDataAccessObject;
        this.getTransactionHistoryPresenter = getTransactionHistoryPresenter;
    }

    @Override
    public void execute() {
        User user = userDataAccessObject.get();
        HashMap<String, TransactionHistory> userHistory =  user.getHistory();

        List<List<String>> userRecord = new ArrayList<>();

        for (Map.Entry<String, TransactionHistory> entry : userHistory.entrySet()) {

            TransactionHistory transactionHistoryForStock = entry.getValue();
            String stock = transactionHistoryForStock.getStock().getFullName();


            for (Transaction tran: transactionHistoryForStock) {

                String type = tran.getType().toString();
                String amount = tran.getAmount().toString();
                String pricePurchasedAt = String.format("%.2f", tran.getPricePoint().getPrice());
                String date = tran.getPricePoint().getTimeStamp().toString();

                // List containing Transaction Facts
                List<String> transaction = new ArrayList<>();
                transaction.add(0, stock);
                transaction.add(1, type);
                transaction.add(2,amount);
                transaction.add(3, pricePurchasedAt);
                transaction.add(4,date);
                userRecord.add(transaction);
            }
        }
        GetTransactionHistoryOutputData transactionHistory = new GetTransactionHistoryOutputData(userRecord);

        getTransactionHistoryPresenter.prepareSuccessView(transactionHistory);
    }
}
