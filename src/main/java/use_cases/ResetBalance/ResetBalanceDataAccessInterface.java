package use_cases.ResetBalance;

import entities.User;

public interface ResetBalanceDataAccessInterface {
    void save(User user);

    User get(String username);
}
