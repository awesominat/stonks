package interface_adapters;

import entity.CommonUser;
import entity.CompanyInformation;
import entity.PricePoint;
import entity.User;
import interface_adapter.GetTransactionHistory.*;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import use_case.APIAccessInterface;
import use_case.Buy.*;
import use_case.GetTransactionHistory.*;
import interface_adapter.ViewManagerModel;
import interface_adapter.Dashboard.DashboardViewModel;

import java.beans.PropertyChangeListener;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

public class GetTransactionHistoryInterfaceAdapters {

    @Test
    public void testGetTransactionHistoryIA() throws APIAccessInterface.TickerNotFoundException {
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

        GetTransactionHistoryDataAccessInterface getTransactionHistoryDataAccessInterface = Mockito.mock(
                GetTransactionHistoryDataAccessInterface.class);
//        GetTransactionHistoryOutputBoundary mockGetTransactionHistoryPresenter = Mockito.mock(
//                GetTransactionHistoryPresenter.class);
        Mockito.when(getTransactionHistoryDataAccessInterface.get()).thenReturn(capturedUser);

        ArgumentCaptor<GetTransactionHistoryOutputData> captor = ArgumentCaptor.forClass(
                GetTransactionHistoryOutputData.class);


        ViewManagerModel mockViewManagerModel = Mockito.mock(ViewManagerModel.class);

        GetTransactionHistoryViewModel mockGetTransactionHistoryViewModel = Mockito.mock(
                GetTransactionHistoryViewModel.class);

        DashboardViewModel mockDashboardViewModel = Mockito.mock(DashboardViewModel.class);

        GetTransactionHistoryPresenter mockGetTransactionHistoryPresenter = new GetTransactionHistoryPresenter(
                mockViewManagerModel,
                mockGetTransactionHistoryViewModel,
                mockDashboardViewModel);

        GetTransactionHistoryOutputData outputDataMock = Mockito.mock(GetTransactionHistoryOutputData.class);
        GetTransactionHistoryState stateMock = Mockito.mock(GetTransactionHistoryState.class);

        List<String> transaction = new ArrayList<>();

        transaction.add(0, "Apple Inc");
        transaction.add(1, "BUY");
        transaction.add(2, String.valueOf(10.0));
        transaction.add(3, String.valueOf(100.0));
        transaction.add(4, String.valueOf(LocalDateTime.now()));

        List<List<String>> transactions = new ArrayList<>();
        transactions.add(transaction);
//        Mockito.when(getTransactionHistoryOutputData.getUserRecord()).thenReturn(transactions);
        Mockito.when(mockGetTransactionHistoryViewModel.getState()).thenReturn(stateMock);
        Mockito.when(outputDataMock.getUserRecord()).thenReturn(transactions);
//        Mockito.verify(mockGetTransactionHistoryPresenter).prepareSuccessView(captor.capture());

        mockGetTransactionHistoryPresenter.prepareSuccessView(outputDataMock);
        Mockito.verify(stateMock).setUserRecord(transactions);
        Mockito.verify(mockGetTransactionHistoryViewModel).setState(stateMock);

        Mockito.verify(mockViewManagerModel).setActiveView(mockGetTransactionHistoryViewModel.getViewName());
        Mockito.verify(mockViewManagerModel).firePropertyChanged();

        GetTransactionHistoryInteractor interactorMock = Mockito.mock(GetTransactionHistoryInteractor.class);

        // Create the controller instance with the mock
        GetTransactionHistoryController controller = new GetTransactionHistoryController(interactorMock);

        controller.execute();

        Mockito.verify(interactorMock).execute();

        GetTransactionHistoryState state = new GetTransactionHistoryState();
        List<List<String>> userRecord = Arrays.asList(

                Arrays.asList("AAPL", "BUY", "100"),

                Arrays.asList("GOOGL", "SELL", "50")

        );
        state.setUserRecord(userRecord);

        // When
        HashSet<String> result = state.allStocksInHistory();

        // Then
        HashSet<String> expected = new HashSet<>(Arrays.asList("AAPL", "GOOGL"));
        assert expected.equals(result);

//        GetTransactionHistoryState state = new GetTransactionHistoryState();
//        List<List<String>> userRecord = Arrays.asList(
//                Arrays.asList("AAPL", "BUY", "100"),
//                Arrays.asList("GOOGL", "SELL", "50")
//        );
        state.setUserRecord(userRecord);

        // When
        HashSet<String> resultTypes = state.allTypesInHistory();

        // Then
        HashSet<String> expectedTypes = new HashSet<>(Arrays.asList("BUY", "SELL"));

        for (String x : resultTypes) {
            assert expectedTypes.contains(x);
        }

        GetTransactionHistoryViewModel viewModel = new GetTransactionHistoryViewModel();

        GetTransactionHistoryState newState = new GetTransactionHistoryState();

        PropertyChangeListener listenerMock = Mockito.mock(PropertyChangeListener.class);

        viewModel.addPropertyChangeListener(listenerMock);

        viewModel.firePropertyChanged();

        Mockito.verify(listenerMock, Mockito.times(1)).propertyChange(any());

        viewModel.setState(newState);

        // Then
        // Verify that the state is set correctly
//        assertEquals(newState, viewModel.getState());
//        viewModel.addPropertyChangeListener(listenerMock);
//        Mockito.verify(viewModel.getPropertyChangeSupport()).addPropertyChangeListener(eq("state"), eq(listenerMock)
    }

//        ViewManagerModel viewManagerModel = new ViewManagerModel();
//        GetTransactionHistoryViewModel getTransactionHistoryViewModel = new GetTransactionHistoryViewModel();
//        DashboardViewModel dashboardViewModel = new DashboardViewModel();
//        GetTransactionHistoryPresenter getTransactionHistoryPresenter = new GetTransactionHistoryPresenter(
//                viewManagerModel,
//                getTransactionHistoryViewModel,
//                dashboardViewModel
//        );
//
//        GetTransactionHistoryInputBoundary getTransactionHistoryInteractor = new GetTransactionHistoryInteractor(
//                getTransactionHistoryDataAccessInterface,
//                getTransactionHistoryPresenter
//        );
//
//        GetTransactionHistoryController getTransactionHistoryController = new GetTransactionHistoryController(
//                getTransactionHistoryInteractor
//        );
//
//        getTransactionHistoryController.execute();
//
//        Mockito.when(getTransactionHistoryDataAccessInterface.get()).thenReturn(capturedUser);
////        getTransactionHistoryPresenter.prepareSuccessView();

