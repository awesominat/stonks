package use_cases;

import entities.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class BaseStockInteractor {

    protected final APIAccessInterface apiAccessInterface;

    public BaseStockInteractor(APIAccessInterface apiAccessInterface) {
        this.apiAccessInterface = apiAccessInterface;
    }

    protected TransactionHistory initHistory(User user, String ticker,
                                             Double amount, Double boughtAt, Transaction transaction) {
        List<PricePoint> lastMonthPrices = apiAccessInterface.getLastMonthPrices(ticker);
        CompanyInformation companyInformation = apiAccessInterface.getCompanyProfile(ticker);
        Stock newStock = new Stock(boughtAt, lastMonthPrices, companyInformation.getName(), ticker);

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);

        return new TransactionHistory(newStock, transactions);
    }

    protected void addToHistory(HashMap<String, TransactionHistory> userHistory, String ticker,
                                User user, Double amount, Double currentPrice, Transaction transaction) {
        TransactionHistory transactionHistory;
        if (!userHistory.containsKey(ticker)) {
            transactionHistory = initHistory(user, ticker, amount, currentPrice, transaction);
        } else {
            transactionHistory = userHistory.get(ticker);
        }
        transactionHistory.addTransaction(transaction);
        userHistory.put(ticker, transactionHistory);
    }
}