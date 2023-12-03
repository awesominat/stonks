package interface_adapter;

import interface_adapter.Dashboard.DashboardPresenter;
import interface_adapter.Dashboard.DashboardState;
import interface_adapter.Dashboard.DashboardViewModel;
import interface_adapter.Sell.SellViewModel;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import use_case.Dashboard.DashboardOutputData;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DashboardInterfaceAdapterTest {


    @Test
    void testPrepareSuccessViewRefreshPressed() {
        // Arrange
        ViewManagerModel viewManagerModelMock = Mockito.mock(ViewManagerModel.class);
        DashboardViewModel dashboardViewModelMock = Mockito.mock(DashboardViewModel.class);
        SellViewModel sellViewModelMock = Mockito.mock(SellViewModel.class);
        DashboardPresenter dashboardPresenter = new DashboardPresenter(viewManagerModelMock, dashboardViewModelMock, sellViewModelMock);

        HashMap<String, List<Double>> stockPriceInfo = getMockStockPriceInfo();
        DashboardOutputData response = new DashboardOutputData(stockPriceInfo);

        // Mock state and interaction
        DashboardState stateMock = Mockito.mock(DashboardState.class);
        Mockito.when(dashboardViewModelMock.getState()).thenReturn(stateMock);

        // Act
        dashboardPresenter.prepareSuccessView(response);

        // Assert
        Mockito.verify(stateMock).setStocksPriceInformationTable(stockPriceInfo);
        Mockito.verify(dashboardViewModelMock).setState(stateMock);
        Mockito.verify(dashboardViewModelMock).firePropertyChanged();
        Mockito.verify(sellViewModelMock).firePropertyChanged();
    }

    @Test
    void testPrepareSuccessViewNotRefreshPressed() {
        // Arrange
        ViewManagerModel viewManagerModelMock = Mockito.mock(ViewManagerModel.class);
        DashboardViewModel dashboardViewModelMock = Mockito.mock(DashboardViewModel.class);
        SellViewModel sellViewModelMock = Mockito.mock(SellViewModel.class);
        DashboardPresenter dashboardPresenter = new DashboardPresenter(viewManagerModelMock, dashboardViewModelMock, sellViewModelMock);

        HashMap<String, Double> userStats = getMockUserStats();
        HashMap<String, Double> ownedStocksInfo = getMockOwnedStocksInfo();
        DashboardOutputData response = new DashboardOutputData(userStats, ownedStocksInfo);

        // Mock state and interaction
        DashboardState stateMock = Mockito.mock(DashboardState.class);
        Mockito.when(dashboardViewModelMock.getState()).thenReturn(stateMock);

        // Act
        dashboardPresenter.prepareSuccessView(response);

        // Assert
        Mockito.verify(stateMock).setUserStats(userStats);

        ArgumentCaptor<HashMap<String, List<Double>>> captor = ArgumentCaptor.forClass(HashMap.class);
        Mockito.verify(stateMock).setStocksPriceInformationTable(captor.capture());

        HashMap<String, List<Double>> capturedStockInfoTable = captor.getValue();

        // Adjusted assertion to consider the default values for non-owned stocks
        assertEquals(getExpectedStockPriceInfo(ownedStocksInfo), capturedStockInfoTable);

        Mockito.verify(dashboardViewModelMock).setState(stateMock);
        Mockito.verifyNoMoreInteractions(sellViewModelMock);
    }

    // Helper method to create a mock stock price information table considering owned stocks
    private HashMap<String, List<Double>> getExpectedStockPriceInfo(HashMap<String, Double> ownedStocks) {
        HashMap<String, List<Double>> expectedStockPriceInfo = new HashMap<>();
        for (String ticker : ownedStocks.keySet()) {
            expectedStockPriceInfo.put(ticker, Arrays.asList(-1.0, -1.0, -1.0));
        }
        return expectedStockPriceInfo;
    }
    // Helper method to create a mock stock price information table
    private HashMap<String, List<Double>> getMockStockPriceInfo() {
        HashMap<String, List<Double>> stockPriceInfo = new HashMap<>();
        stockPriceInfo.put("AAPL", Arrays.asList(150.0, 155.0, 145.0));
        stockPriceInfo.put("GOOGL", Arrays.asList(2000.0, 2050.0, 1980.0));
        return stockPriceInfo;
    }

    private HashMap<String, Double> getMockUserStats() {
        HashMap<String, Double> userStats = new HashMap<>();
        userStats.put("TotalBalance", 5000.0);
        userStats.put("AvailableBalance", 2500.0);
        return userStats;
    }

    // Helper method to create a mock owned stocks information table
    private HashMap<String, Double> getMockOwnedStocksInfo() {
        HashMap<String, Double> ownedStocksInfo = new HashMap<>();
        ownedStocksInfo.put("AAPL", 10.0);
        ownedStocksInfo.put("GOOGL", 5.0);
        return ownedStocksInfo;
    }

    @Test
    void testGettersAndSetters() {
        // Arrange
        HashMap<String, Double> userStats = new HashMap<>();
        userStats.put("TotalBalance", 5000.0);

        HashMap<String, Double> ownedStocksTable = new HashMap<>();
        ownedStocksTable.put("AAPL", 10.0);

        HashMap<String, List<Double>> stocksPriceInfo = new HashMap<>();
        stocksPriceInfo.put("AAPL", Arrays.asList(150.0, 155.0, 145.0));

        DashboardState dashboardState = new DashboardState();

        // Act - set values using setters
        dashboardState.setUserStats(userStats);
        dashboardState.setOwnedStocksTable(ownedStocksTable);
        dashboardState.setStocksPriceInformationTable(stocksPriceInfo);
        dashboardState.setRefreshPressed(true);
        dashboardState.setResetPressed(true);

        // Assert - use getters to retrieve values
        assertEquals(userStats, dashboardState.getUserStats());
        assertEquals(ownedStocksTable, dashboardState.getOwnedStocksTable());
        assertEquals(stocksPriceInfo, dashboardState.getStocksPriceInformationTable());
        assertEquals(true, dashboardState.getRefreshPressed());
        assertEquals(true, dashboardState.getResetPressed());
    }

    @Test
    void testDefaultConstructor() {
        // Act
        DashboardState dashboardState = new DashboardState();

        // Assert - verify default values
        assertEquals(new HashMap<>(), dashboardState.getUserStats());
        assertEquals(new HashMap<>(), dashboardState.getOwnedStocksTable());
        assertEquals(new HashMap<>(), dashboardState.getStocksPriceInformationTable());
        assertEquals(false, dashboardState.getRefreshPressed());
        assertEquals(false, dashboardState.getResetPressed());
    }
}
