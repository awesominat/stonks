package use_case;

import data_access.InMemoryUserDataAccessObject;
import entity.*;
import org.junit.jupiter.api.*;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
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
    public void testInfoFetch() {
        DashboardOutputBoundary dashboardPresenter = Mockito.mock(DashboardOutputBoundary.class);

        DashboardInteractor dashboardInteractor = new DashboardInteractor(userDataAccessObject, dashboardPresenter, mockApi);
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

        assertEquals(dashboardOutputData.getOwnedTickers().size(), 1);
        assertEquals(dashboardOutputData.getOwnedAmounts().size(), 1);
        assertEquals(dashboardOutputData.getOwnedAmounts().get(0), 10.0);
        assertEquals(dashboardOutputData.getOwnedTickers().get(0), "AAPL");
    }

    @Test
    public void testRefreshCase() {
        DashboardOutputBoundary dashboardPresenter = Mockito.mock(DashboardOutputBoundary.class);

        DashboardInteractor dashboardInteractor = new DashboardInteractor(userDataAccessObject, dashboardPresenter, mockApi);
        DashboardInputData dashboardInputData = new DashboardInputData(true);
        dashboardInteractor.execute(dashboardInputData);

        ArgumentCaptor<DashboardOutputData> captor = ArgumentCaptor.forClass(DashboardOutputData.class);

        Mockito.verify(dashboardPresenter).prepareSuccessView(captor.capture());
        Mockito.verify(dashboardPresenter, Mockito.never()).prepareFailView(any(String.class));

        DashboardOutputData dashboardOutputData = captor.getValue();

        assertTrue(dashboardOutputData.getRefreshPressed());

        assertEquals(dashboardOutputData.getCurrentPriceStats().size(), 1);
        assertEquals(dashboardOutputData.getCurrentPriceStats().get(0).get(0), 100.0);
        assertEquals(dashboardOutputData.getCurrentPriceStats().get(0).get(1), -1.0);
        assertEquals(dashboardOutputData.getCurrentPriceStats().get(0).get(2), 2.0);

        assertNull(dashboardOutputData.getOwnedAmounts());
        assertNull(dashboardOutputData.getUserStats());
        assertNull(dashboardOutputData.getOwnedTickers());
    }


    @Test
    public void testInvalidTicker() {
        DashboardOutputBoundary dashboardPresenter = Mockito.mock(DashboardOutputBoundary.class);

        User capturedUser = userDataAccessObject.get();
        capturedUser.addToPortfolio("AAPL :)", 1.0);

        DashboardInteractor dashboardInteractor = new DashboardInteractor(userDataAccessObject, dashboardPresenter, mockApi);
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

        DashboardInteractor dashboardInteractor = new DashboardInteractor(userDataAccessObject, dashboardPresenter, mockApi);
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
}
