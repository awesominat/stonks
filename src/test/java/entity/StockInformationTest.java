package entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StockInformationTest {

    @Test
    void getPriceChange() {
        // Given
        Double initialPriceChange = 5.0;
        StockInformation stockInformation = new StockInformation(100.0, initialPriceChange, 5.0);

        // When
        Double result = stockInformation.getPriceChange();

        // Then
        assertEquals(initialPriceChange, result);
    }

    @Test
    void setPriceChange() {
        // Given
        Double initialPriceChange = 5.0;
        StockInformation stockInformation = new StockInformation(100.0, initialPriceChange, 5.0);

        // When
        Double newPriceChange = 7.0;
        stockInformation.setPriceChange(newPriceChange);

        // Then
        assertEquals(newPriceChange, stockInformation.getPriceChange());
    }

    @Test
    void getPercentChange() {
        // Given
        Double initialPercentChange = 5.0;
        StockInformation stockInformation = new StockInformation(100.0, 7.0, initialPercentChange);

        // When
        Double result = stockInformation.getPercentChange();

        // Then
        assertEquals(initialPercentChange, result);
    }

    @Test
    void setPercentChange() {
        // Given
        Double initialPercentChange = 5.0;
        StockInformation stockInformation = new StockInformation(100.0, 7.0, initialPercentChange);

        // When
        Double newPercentChange = 10.0;
        stockInformation.setPercentChange(newPercentChange);

        // Then
        assertEquals(newPercentChange, stockInformation.getPercentChange());
    }

    @Test
    void getCurrentPrice() {
        // Given
        Double initialCurrentPrice = 100.0;
        StockInformation stockInformation = new StockInformation(initialCurrentPrice, 7.0, 5.0);

        // When
        Double result = stockInformation.getCurrentPrice();

        // Then
        assertEquals(initialCurrentPrice, result);
    }

    @Test
    void setCurrentPrice() {
        // Given
        Double initialCurrentPrice = 100.0;
        StockInformation stockInformation = new StockInformation(initialCurrentPrice, 7.0, 5.0);

        // When
        Double newCurrentPrice = 120.0;
        stockInformation.setCurrentPrice(newCurrentPrice);

        // Then
        assertEquals(newCurrentPrice, stockInformation.getCurrentPrice());
    }
    
}