package entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class PricePointTest {

    @Test
    void setTimeStamp() {
        // Given
        LocalDateTime initialTimestamp = LocalDateTime.now();
        PricePoint pricePoint = new PricePoint(initialTimestamp, 100.0);

        // When
        LocalDateTime newTimestamp = initialTimestamp.plusHours(1);
        pricePoint.setTimeStamp(newTimestamp);

        // Then
        assertEquals(newTimestamp, pricePoint.getTimeStamp());
    }

    @Test
    void setPrice() {
        // Given
        LocalDateTime timestamp = LocalDateTime.now();
        PricePoint pricePoint = new PricePoint(timestamp, 100.0);

        // When
        Double newPrice = 120.0;
        pricePoint.setPrice(newPrice);

        // Then
        assertEquals(newPrice, pricePoint.getPrice());
    }

    @Test
    void testToString() {
        // Given
        LocalDateTime timestamp = LocalDateTime.of(2023, 1, 1, 12, 0);
        PricePoint pricePoint = new PricePoint(timestamp, 150.0);

        // When
        String result = pricePoint.toString();

        // Then
        assertEquals("(2023-01-01T12:00: price is 150.0)", result);
    }

}