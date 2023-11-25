package use_cases.GetTransactionHistory;

import entities.*;

import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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

        // List of Stock Names
        List<String> stocks = new ArrayList<>();

        List<List<Transaction>> listOfTransactions = new ArrayList<>();

        for (Map.Entry<String, TransactionHistory> entry : userHistory.entrySet()) {

            String stock = entry.getValue().getStock().getFullName();
            TransactionHistory historyTransactionsForStock = entry.getValue();

            stocks.add(stock);
            listOfTransactions.add(historyTransactionsForStock.getTransactions());
        }


        HashMap<String, List<List<Object>>> userRecord = new HashMap<>();
        for (String stock: stocks) {
            // List of Transactions of a Particular Stock (List of Transaction)
            List<List<Object>> listOfTransactionFacts = new ArrayList<>();

            for (List<Transaction> transactions : listOfTransactions) {

                for (Transaction transaction : transactions) {

                    String type = transaction.getType().toString();
                    Double amount = transaction.getAmount();
                    Double pricePurchasedAt = transaction.getPricePoint().getPrice();
                    LocalDate date = transaction.getPricePoint().getTimeStamp();

                    // List containing Transaction Facts
                    List<Object> Transaction = new ArrayList<>();

                    Transaction.add(type);
                    Transaction.add(amount);
                    Transaction.add(pricePurchasedAt);
                    Transaction.add(date);
                    listOfTransactionFacts.add(Transaction);

                }

            }
            userRecord.put(stock, listOfTransactionFacts);

        }




        GetTransactionHistoryOutputData transactionHistory = new GetTransactionHistoryOutputData(userRecord);

        getTransactionHistoryPresenter.prepareSuccessView(transactionHistory);


    }
}