    @Test
    public void testFilterWithStockName() {
        // Given
        FilterByStockName filter = new FilterByStockName();
        List<List<String>> transactionsToFilter = new ArrayList<>(Arrays.asList(
                new ArrayList<>(Arrays.asList("AAPL", "BUY", "100")),
                new ArrayList<>(Arrays.asList("GOOGL", "SELL", "50")),
                new ArrayList<>(Arrays.asList("AAPL", "SELL", "20"))
        ));

        // When
        filter.filter(transactionsToFilter, "AAPL");

        // Then
        List<List<String>> expectedFilteredTransactions = new ArrayList<>(Arrays.asList(
                new ArrayList<>(Arrays.asList("AAPL", "BUY", "100")),
                new ArrayList<>(Arrays.asList("AAPL", "SELL", "20"))
        ));
        assertEquals(expectedFilteredTransactions, transactionsToFilter);
    }

    @Test
    public void testFilterWithNoStockNameFilter() {
        // Given
        FilterByStockName filter = new FilterByStockName();
        List<List<String>> transactionsToFilter = new ArrayList<>(Arrays.asList(
                new ArrayList<>(Arrays.asList("AAPL", "BUY", "100")),
                new ArrayList<>(Arrays.asList("GOOGL", "SELL", "50")),
                new ArrayList<>(Arrays.asList("AAPL", "SELL", "20"))
        ));

        // When
        filter.filter(transactionsToFilter, "No filter");

        // Then
        // Since the filter condition is not applied, the list should remain unchanged
        List<List<String>> expectedTransactions = new ArrayList<>(Arrays.asList(
                new ArrayList<>(Arrays.asList("AAPL", "BUY", "100")),
                new ArrayList<>(Arrays.asList("GOOGL", "SELL", "50")),
                new ArrayList<>(Arrays.asList("AAPL", "SELL", "20"))
        ));
        assertEquals(expectedTransactions, transactionsToFilter);
    }

