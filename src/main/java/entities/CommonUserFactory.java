package entities;

import java.util.HashMap;

public class CommonUserFactory implements UserFactory {
    public User create() {
        Double initialBalance = 1000.0;
        HashMap<String, Double> initialPortfolio = new HashMap<>();
        HashMap<String, TransactionHistory> history = new HashMap<String, TransactionHistory>();

        return new CommonUser(initialPortfolio, history, initialBalance);
    }
}
