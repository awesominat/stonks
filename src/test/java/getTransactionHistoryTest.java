import entities.CommonUser;
import entities.CompanyInformation;
import entities.PricePoint;
import entities.User;
import interface_adapters.GetTransactionHistory.GetTransactionHistoryController;
import use_cases.Buy.BuyOutputBoundary;
import use_cases.GetTransactionHistory.*;
import use_cases.Sell.SellOutputBoundary;
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


import static org.mockito.ArgumentMatchers.any;
public class getTransactionHistoryTest {

    @Test
    public void testGetTransactionHistory() throws IOException {
        APIAccessInterface mockApi = Mockito.mock(APIAccessInterface.class);
        BuyDataAccessInterface userDataAccessObject = Mockito.mock(BuyDataAccessInterface.class);

        Mockito.when(userDataAccessObject.get()).thenReturn(new CommonUser());
        Mockito.when(mockApi.getCurrentPrice("AAPL")).thenReturn(new PricePoint(LocalDate.now(), 100.0));
        Mockito.when(mockApi.getCompanyProfile("AAPL")).thenReturn(
                new CompanyInformation(
                        "US",
                        "Apple Inc",
                        "AAPL",
                        "https://www.apple.com/",
                        "1980-12-12")
        );

        BuyInputData buyInputData = new BuyInputData(10.0, "AAPL");

        BuyOutputBoundary mockBuyPresenter = Mockito.mock(BuyOutputBoundary.class);

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);

        BuyInteractor buyInteractor = new BuyInteractor(
                userDataAccessObject,
                mockBuyPresenter,
                mockApi
        );

        buyInteractor.execute(buyInputData);

        Mockito.verify(mockApi).getCurrentPrice("AAPL");
        Mockito.verify(mockBuyPresenter).prepareSuccessView(any(BuyOutputData.class));
        Mockito.verify(userDataAccessObject).save();

        User capturedUser = userDataAccessObject.get(); //userCaptor.getValue();

        assert capturedUser.getBalance().equals(9000.0);

        // GetTransactionHistoryUseCase Testing
        GetTransactionHistoryDataAccessInterface getTransactionHistoryUserDataObject = Mockito.mock(
                GetTransactionHistoryDataAccessInterface.class);
        Mockito.when(getTransactionHistoryUserDataObject.get()).thenReturn(new CommonUser());

        GetTransactionHistoryOutputBoundary mockGetTransactionHistoryPresenter = Mockito.mock(
                GetTransactionHistoryOutputBoundary.class);



        GetTransactionHistoryInteractor getTransactionHistoryInteractor = new GetTransactionHistoryInteractor(
                getTransactionHistoryUserDataObject,
                mockGetTransactionHistoryPresenter
        );

        getTransactionHistoryInteractor.execute();

        Mockito.verify(mockGetTransactionHistoryPresenter).prepareSuccessView(any(
                GetTransactionHistoryOutputData.class)
        );
        // Check why it does not work
//        Mockito.verify(getTransactionHistoryUserDataObject).save();
//
//        User capturedUserForTransactionHistory = getTransactionHistoryUserDataObject.get();
//
//        assert capturedUserForTransactionHistory.getHistory().containsKey("AAPL");
    }
}
