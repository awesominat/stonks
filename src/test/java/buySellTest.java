import entities.CommonUser;
import entities.CompanyInformation;
import entities.PricePoint;
import entities.User;
import interface_adapters.Buy.BuyOutputBoundary;
import interface_adapters.Sell.SellOutputBoundary;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import use_cases.APIAccessInterface;
import use_cases.Buy.BuyDataAccessInterface;
import use_cases.Buy.BuyInputData;
import use_cases.Buy.BuyInteractor;
import use_cases.Buy.BuyOutputData;
import use_cases.Sell.SellDataAccessInterface;
import use_cases.Sell.SellInputData;
import use_cases.Sell.SellInteractor;
import use_cases.Sell.SellOutputData;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;

public class buySellTest {
    @Test
    public void testBuy() throws IOException {
        APIAccessInterface mockApi = Mockito.mock(APIAccessInterface.class);
        BuyDataAccessInterface userDataAccessObject = Mockito.mock(BuyDataAccessInterface.class);

        List<PricePoint> lastMonthPrices = new ArrayList<>();
        lastMonthPrices.add(new PricePoint(LocalDate.now(), 100.0));

        Mockito.when(userDataAccessObject.get("zain")).thenReturn(new CommonUser("zain", "mypass"));
        Mockito.when(mockApi.getCurrentPrice("AAPL")).thenReturn(new PricePoint(LocalDate.now(), 100.0));
        Mockito.when(mockApi.getCompanyProfile("AAPL")).thenReturn(new CompanyInformation("US",
                "Apple Inc", "AAPL", "https://www.apple.com/", "1980-12-12"));
        Mockito.when(mockApi.getLastMonthPrices("AAPL")).thenReturn(lastMonthPrices);

        BuyInputData buyInputData = new BuyInputData(10.0, "AAPL", "zain");

        BuyOutputBoundary mockBuyPresenter = Mockito.mock(BuyOutputBoundary.class);

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);

        BuyInteractor buyInteractor = new BuyInteractor(userDataAccessObject, mockBuyPresenter, mockApi);
        buyInteractor.execute(buyInputData);

        Mockito.verify(mockApi).getCurrentPrice("AAPL");
        Mockito.verify(mockBuyPresenter).prepareSuccessView(any(BuyOutputData.class));
        Mockito.verify(userDataAccessObject).save(userCaptor.capture());

        User capturedUser = userCaptor.getValue();

        assert capturedUser.getBalance().equals(9000.0);
    }

    @Test
    public void testSell() throws IOException {
        APIAccessInterface mockApi = Mockito.mock(APIAccessInterface.class);
        SellDataAccessInterface userDataAccessObject = Mockito.mock(SellDataAccessInterface.class);

        List<PricePoint> lastMonthPrices = new ArrayList<>();
        lastMonthPrices.add(new PricePoint(LocalDate.now(), 100.0));

        User mockUser = new CommonUser("zain", "mypass");
        mockUser.addToPortfolio("AAPL", 10.0);

        Mockito.when(userDataAccessObject.get("zain")).thenReturn(mockUser);

        Mockito.when(mockApi.getCurrentPrice("AAPL")).thenReturn(new PricePoint(LocalDate.now(), 100.0));
        Mockito.when(mockApi.getCompanyProfile("AAPL")).thenReturn(new CompanyInformation("US",
                "Apple Inc", "AAPL", "https://www.apple.com/", "1980-12-12"));
        Mockito.when(mockApi.getLastMonthPrices("AAPL")).thenReturn(lastMonthPrices);

        SellInputData sellInputData = new SellInputData(10.0, "AAPL", "zain");

        SellOutputBoundary mockSellPresenter = Mockito.mock(SellOutputBoundary.class);

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);

        SellInteractor sellInteractor = new SellInteractor(userDataAccessObject, mockSellPresenter, mockApi);
        sellInteractor.execute(sellInputData);

        Mockito.verify(mockApi).getCurrentPrice("AAPL");
        Mockito.verify(mockSellPresenter).prepareSuccessView(any(SellOutputData.class));
        Mockito.verify(userDataAccessObject).save(userCaptor.capture());

        User capturedUser = userCaptor.getValue();
        assert capturedUser.getBalance().equals(11000.0);
        assert capturedUser.getPortfolio().isEmpty();
    }
}