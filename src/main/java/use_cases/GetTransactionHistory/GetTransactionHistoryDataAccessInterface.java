package use_cases.GetTransactionHistory;

import entities.User;

public interface GetTransactionHistoryDataAccessInterface {

    void save();

    User get();
}