    @Test
    public void testFilterWithTransactionType() {
        // Given
        FilterByTransactionType filter = new FilterByTransactionType();
        List<List<String>> transactionsToFilter = new ArrayList<>(Arrays.asList(
                new ArrayList<>(Arrays.asList("AAPL", "BUY", "100")),
                new ArrayList<>(Arrays.asList("GOOGL", "SELL", "50")),
                new ArrayList<>(Arrays.asList("AAPL", "SELL", "20"))
        ));

        // When
        filter.filter(transactionsToFilter, "SELL");

        // Then
        List<List<String>> expectedFilteredTransactions = new ArrayList<>(Arrays.asList(
                new ArrayList<>(Arrays.asList("GOOGL", "SELL", "50")),
                new ArrayList<>(Arrays.asList("AAPL", "SELL", "20"))
        ));
        assertEquals(expectedFilteredTransactions, transactionsToFilter);
    }

    @Test
    public void testFilterWithNoTransactionTypeFilter() {
        // Given
        FilterByTransactionType filter = new FilterByTransactionType();
        List<List<String>> transactionsToFilter = new ArrayList<>(Arrays.asList(
                new ArrayList<>(Arrays.asList("AAPL", "BUY", "100")),
                new ArrayList<>(Arrays.asList("GOOGL", "SELL", "50")),
                new ArrayList<>(Arrays.asList("AAPL", "SELL", "20"))
        ));

        // When
        filter.filter(transactionsToFilter, "No filter");

        // Then
        // Since the filter condition is not applied, the list should remain unchanged
        List<List<String>> expectedTransactions = new ArrayList<>(Arrays.asList(
                new ArrayList<>(Arrays.asList("AAPL", "BUY", "100")),
                new ArrayList<>(Arrays.asList("GOOGL", "SELL", "50")),
                new ArrayList<>(Arrays.asList("AAPL", "SELL", "20"))
        ));
        assertEquals(expectedTransactions, transactionsToFilter);


    }
    @Test
    public void testFilterRemovesCorrectTransactions() {
        // Given
        FilterByTransactionType filter = new FilterByTransactionType();
        List<List<String>> transactionsToFilter = new ArrayList<>(Arrays.asList(
                new ArrayList<>(Arrays.asList("AAPL", "BUY", "100")),
                new ArrayList<>(Arrays.asList("GOOGL", "SELL", "50")),
                new ArrayList<>(Arrays.asList("AAPL", "SELL", "20")),
                new ArrayList<>(Arrays.asList("MSFT", "BUY", "30"))
        ));

        // When
        filter.filter(transactionsToFilter, "SELL");

        // Then
        List<List<String>> expectedFilteredTransactions = new ArrayList<>(Arrays.asList(
                new ArrayList<>(Arrays.asList("GOOGL", "SELL", "50")),
                new ArrayList<>(Arrays.asList("AAPL", "SELL", "20"))
        ));
        assertEquals(expectedFilteredTransactions, transactionsToFilter);
    }
    @Test
    public void testApplyFilters() {
        // Given
        FilterCollection filterCollection = new FilterCollection();
        Filter filter1Mock = Mockito.mock(Filter.class);
        Filter filter2Mock = Mockito.mock(Filter.class);

        // Add mock filters to the collection
        filterCollection.add(filter1Mock);
        filterCollection.add(filter2Mock);

        List<List<String>> transactionsToFilter = new ArrayList<>(Arrays.asList(
                Arrays.asList("AAPL", "BUY", "100"),
                Arrays.asList("GOOGL", "SELL", "50"),
                Arrays.asList("AAPL", "SELL", "20")
        ));

        // When
        filterCollection.applyFilters(transactionsToFilter, new String[]{"AAPL", "SELL"});

        // Verify that filter methods are called on the mock filters
        Mockito.verify(filter1Mock).filter(transactionsToFilter, "AAPL");

        Mockito.verify(filter2Mock).filter(transactionsToFilter, "SELL");
    }

    @Test
    public void testApplyFiltersIncorrectNumberOfArguments() {
        // Given
        FilterCollection filterCollection = new FilterCollection();

        Filter filterMock = Mockito.mock(Filter.class);

        filterCollection.add(filterMock);

        List<List<String>> transactionsToFilter = new ArrayList<>(Arrays.asList(
                Arrays.asList("AAPL", "BUY", "100"),
                Arrays.asList("GOOGL", "SELL", "50"),
                Arrays.asList("AAPL", "SELL", "20")
        ));

        // When, Then
        // Verify that an exception is thrown for an incorrect number of filter arguments
        assertThrows(RuntimeException.class,
                () -> filterCollection.applyFilters(transactionsToFilter, new String[]{
                        "AAPL",
                        "SELL",
                        "ExtraArgument"}
                )
        );

        // Verify that filter methods are not called on the mock filter due to the exception
        Mockito.verify(filterMock, Mockito.never()).filter(any(), any());
    }
}


