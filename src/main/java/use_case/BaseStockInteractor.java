package use_case;

import entity.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class BaseStockInteractor {

    protected final APIAccessInterface apiAccessInterface;

    public BaseStockInteractor(APIAccessInterface apiAccessInterface) {
        this.apiAccessInterface = apiAccessInterface;
    }

    /**
     * Initializes a {@link TransactionHistory} for a given stock ticker. If the ticker matches
     * the application's name {@link TopupTransaction}, a generic stock is created. Otherwise, it attempts to retrieve
     * company information for the given ticker. Validate ticker before calling this method.
     *
     * @param ticker   The stock ticker to look up the stock.
     * @param boughtAt The price at which the stock is bought.
     * @return A new {@link TransactionHistory} object for the given ticker.
     * @throws RuntimeException if the ticker is not found in the API. This is considered
     *                          a pre-condition violation.
     */
    protected TransactionHistory initHistory(String ticker, Double boughtAt) {
        if (ticker.equals(apiAccessInterface.getAppName())) {
            Stock newStock = new Stock(boughtAt, apiAccessInterface.getAppName(), apiAccessInterface.getAppName());
            List<Transaction> transactions = new ArrayList<>();
            return new TransactionHistory(newStock, transactions);
        } else {
            CompanyInformation companyInformation = null;
            try {
                companyInformation = apiAccessInterface.getCompanyProfile(ticker);
            } catch (APIAccessInterface.TickerNotFoundException e) {
                throw new RuntimeException("Pre-condition violated, ticker " + ticker + " does not exist");
            }
            List<Transaction> transactions = new ArrayList<>();
            Stock newStock = new Stock(boughtAt, companyInformation.getName(), ticker);
            return new TransactionHistory(newStock, transactions);
        }
    }
    protected void updatePortfolio(User user, String ticker, Double amount) {
        if (user.isInPortfolio(ticker)) {
            Double currentlyOwned = user.getStockOwned(ticker);
            if (currentlyOwned + amount <= 0.000001) {
                user.removeFromPortfolio(ticker);
            } else {
                user.addToPortfolio(ticker, currentlyOwned + amount);
            }
        } else {
            user.addToPortfolio(ticker, amount);
        }
    }

    protected void addToHistory(
            User user,
            String ticker,
            Double currentPrice,
            Transaction transaction
    ) {

        HashMap<String, TransactionHistory> userHistory = user.getHistory();
        TransactionHistory transactionHistory;

        if (!userHistory.containsKey(ticker)) {
            transactionHistory = initHistory(ticker, currentPrice);
        } else {
            transactionHistory = userHistory.get(ticker);
        }
        transactionHistory.addTransaction(transaction);
        userHistory.put(ticker, transactionHistory);
    }
}