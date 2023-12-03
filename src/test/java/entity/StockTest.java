package entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StockTest {

    @Test
    void getLastSeenPrice() {
        // Given
        Double initialPrice = 50.0;
        Stock stock = new Stock(initialPrice, "Example Stock", "EXM");

        // When
        Double result = stock.getLastSeenPrice();

        // Then
        assertEquals(initialPrice, result);
    }

    @Test
    void setLastSeenPrice() {
        // Given
        Double initialPrice = 50.0;
        Stock stock = new Stock(initialPrice, "Example Stock", "EXM");

        // When
        Double newPrice = 60.0;
        stock.setLastSeenPrice(newPrice);

        // Then
        assertEquals(newPrice, stock.getLastSeenPrice());
    }

    @Test
    void getPriceHistory() {
        // Given
        List<PricePoint> priceHistory = new ArrayList<>();
        priceHistory.add(new PricePoint(LocalDateTime.parse("2023-01-01T00:00:00"), 50.0));
        priceHistory.add(new PricePoint(LocalDateTime.parse("2023-01-02T00:00:00"), 55.0));

        Stock stock = new Stock(50.0, "Example Stock", "EXM");
        stock.setPriceHistory(priceHistory);

        // When
        List<PricePoint> result = stock.getPriceHistory();

        // Then
        assertEquals(priceHistory, result);
    }

    @Test
    void setPriceHistory() {
        // Given
        List<PricePoint> priceHistory = new ArrayList<>();
        priceHistory.add(new PricePoint(LocalDateTime.parse("2023-01-01T00:00:00"), 50.0));
        priceHistory.add(new PricePoint(LocalDateTime.parse("2023-01-02T00:00:00"), 55.0));

        Stock stock = new Stock(50.0, "Example Stock", "EXM");

        // When
        stock.setPriceHistory(priceHistory);

        // Then
        assertEquals(priceHistory, stock.getPriceHistory());
    }

    @Test
    void getFullName() {
        // Given
        Stock stock = new Stock(50.0, "Example Stock", "EXM");

        // When
        String result = stock.getFullName();

        // Then
        assertEquals("Example Stock", result);
    }

    @Test
    void getTicker() {
        // Given
        Stock stock = new Stock(50.0, "Example Stock", "EXM");

        // When
        String result = stock.getTicker();

        // Then
        assertEquals("EXM", result);
    }

    @Test
    void testToString() {
        // Given
        Stock stock = new Stock(50.0, "Example Stock", "EXM");

        // When
        String result = stock.toString();

        // Then
        assertEquals("Example Stock", result);
    }

}