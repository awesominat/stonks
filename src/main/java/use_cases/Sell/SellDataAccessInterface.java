package use_cases.Sell;
import entities.User;

public interface SellDataAccessInterface {
    void save(User user);
    User get(String username);
}
