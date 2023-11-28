package entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CommonUserTest {

    private CommonUser user;

    @BeforeEach
    void setUp() {
        HashMap<String, Double> portfolio = new HashMap<>();
        HashMap<String, TransactionHistory> history = new HashMap<>();
        Double balance = 9740.0;

        portfolio.put("GOOGL", 3.0);

        Stock googleStock = new Stock(120.0, "Google", "GOOGL");
        List<Transaction> googleTransactions = new ArrayList<>();
        googleTransactions.add(new BuyTransaction(3.0, new PricePoint(LocalDate.now(), 120.0)));

        history.put("GOOGL", new TransactionHistory(googleStock, googleTransactions));

        user = new CommonUser(portfolio, history, balance);
    }

    @Test
    void getBalance() {
        assertEquals(9740.0, user.getBalance());
    }

    @Test
    void setBalance() {
        user.setBalance(10000.0);
        assertEquals(10000.0, user.getBalance());
    }

    @Test
    void getPortfolio() {
        assertEquals(3.0, user.getPortfolio().get("GOOGL"));
    }

    @Test
    void addToPortfolio() {
        user.addToPortfolio("MSFT", 1.0);
        assertEquals(1.0, user.getPortfolio().get("MSFT"));
    }

    @Test
    void isInPortfolio() {
        assertTrue(user.isInPortfolio("GOOGL"));
        assertFalse(user.isInPortfolio("AAPL"));
    }

    @Test
    void removeFromPortfolio() {
        user.removeFromPortfolio("GOOGL");
        assertFalse(user.isInPortfolio("GOOGL"));
    }

    @Test
    void clearPortfolio() {
        user.clearPortfolio();
        assertTrue(user.getPortfolio().isEmpty());
    }

}