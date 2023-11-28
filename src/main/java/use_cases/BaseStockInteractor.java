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

    protected TransactionHistory initHistory(String ticker, Double boughtAt) {
        CompanyInformation companyInformation = apiAccessInterface.getCompanyProfile(ticker);
        Stock newStock = new Stock(boughtAt, companyInformation.getName(), ticker);

        List<Transaction> transactions = new ArrayList<>();

        return new TransactionHistory(newStock, transactions);
    }
    protected void updatePortfolio(User user, String ticker, Double amount) {
        if (amount == 0 && user.isInPortfolio(ticker)) {
            user.removeFromPortfolio(ticker);
        } else if (user.isInPortfolio(ticker)) {
            user.addToPortfolio(ticker, user.getPortfolio().get(ticker) + amount);
        } else {
            user.addToPortfolio(ticker, amount);
        }
    }

    protected void addToHistory(HashMap<String, TransactionHistory> userHistory, String ticker,
                                Double currentPrice, Transaction transaction) {
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