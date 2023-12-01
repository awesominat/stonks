package use_case;

import data_access.InMemoryUserDataAccessObject;
import entity.*;
import org.junit.jupiter.api.*;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import use_case.Buy.*;

import java.time.LocalDate;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BuyTest {
    private InMemoryUserDataAccessObject userDataAccessObject;
    APIAccessInterface mockApi;

    @BeforeAll
    public void setupAPI() throws APIAccessInterface.TickerNotFoundException {
        mockApi = Mockito.mock(APIAccessInterface.class);

        Mockito.when(mockApi.getCurrentPrice("AAPL")).thenReturn(new PricePoint(LocalDate.now(), 100.0));
        Mockito.when(mockApi.getCurrentPrice("AAPL :)")).thenThrow(new APIAccessInterface.TickerNotFoundException("Ticker AAPL :) does not exist."));
        Mockito.when(mockApi.getCompanyProfile("AAPL")).thenReturn(new CompanyInformation("US",
                "Apple Inc", "AAPL", "https://www.apple.com/", "1980-12-12"));
    }

    @BeforeEach
    public void setupUser() {
        userDataAccessObject = new InMemoryUserDataAccessObject();
    }

    @AfterEach
    public void tearDown() {
        Mockito.clearInvocations(mockApi);
    }
    @Test
    public void testValidPurchase() throws APIAccessInterface.TickerNotFoundException {
        BuyOutputBoundary buyPresenter = Mockito.mock(BuyOutputBoundary.class);

        BuyInteractor buyInteractor = new BuyInteractor(userDataAccessObject, buyPresenter, mockApi);
        BuyInputData buyInputData = new BuyInputData(10.0, "AAPL");
        buyInteractor.execute(buyInputData);

        ArgumentCaptor<BuyOutputData> captor = ArgumentCaptor.forClass(BuyOutputData.class);

        Mockito.verify(buyPresenter).prepareSuccessView(captor.capture());
        Mockito.verify(buyPresenter, Mockito.never()).prepareFailView(any(String.class));

        Mockito.verify(mockApi).getCurrentPrice("AAPL");

        BuyOutputData buyOutputData = captor.getValue();

        User capturedUser = userDataAccessObject.get();
        assertTrue(buyOutputData.getExecutedPurchase());
        assertEquals(buyOutputData.getNewBalance(), 9000.0);
        assertEquals(capturedUser.getBalance(), buyOutputData.getNewBalance());
        assertTrue(capturedUser.isInPortfolio("AAPL"));
        assertEquals(capturedUser.getStockOwned("AAPL"), 10.0);

        HashMap<String, TransactionHistory> history = capturedUser.getHistory();
        assertTrue(history.containsKey("AAPL"));
        assertEquals(history.get("AAPL").getStock().getFullName(), "Apple Inc");

        Transaction mostRecentTransaction = history.get("AAPL").getMostRecentTransaction();

        assertEquals(mostRecentTransaction.getType(), TransactionType.BUY);
        assertEquals(mostRecentTransaction.getAmount(), 10.0);
        assertEquals(mostRecentTransaction.getPricePoint().getPrice(), 100.0);
        assertEquals(mostRecentTransaction.getPricePoint().getTimeStamp(), LocalDate.now());
    }

    @Test
    public void testGetBalance() throws APIAccessInterface.TickerNotFoundException {
        BuyOutputBoundary buyPresenter = Mockito.mock(BuyOutputBoundary.class);

        BuyInteractor buyInteractor = new BuyInteractor(userDataAccessObject, buyPresenter, mockApi);
        BuyInputData buyInputData = new BuyInputData();
        buyInteractor.execute(buyInputData);

        ArgumentCaptor<BuyOutputData> captor = ArgumentCaptor.forClass(BuyOutputData.class);

        Mockito.verify(buyPresenter).prepareSuccessView(captor.capture());
        Mockito.verify(buyPresenter, Mockito.never()).prepareFailView(any(String.class));

        BuyOutputData buyOutputData = captor.getValue();

        User capturedUser = userDataAccessObject.get();
        assertFalse(buyOutputData.getExecutedPurchase());
        assertEquals(buyOutputData.getNewBalance(), 10000.0);
        assertEquals(capturedUser.getBalance(), buyOutputData.getNewBalance());
        assertEquals(capturedUser.getPortfolio().size(), 0);
    }

    @Test
    public void testNegativeAmount() throws APIAccessInterface.TickerNotFoundException {
        BuyOutputBoundary buyPresenter = Mockito.mock(BuyOutputBoundary.class);

        BuyInteractor buyInteractor = new BuyInteractor(userDataAccessObject, buyPresenter, mockApi);
        BuyInputData buyInputData = new BuyInputData(-4.0, "AAPL");
        buyInteractor.execute(buyInputData);

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);

        Mockito.verify(buyPresenter).prepareFailView(captor.capture());
        Mockito.verify(buyPresenter, Mockito.never()).prepareSuccessView(any(BuyOutputData.class));

        String errorMessage = captor.getValue();

        assertEquals(errorMessage, "Please enter a decimal value greater than 0");
    }

    @Test
    public void testInvalidTicker() throws APIAccessInterface.TickerNotFoundException {
        BuyOutputBoundary buyPresenter = Mockito.mock(BuyOutputBoundary.class);

        BuyInteractor buyInteractor = new BuyInteractor(userDataAccessObject, buyPresenter, mockApi);
        BuyInputData buyInputData = new BuyInputData(5.0, "AAPL :)");
        buyInteractor.execute(buyInputData);

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);

        Mockito.verify(buyPresenter).prepareFailView(captor.capture());
        Mockito.verify(buyPresenter, Mockito.never()).prepareSuccessView(any(BuyOutputData.class));

        String errorMessage = captor.getValue();

        assertEquals(errorMessage, "Please enter a valid ticker.");
    }

    @Test
    public void testBuyNews() throws APIAccessInterface.TickerNotFoundException {
        BuyOutputBoundary buyPresenter = Mockito.mock(BuyOutputBoundary.class);

        BuyInteractor buyInteractor = new BuyInteractor(userDataAccessObject, buyPresenter, mockApi);
        BuyInputData buyInputData = new BuyInputData("AAPL");
        buyInteractor.execute(buyInputData);

        ArgumentCaptor<BuySearchOutputData> captor = ArgumentCaptor.forClass(BuySearchOutputData.class);

        Mockito.verify(buyPresenter).prepareSuccessView(captor.capture());
        Mockito.verify(buyPresenter, Mockito.never()).prepareFailView(any(String.class));

        BuySearchOutputData companyNews = captor.getValue();

        assertEquals(companyNews.getTicker(), "AAPL");
        assertEquals(companyNews.getCurBalance(), userDataAccessObject.get().getBalance());
        assertEquals(companyNews.getStringMap().get("country"), "US");
        assertEquals(companyNews.getStringMap().get("weburl"), "https://www.apple.com/");
        assertEquals(companyNews.getStringMap().get("currently held"), "0.0");
    }

    @Test
    public void testBuyNewsInvalid() throws APIAccessInterface.TickerNotFoundException {
        BuyOutputBoundary buyPresenter = Mockito.mock(BuyOutputBoundary.class);

        BuyInteractor buyInteractor = new BuyInteractor(userDataAccessObject, buyPresenter, mockApi);
        BuyInputData buyInputData = new BuyInputData("AAPL :)");
        buyInteractor.execute(buyInputData);

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);

        Mockito.verify(buyPresenter).prepareFailView(captor.capture());
        Mockito.verify(buyPresenter, Mockito.never()).prepareSuccessView(any(BuyOutputData.class));

        String errorMessage = captor.getValue();

        assertEquals(errorMessage, "Please enter a valid ticker.");
    }
}
