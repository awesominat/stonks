package use_case;

import data_access.InMemoryUserDataAccessObject;
import entity.*;
import org.junit.jupiter.api.*;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import use_case.CacheStockInformation.CacheStockInformationInteractor;
import use_case.Dashboard.DashboardInputData;
import use_case.Dashboard.DashboardInteractor;
import use_case.Dashboard.DashboardOutputBoundary;
import use_case.Dashboard.DashboardOutputData;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DashboardTest {
    private InMemoryUserDataAccessObject userDataAccessObject = new InMemoryUserDataAccessObject(new CommonUserFactory());
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
        userDataAccessObject = new InMemoryUserDataAccessObject(new CommonUserFactory());

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
    public void testInfoFetch() {
        DashboardOutputBoundary dashboardPresenter = Mockito.mock(DashboardOutputBoundary.class);
        CacheStockInformation cacheStockInformation = new CacheStockInformation();

        DashboardInteractor dashboardInteractor = new DashboardInteractor(
                userDataAccessObject, cacheStockInformation, dashboardPresenter, mockApi);
        DashboardInputData dashboardInputData = new DashboardInputData(false);
        dashboardInteractor.execute(dashboardInputData);

        ArgumentCaptor<DashboardOutputData> captor = ArgumentCaptor.forClass(DashboardOutputData.class);

        Mockito.verify(dashboardPresenter).prepareSuccessView(captor.capture());
        Mockito.verify(dashboardPresenter, Mockito.never()).prepareFailView(any(String.class));

        DashboardOutputData dashboardOutputData = captor.getValue();

        User capturedUser = userDataAccessObject.get();
        assertFalse(dashboardOutputData.getRefreshPressed());
        HashMap<String, Double> userStats = dashboardOutputData.getUserStats();

        assertEquals(userStats.size(), 4);
        assertEquals(userStats.get("balance"), 10000.0);
        assertEquals(userStats.get("balance"), capturedUser.getBalance());
        assertEquals(userStats.get("Net worth"), 11000.0);
        assertEquals(userStats.get("Days since last reset"), -1.0);
        assertEquals(userStats.get("Portfolio net worth"), 1000.0);

        assertNull(dashboardOutputData.getStockPriceInformationTable());
        assertEquals(dashboardOutputData.getOwnedStocksInformationTable().size(), 1);
        assertTrue(dashboardOutputData.getOwnedStocksInformationTable().containsKey("AAPL"));
        assertEquals(dashboardOutputData.getOwnedStocksInformationTable().get("AAPL"), 10.0);

        assertEquals(cacheStockInformation.getStockInformationMap().size(), 0);
    }

    @Test
    public void testRefreshCase() {
        DashboardOutputBoundary dashboardPresenter = Mockito.mock(DashboardOutputBoundary.class);
        CacheStockInformation cacheStockInformation = new CacheStockInformation();

        DashboardInteractor dashboardInteractor = new DashboardInteractor(
                userDataAccessObject, cacheStockInformation, dashboardPresenter, mockApi);
        DashboardInputData dashboardInputData = new DashboardInputData(true);
        dashboardInteractor.execute(dashboardInputData);

        ArgumentCaptor<DashboardOutputData> captor = ArgumentCaptor.forClass(DashboardOutputData.class);

        Mockito.verify(dashboardPresenter).prepareSuccessView(captor.capture());
        Mockito.verify(dashboardPresenter, Mockito.never()).prepareFailView(any(String.class));

        DashboardOutputData dashboardOutputData = captor.getValue();

        assertTrue(dashboardOutputData.getRefreshPressed());

        assertEquals(cacheStockInformation.getStockInformationMap().size(), 0);
        assertEquals(dashboardOutputData.getStockPriceInformationTable().size(), 1);
        assertTrue(dashboardOutputData.getStockPriceInformationTable().containsKey("AAPL"));
        assertEquals(dashboardOutputData.getStockPriceInformationTable().get("AAPL").get(0), 100.0);
        assertEquals(dashboardOutputData.getStockPriceInformationTable().get("AAPL").get(1), -1.0);
        assertEquals(dashboardOutputData.getStockPriceInformationTable().get("AAPL").get(2), 2.0);

        assertNull(dashboardOutputData.getOwnedStocksInformationTable());
        assertNull(dashboardOutputData.getUserStats());
    }


    @Test
    public void testInvalidTicker() {
        CacheStockInformation cacheStockInformation = new CacheStockInformation();
        DashboardOutputBoundary dashboardPresenter = Mockito.mock(DashboardOutputBoundary.class);

        User capturedUser = userDataAccessObject.get();
        capturedUser.addToPortfolio("AAPL :)", 1.0);

        DashboardInteractor dashboardInteractor = new DashboardInteractor(
                userDataAccessObject, cacheStockInformation, dashboardPresenter, mockApi);
        DashboardInputData dashboardInputData = new DashboardInputData(true);
        dashboardInteractor.execute(dashboardInputData);

        ArgumentCaptor<DashboardOutputData> captor = ArgumentCaptor.forClass(DashboardOutputData.class);

        Mockito.verify(dashboardPresenter).prepareSuccessView(captor.capture());
        Mockito.verify(dashboardPresenter, Mockito.never()).prepareFailView(any(String.class));

        DashboardOutputData dashboardOutputData = captor.getValue();

        assertTrue(dashboardOutputData.getRefreshPressed());
        assertEquals(capturedUser.getPortfolio().size(), 1);
    }

    @Test
    public void testDaysSinceReset() {
        CacheStockInformation cacheStockInformation = new CacheStockInformation();
        DashboardOutputBoundary dashboardPresenter = Mockito.mock(DashboardOutputBoundary.class);

        User capturedUser = userDataAccessObject.get();

        Double previousBalance = capturedUser.getBalance();
        List<Transaction> transactionList = new ArrayList<>();
        transactionList.add(new TopupTransaction(1.0, new PricePoint(LocalDateTime.now().minusDays(5), 10000.0)));
        transactionList.add(new TopupTransaction(1.0, new PricePoint(LocalDateTime.now().minusMonths(1), 10000.0)));

        Stock newStock = new Stock(previousBalance, "RESET", "RESET");
        capturedUser.getHistory().put("RESET", new TransactionHistory(
                newStock,
                transactionList
        ));

        DashboardInteractor dashboardInteractor = new DashboardInteractor(
                userDataAccessObject, cacheStockInformation, dashboardPresenter, mockApi);
        DashboardInputData dashboardInputData = new DashboardInputData(false);
        dashboardInteractor.execute(dashboardInputData);

        ArgumentCaptor<DashboardOutputData> captor = ArgumentCaptor.forClass(DashboardOutputData.class);

        Mockito.verify(dashboardPresenter).prepareSuccessView(captor.capture());
        Mockito.verify(dashboardPresenter, Mockito.never()).prepareFailView(any(String.class));

        DashboardOutputData dashboardOutputData = captor.getValue();

        HashMap<String, Double> userStats = dashboardOutputData.getUserStats();
        assertFalse(dashboardOutputData.getRefreshPressed());
        assertEquals(userStats.get("Days since last reset"), 5.0);
    }

    @Test
    public void testCacheStockInformationPropertyChanged() {
        CacheStockInformation cacheStockInformation = new CacheStockInformation();
        DashboardOutputBoundary dashboardPresenter = Mockito.mock(DashboardOutputBoundary.class);

        CacheStockInformationInteractor cacheStockInformationInteractor = new CacheStockInformationInteractor(
                userDataAccessObject, cacheStockInformation, mockApi);
//
        DashboardInteractor dashboardInteractor = new DashboardInteractor(
                userDataAccessObject, cacheStockInformation, dashboardPresenter, mockApi);

        cacheStockInformationInteractor.execute();

        ArgumentCaptor<DashboardOutputData> argumentCaptor = ArgumentCaptor.forClass(DashboardOutputData.class);
        Mockito.verify(dashboardPresenter).prepareSuccessView(argumentCaptor.capture());
        DashboardOutputData capturedData = argumentCaptor.getValue();

        assertTrue(capturedData.getRefreshPressed());
        assertEquals(capturedData.getStockPriceInformationTable().size(), 1);
        assertTrue(capturedData.getStockPriceInformationTable().containsKey("AAPL"));
        assertEquals(capturedData.getStockPriceInformationTable().get("AAPL").get(0), 100.0);
        assertEquals(capturedData.getStockPriceInformationTable().get("AAPL").get(1), -1.0);
        assertEquals(capturedData.getStockPriceInformationTable().get("AAPL").get(2), 2.0);
        assertNull(capturedData.getOwnedStocksInformationTable());
        assertNull(capturedData.getUserStats());
    }
}
