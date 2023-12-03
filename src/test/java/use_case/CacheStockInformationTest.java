package use_case;

import data_access.InMemoryUserDataAccessObject;
import entity.*;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import use_case.CacheStockInformation.CacheStockInformationInteractor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CacheStockInformationTest {
    private InMemoryUserDataAccessObject userDataAccessObject = new InMemoryUserDataAccessObject();
    APIAccessInterface mockApi = Mockito.mock(APIAccessInterface.class);

    @BeforeAll
    public void setupAPI() throws APIAccessInterface.TickerNotFoundException {
        mockApi = Mockito.mock(APIAccessInterface.class);

        Mockito.when(mockApi.getCurrentPrice("AAPL")).thenReturn(new PricePoint(LocalDateTime.now(), 100.0));
        Mockito.when(mockApi.getCurrentPrice("AAPL :)")).thenThrow(new APIAccessInterface.TickerNotFoundException("Ticker AAPL :) does not exist."));
        Mockito.when(mockApi.getCompanyProfile("AAPL")).thenReturn(new CompanyInformation("US",
                "Apple Inc", "AAPL", "https://www.apple.com/", "1980-12-12"));

        Mockito.when(mockApi.getCurrentStockInformation("AAPL")).thenReturn(
                new StockInformation(100.0, -1.0, 2.0));
        Mockito.when(mockApi.getCurrentStockInformation("AAPL :)")).thenThrow(
                new APIAccessInterface.TickerNotFoundException("Invalid ticker"));

    }

    @BeforeEach
    public void setupUser() {
        userDataAccessObject = new InMemoryUserDataAccessObject();

        User user = userDataAccessObject.get();
        user.addToPortfolio("AAPL", 10.0);

        List<Transaction> transactionList = new ArrayList<>();
        transactionList.add(new BuyTransaction(10.0, new PricePoint(LocalDateTime.now(), 100.0)));

        user.getHistory().put("AAPL", new TransactionHistory(
                new Stock(100.0, "Apple Inc", "AAPL"),
                transactionList
        ));
    }

    @AfterEach
    public void tearDown() {
        Mockito.clearInvocations(mockApi);
    }
    @Test
    public void testCachedStock() {
        CacheStockInformation cacheStockInformation = new CacheStockInformation();

        CacheStockInformationInteractor cacheStockInformationInteractor = new CacheStockInformationInteractor(
                userDataAccessObject, cacheStockInformation, mockApi);
        cacheStockInformationInteractor.execute();

        HashMap<String, List<Double>> stockInformationMap = cacheStockInformation.getStockInformationMap();

        assertEquals(stockInformationMap.size(), 1);
        assertEquals(stockInformationMap.get("AAPL").size(), 3);
        assertEquals(stockInformationMap.get("AAPL").get(0), 100.0);
        assertEquals(stockInformationMap.get("AAPL").get(1), -1.0);
        assertEquals(stockInformationMap.get("AAPL").get(2), 2.0);
    }

    @Test
    public void testInvalidTicker() {
        CacheStockInformation cacheStockInformation = new CacheStockInformation();

        User capturedUser = userDataAccessObject.get();
        capturedUser.addToPortfolio("AAPL :)", 1.0);

        CacheStockInformationInteractor cacheStockInformationInteractor = new CacheStockInformationInteractor(
                userDataAccessObject, cacheStockInformation, mockApi);
        cacheStockInformationInteractor.execute();

        assertEquals(capturedUser.getPortfolio().size(), 1);
    }
}
