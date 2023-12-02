package use_case;

import data_access.InMemoryUserDataAccessObject;
import entity.*;
import org.junit.jupiter.api.*;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import use_case.Sell.SellInputData;
import use_case.Sell.SellInteractor;
import use_case.Sell.SellOutputBoundary;
import use_case.Sell.SellOutputData;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SellTest {
    private InMemoryUserDataAccessObject userDataAccessObject = new InMemoryUserDataAccessObject();
    APIAccessInterface mockApi = Mockito.mock(APIAccessInterface.class);

    @BeforeAll
    public void setupAPI() throws APIAccessInterface.TickerNotFoundException {

        Mockito.when(mockApi.getCurrentPrice("AAPL")).thenReturn(new PricePoint(LocalDateTime.now(), 100.0));
        Mockito.when(mockApi.getCurrentPrice("AAPL :)")).thenThrow(new APIAccessInterface.TickerNotFoundException("Ticker AAPL :) does not exist."));
        Mockito.when(mockApi.getCompanyProfile("AAPL")).thenReturn(new CompanyInformation("US",
                "Apple Inc", "AAPL", "https://www.apple.com/", "1980-12-12"));
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
    public void testOwnedStocksAmountsBalance() throws APIAccessInterface.TickerNotFoundException {
        SellOutputBoundary sellPresenter = Mockito.mock(SellOutputBoundary.class);

        SellInteractor sellInteractor = new SellInteractor(userDataAccessObject, sellPresenter, mockApi);
        SellInputData sellInputData = new SellInputData();
        sellInteractor.execute(sellInputData);

        ArgumentCaptor<SellOutputData> captor = ArgumentCaptor.forClass(SellOutputData.class);

        Mockito.verify(sellPresenter).prepareSuccessView(captor.capture());
        Mockito.verify(sellPresenter, Mockito.never()).prepareFailView(any(String.class));

        SellOutputData sellOutputData = captor.getValue();

        User capturedUser = userDataAccessObject.get();
        assertFalse(sellOutputData.isExecuteTypeSell());
        assertEquals(sellOutputData.getOwnedStocks(), List.of("AAPL"));
        assertEquals(sellOutputData.getOwnedAmounts(), List.of(10.0));
        assertEquals(sellOutputData.getBalance(), 10000.0);
        assertEquals(capturedUser.getBalance(), sellOutputData.getBalance());
    }

    @Test
    public void testInvalidTicker() throws APIAccessInterface.TickerNotFoundException {
        SellOutputBoundary sellPresenter = Mockito.mock(SellOutputBoundary.class);

        SellInteractor sellInteractor = new SellInteractor(userDataAccessObject, sellPresenter, mockApi);
        SellInputData sellInputData = new SellInputData(5.0, "AAPL :)");
        sellInteractor.execute(sellInputData);

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);

        Mockito.verify(sellPresenter).prepareFailView(captor.capture());
        Mockito.verify(sellPresenter, Mockito.never()).prepareSuccessView(any(SellOutputData.class));

        String errorMessage = captor.getValue();

        assertEquals(errorMessage, "This ticker does not exist!");
    }

    @Test
    public void testValidSell() throws APIAccessInterface.TickerNotFoundException {
        SellOutputBoundary sellPresenter = Mockito.mock(SellOutputBoundary.class);

        SellInteractor sellInteractor = new SellInteractor(userDataAccessObject, sellPresenter, mockApi);
        SellInputData sellInputData = new SellInputData(5.0, "AAPL");
        sellInteractor.execute(sellInputData);

        // force sell just 5

        ArgumentCaptor<SellOutputData> captor = ArgumentCaptor.forClass(SellOutputData.class);

        Mockito.verify(sellPresenter).prepareSuccessView(captor.capture());
        Mockito.verify(sellPresenter, Mockito.never()).prepareFailView(any(String.class));

        Mockito.verify(mockApi).getCurrentPrice("AAPL");

        SellOutputData sellOutputData = captor.getValue();

        User capturedUser = userDataAccessObject.get();
        assertTrue(sellOutputData.isExecuteTypeSell());
        assertNull(sellOutputData.getBalance());
        assertEquals(sellOutputData.getAmount(), 5.0);
        assertEquals(sellOutputData.getTicker(), "AAPL");
        assertEquals(capturedUser.getBalance(), 10500.0);
        assertTrue(capturedUser.isInPortfolio("AAPL"));
        assertEquals(capturedUser.getStockOwned("AAPL"), 5.0);

        HashMap<String, TransactionHistory> history = capturedUser.getHistory();
        assertTrue(history.containsKey("AAPL"));
        assertEquals(history.get("AAPL").getStock().getFullName(), "Apple Inc");

        Transaction mostRecentTransaction = history.get("AAPL").getMostRecentTransaction();

        assertEquals(mostRecentTransaction.getType(), TransactionType.SELL);
        assertEquals(mostRecentTransaction.getAmount(), 5.0);
        assertEquals(mostRecentTransaction.getPricePoint().getPrice(), 100.0);


        LocalDateTime expectedTimestamp = mostRecentTransaction.getPricePoint().getTimeStamp();
        LocalDateTime actualTimestamp = LocalDateTime.now();

        long secondsDifference = ChronoUnit.SECONDS.between(expectedTimestamp, actualTimestamp);

        assertTrue(Math.abs(secondsDifference) < 5, "Timestamps are not close enough.");

        // force test selling ALL AAPL stock
        Mockito.clearInvocations(mockApi);
        Mockito.clearInvocations(sellPresenter);

        SellInputData sellAllInputData = new SellInputData(5.0, "AAPL");
        sellInteractor.execute(sellAllInputData);

        ArgumentCaptor<SellOutputData> captor2 = ArgumentCaptor.forClass(SellOutputData.class);

        Mockito.verify(sellPresenter).prepareSuccessView(captor2.capture());
        Mockito.verify(sellPresenter, Mockito.never()).prepareFailView(any(String.class));

        SellOutputData sellOutputData2 = captor.getValue();
        assertTrue(sellOutputData2.isExecuteTypeSell());
        assertNull(sellOutputData2.getBalance());
        assertEquals(capturedUser.getBalance(), 11000.0);
        assertFalse(capturedUser.isInPortfolio("AAPL"));
        assertNull(capturedUser.getStockOwned("AAPL"));
    }

    @Test
    public void testNegativeAmount() throws APIAccessInterface.TickerNotFoundException {
        SellOutputBoundary sellPresenter = Mockito.mock(SellOutputBoundary.class);

        SellInteractor sellInteractor = new SellInteractor(userDataAccessObject, sellPresenter, mockApi);
        SellInputData sellInputData = new SellInputData(-4.0, "AAPL");
        sellInteractor.execute(sellInputData);

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);

        Mockito.verify(sellPresenter).prepareFailView(captor.capture());
        Mockito.verify(sellPresenter, Mockito.never()).prepareSuccessView(any(SellOutputData.class));

        String errorMessage = captor.getValue();

        assertEquals(errorMessage, "Please enter a decimal value greater than 0");
    }
}
