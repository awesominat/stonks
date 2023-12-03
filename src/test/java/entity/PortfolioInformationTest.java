package entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PortfolioInformationTest {

    @Test
    void getTicker() {
        // Given
        PortfolioInformation portfolioInformation = new PortfolioInformation();
        portfolioInformation.setTicker("AAPL");

        // When
        String result = portfolioInformation.getTicker();

        // Then
        assertEquals("AAPL", result);
    }

    @Test
    void setTicker() {
        // Given
        PortfolioInformation portfolioInformation = new PortfolioInformation();

        // When
        portfolioInformation.setTicker("GOOGL");

        // Then
        assertEquals("GOOGL", portfolioInformation.getTicker());
    }

    @Test
    void getFullName() {
        // Given
        PortfolioInformation portfolioInformation = new PortfolioInformation();
        portfolioInformation.setFullName("Apple Inc");

        // When
        String result = portfolioInformation.getFullName();

        // Then
        assertEquals("Apple Inc", result);
    }

    @Test
    void setFullName() {
        // Given
        PortfolioInformation portfolioInformation = new PortfolioInformation();

        // When
        portfolioInformation.setFullName("Google Inc");

        // Then
        assertEquals("Google Inc", portfolioInformation.getFullName());
    }

    @Test
    void getAmount() {
        // Given
        PortfolioInformation portfolioInformation = new PortfolioInformation();
        portfolioInformation.setAmount(10.0);

        // When
        Double result = portfolioInformation.getAmount();

        // Then
        assertEquals(10.0, result);
    }

    @Test
    void setAmount() {
        // Given
        PortfolioInformation portfolioInformation = new PortfolioInformation();

        // When
        portfolioInformation.setAmount(15.0);

        // Then
        assertEquals(15.0, portfolioInformation.getAmount());
    }

    @Test
    void getPrice() {
        // Given
        PortfolioInformation portfolioInformation = new PortfolioInformation();
        portfolioInformation.setPrice(150.0);

        // When
        Double result = portfolioInformation.getPrice();

        // Then
        assertEquals(150.0, result);
    }

    @Test
    void setPrice() {
        // Given
        PortfolioInformation portfolioInformation = new PortfolioInformation();

        // When
        portfolioInformation.setPrice(73.0);

        // Then
        assertEquals(73.0, portfolioInformation.getPrice());
    }

}