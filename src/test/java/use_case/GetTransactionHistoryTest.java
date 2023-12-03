package use_case;

import entity.CommonUser;
import entity.CompanyInformation;
import entity.PricePoint;
import entity.User;
import interface_adapter.Dashboard.DashboardViewModel;
import interface_adapter.GetTransactionHistory.GetTransactionHistoryController;
import interface_adapter.GetTransactionHistory.GetTransactionHistoryPresenter;
import interface_adapter.GetTransactionHistory.GetTransactionHistoryViewModel;
import interface_adapter.ViewManagerModel;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import use_case.Buy.*;
import use_case.GetTransactionHistory.*;
import use_case.ResetBalance.ResetBalanceOutputData;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;

public class GetTransactionHistoryTest {
    @Test
    public void testGetTransactionHistory() throws APIAccessInterface.TickerNotFoundException {
        APIAccessInterface mockApi = Mockito.mock(APIAccessInterface.class);
        BuyDataAccessInterface userDataAccessObject = Mockito.mock(BuyDataAccessInterface.class);

        Mockito.when(userDataAccessObject.get()).thenReturn(new CommonUser());

        Mockito.when(mockApi.getCurrentPrice("AAPL")).thenReturn(new PricePoint(
                LocalDateTime.now(),
                100.0)
        );

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

        // testBuy and then Check GetTransactionHistory
        GetTransactionHistoryDataAccessInterface getTransactionHistoryDataAccessInterface = Mockito.mock(
                GetTransactionHistoryDataAccessInterface.class);
        GetTransactionHistoryOutputBoundary mockGetTransactionHistoryPresenter = Mockito.mock(
                GetTransactionHistoryPresenter.class);


        Mockito.when(getTransactionHistoryDataAccessInterface.get()).thenReturn(capturedUser);

        GetTransactionHistoryInputBoundary getTransactionHistoryInteractor = new GetTransactionHistoryInteractor(
                getTransactionHistoryDataAccessInterface,
                mockGetTransactionHistoryPresenter
        );

        GetTransactionHistoryController getTransactionHistoryController = new GetTransactionHistoryController(
                getTransactionHistoryInteractor);

//        getTransactionHistoryController.execute();

        getTransactionHistoryInteractor.execute();
        ArgumentCaptor<GetTransactionHistoryOutputData> captor = ArgumentCaptor.forClass(
                GetTransactionHistoryOutputData.class);

//        ViewManagerModel viewManagerModel = new ViewManagerModel();
//        GetTransactionHistoryViewModel getTransactionHistoryViewModel = new GetTransactionHistoryViewModel();
//        DashboardViewModel dashboardViewModel = new DashboardViewModel();
//        GetTransactionHistoryPresenter getTransactionHistoryPresenter = new GetTransactionHistoryPresenter(
//                viewManagerModel,
//                getTransactionHistoryViewModel,
//                dashboardViewModel
//        );

        Mockito.verify(mockGetTransactionHistoryPresenter).prepareSuccessView(captor.capture());
//        Mockito.verify(getTransactionHistoryDataAccessInterface).save();
        GetTransactionHistoryOutputData getTransactionHistoryOutputData = Mockito.mock(
                GetTransactionHistoryOutputData.class);

//        List<String> transaction = new ArrayList<>();
//
//        transaction.add(0, "Apple Inc");
//        transaction.add(1, "BUY");
//        transaction.add(2, String.valueOf(10.0));
//        transaction.add(3, String.valueOf(100.0));
//        transaction.add(4, String.valueOf(LocalDateTime.now()));

//        List<List<String>> transactions = new ArrayList<>();
//        transactions.add(transaction);
//        Mockito.when(getTransactionHistoryOutputData.getUserRecord()).thenReturn(transactions);

        int result = 1;

        List<List<String>> transactionsUserRecord = captor.getValue().getUserRecord();
        for (List<String> transactionInUserRecord: transactionsUserRecord) {
            if (transactionInUserRecord.get(0).equals(mockApi.getCompanyProfile("AAPL").getName())) {
                result = 0;
            }
        }

        assert (result == 0);
    }
}
