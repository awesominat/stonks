package use_case.GetTransactionHistory;

import entity.Transaction;
import entity.TransactionHistory;
import entity.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The {@code GetTransactionHistoryInteractor} class implements the
 * {@code GetTransactionHistoryInputBoundary} interface and is responsible for
 * retrieving and processing transaction history data for a user. It interacts
 * with the data access layer to fetch user data, transforms the data into a
 * suitable format, and prepares it for presentation using the provided presenter.
 */
public class GetTransactionHistoryInteractor implements GetTransactionHistoryInputBoundary{
    // The data access object
    final GetTransactionHistoryDataAccessInterface userDataAccessObject;
    // presenter that the output data from the transaction history is passed to
    GetTransactionHistoryOutputBoundary getTransactionHistoryPresenter;

    /**
     * Constructs a {@code GetTransactionHistoryInteractor} with the specified dependencies.
     *
     * @param userDataAccessObject         the data access object for fetching user data
     * @param getTransactionHistoryPresenter the presenter for handling transaction history presentation
     */
    public GetTransactionHistoryInteractor(
            GetTransactionHistoryDataAccessInterface userDataAccessObject,
            GetTransactionHistoryOutputBoundary getTransactionHistoryPresenter) {
        this.userDataAccessObject = userDataAccessObject;
        this.getTransactionHistoryPresenter = getTransactionHistoryPresenter;
    }

    /**
     * Executes the transaction history retrieval and presentation process. It fetches
     * user data, processes the transaction history, sorts the transactions by date,
     * converts the transaction data into a suitable format, and notifies the presenter
     * with the prepared transaction history for display.
     */
    @Override
    public void execute() {
        User user = userDataAccessObject.get();
        HashMap<String, TransactionHistory> userHistory =  user.getHistory();

        List<List<Object>> userRecord = new ArrayList<>();

        for (Map.Entry<String, TransactionHistory> entry : userHistory.entrySet()) {
            TransactionHistory transactionHistoryForStock = entry.getValue();
            String stock = transactionHistoryForStock.getStock().getFullName();

            for (Transaction tran: transactionHistoryForStock) {
                String type = tran.getType().toString();
                String amount = tran.getAmount().toString();
                String pricePurchasedAt = String.format("%.2f", tran.getPricePoint().getPrice());

                LocalDateTime localDateTime = tran.getPricePoint().getTimeStamp();

                // List containing Transaction Facts
                List<Object> transaction = new ArrayList<>();
                transaction.add(0, stock);
                transaction.add(1, type);
                transaction.add(2, amount);
                transaction.add(3, pricePurchasedAt);
                transaction.add(4, localDateTime);
                userRecord.add(transaction);
            }
        }
        sortTransactions(userRecord);
        List<List<String>> sortedUserRecord = convertObjectListToStringList(userRecord);
        GetTransactionHistoryOutputData transactionHistory = new GetTransactionHistoryOutputData(sortedUserRecord);
        getTransactionHistoryPresenter.prepareSuccessView(transactionHistory);
    }

    /**
     * Sorts a list of transactions based on the datetime of the transactions.
     *
     * @param unsortedTransactions the list of transactions to be sorted
     */
    private void sortTransactions(List<List<Object>> unsortedTransactions) {
        unsortedTransactions.sort((list1, list2) -> {
            LocalDateTime dateTime1 = (LocalDateTime) list1.get(4);
            LocalDateTime dateTime2 = (LocalDateTime) list2.get(4);
            return dateTime2.compareTo(dateTime1);
        });
    }

    /**
     * Converts a list of transactions from an object-based format to a string-based format.
     *
     * @param objectList the list of transactions in object format
     * @return the list of transactions in string format
     */
    private List<List<String>> convertObjectListToStringList(List<List<Object>> objectList) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss a yyyy-MM-dd");
        List<List<String>> outputList = new ArrayList<>();
        for (List<Object> o: objectList) {
            List<String> stringOutput = new ArrayList<>();
            stringOutput.add(o.get(0).toString());
            stringOutput.add(o.get(1).toString());
            stringOutput.add(o.get(2).toString());
            stringOutput.add(o.get(3).toString());
            LocalDateTime date = (LocalDateTime) o.get(4);
            stringOutput.add(date.format(formatter));
            outputList.add(stringOutput);
        }
        return outputList;
    }
}
