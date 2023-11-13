package use_cases.Buy;

import entities.User;

public interface BuyDataAccessInterface {
    void save(User user);

    User get(String username);
}
