package entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CommonUserTest {

    private CommonUser user;

    @BeforeEach
    public void setUp() {
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
    public void getBalance() {
        assertEquals(Double.valueOf(9740.0), user.getBalance());
    }

    @Test
    public void setBalance() {
        user.setBalance(10000.0);
        assertEquals(Double.valueOf(10000.0), user.getBalance());
    }

    @Test
    public void getPortfolio() {
        assertEquals(Double.valueOf(3.0), user.getPortfolio().get("GOOGL"));
    }

    @Test
    public void addToPortfolio() {
        user.addToPortfolio("MSFT", 1.0);
        assertEquals(Double.valueOf(1.0), user.getPortfolio().get("MSFT"));
    }

    @Test
    public void isInPortfolio() {
        assertTrue(user.isInPortfolio("GOOGL"));
        assertFalse(user.isInPortfolio("AAPL"));
    }

    @Test
    public void removeFromPortfolio() {
        user.removeFromPortfolio("GOOGL");
        assertFalse(user.isInPortfolio("GOOGL"));
    }

    @Test
    public void clearPortfolio() {
        user.clearPortfolio();
        assertTrue(user.getPortfolio().isEmpty());
    }

}