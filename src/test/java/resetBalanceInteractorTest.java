import entity.*;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import use_case.APIAccessInterface;
import use_case.Buy.*;
import use_case.ResetBalance.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;

import static org.mockito.ArgumentMatchers.any;

public class resetBalanceInteractorTest {

    @Test
    public void testReset() throws APIAccessInterface.TickerNotFoundException {
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

        Mockito.when(mockApi.getAppName()).thenReturn("RESET");

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

        // testBuy and then reset

        ResetBalanceInputData resetBalanceInputData = new ResetBalanceInputData(true);

        ResetBalanceOutputBoundary mockResetBalancePresenter = Mockito.mock(ResetBalanceOutputBoundary.class);

        ResetBalanceDataAccessInterface userDataAccessInterface = Mockito.mock(ResetBalanceDataAccessInterface.class);

        Mockito.when(userDataAccessInterface.get()).thenReturn(capturedUser);
        ResetBalanceInteractor resetBalanceInteractor = new ResetBalanceInteractor(
                userDataAccessInterface,
                mockResetBalancePresenter,
                mockApi);


        ArgumentCaptor<ResetBalanceOutputData> captor = ArgumentCaptor.forClass(ResetBalanceOutputData.class);

        resetBalanceInteractor.execute(resetBalanceInputData);
        Mockito.verify(mockResetBalancePresenter).prepareSuccessView(captor.capture());
        Mockito.verify(userDataAccessInterface).save();

        ResetBalanceOutputData resetBalanceOutputData = captor.getValue();

        assert(captor.getValue().isResetPressed());
        assert capturedUser.getBalance().equals(10000.0);
        assert capturedUser.getPortfolio().isEmpty();
        HashMap<String, TransactionHistory> userHistory = capturedUser.getHistory();
        assert userHistory.containsKey("RESET");

        int result = 1;
        for (String stock: userHistory.keySet()) {
            if (stock.equals("RESET")) {
                TransactionHistory transactionHistory = userHistory.get(stock);


                for (Transaction transaction : transactionHistory) {
                    result = transaction.getPricePoint().getTimeStamp().compareTo(LocalDate.now());
                }
            }

        }
        assert result == 0;


    }
}
