import entity.CommonUser;
import entity.CompanyInformation;
import entity.PricePoint;
import entity.User;
import use_case.Buy.BuyOutputBoundary;
import use_case.Sell.SellOutputBoundary;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import use_case.APIAccessInterface;
import use_case.Buy.BuyDataAccessInterface;
import use_case.Buy.BuyInputData;
import use_case.Buy.BuyInteractor;
import use_case.Buy.BuyOutputData;
import use_case.Sell.SellDataAccessInterface;
import use_case.Sell.SellInputData;
import use_case.Sell.SellInteractor;
import use_case.Sell.SellOutputData;

import java.io.IOException;
import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;

public class buySellTest {
    @Test
    public void testBuy() throws IOException {
        APIAccessInterface mockApi = Mockito.mock(APIAccessInterface.class);
        BuyDataAccessInterface userDataAccessObject = Mockito.mock(BuyDataAccessInterface.class);

        Mockito.when(userDataAccessObject.get()).thenReturn(new CommonUser());
        Mockito.when(mockApi.getCurrentPrice("AAPL")).thenReturn(new PricePoint(LocalDate.now(), 100.0));
        Mockito.when(mockApi.getCompanyProfile("AAPL")).thenReturn(new CompanyInformation("US",
                "Apple Inc", "AAPL", "https://www.apple.com/", "1980-12-12"));

        BuyInputData buyInputData = new BuyInputData(10.0, "AAPL");

        BuyOutputBoundary mockBuyPresenter = Mockito.mock(BuyOutputBoundary.class);

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);

        BuyInteractor buyInteractor = new BuyInteractor(userDataAccessObject, mockBuyPresenter, mockApi);
        buyInteractor.execute(buyInputData);

        Mockito.verify(mockApi).getCurrentPrice("AAPL");
        Mockito.verify(mockBuyPresenter).prepareSuccessView(any(BuyOutputData.class));
        Mockito.verify(userDataAccessObject).save();

        User capturedUser = userDataAccessObject.get(); //userCaptor.getValue();

        assert capturedUser.getBalance().equals(9000.0);
    }

    @Test
    public void testSell() throws IOException {
        APIAccessInterface mockApi = Mockito.mock(APIAccessInterface.class);
        SellDataAccessInterface userDataAccessObject = Mockito.mock(SellDataAccessInterface.class);

        User mockUser = new CommonUser();
        mockUser.addToPortfolio("AAPL", 10.0);

        Mockito.when(userDataAccessObject.get()).thenReturn(mockUser);

        Mockito.when(mockApi.getCurrentPrice("AAPL")).thenReturn(new PricePoint(LocalDate.now(), 100.0));
        Mockito.when(mockApi.getCompanyProfile("AAPL")).thenReturn(new CompanyInformation("US",
                "Apple Inc", "AAPL", "https://www.apple.com/", "1980-12-12"));

        SellInputData sellInputData = new SellInputData(10.0, "AAPL");

        SellOutputBoundary mockSellPresenter = Mockito.mock(SellOutputBoundary.class);

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);

        SellInteractor sellInteractor = new SellInteractor(userDataAccessObject, mockSellPresenter, mockApi);
        sellInteractor.execute(sellInputData);

        Mockito.verify(mockApi).getCurrentPrice("AAPL");
        Mockito.verify(mockSellPresenter).prepareSuccessView(any(SellOutputData.class));
        Mockito.verify(userDataAccessObject).save(); // userCaptor.capture());

//        User capturedUser = userCaptor.getValue();
        assert mockUser.getBalance().equals(11000.0);
        assert mockUser.getPortfolio().isEmpty();
    }
}
