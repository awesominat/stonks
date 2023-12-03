package entity;

import java.util.HashMap;

public class CommonUserFactory implements UserFactory {
    public User create() {
        Double initialBalance = 10000.0;
        HashMap<String, Double> initialPortfolio = new HashMap<>();
        HashMap<String, TransactionHistory> history = new HashMap<>();

        return new CommonUser(initialPortfolio, history, initialBalance);
    }
}
