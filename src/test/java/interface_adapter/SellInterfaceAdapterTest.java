package interface_adapter;

import interface_adapter.Dashboard.DashboardState;
import interface_adapter.Dashboard.DashboardViewModel;
import interface_adapter.Sell.SellController;
import interface_adapter.Sell.SellPresenter;
import interface_adapter.Sell.SellState;
import interface_adapter.Sell.SellViewModel;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import use_case.Sell.SellInputBoundary;
import use_case.Sell.SellInputData;
import use_case.Sell.SellOutputData;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

public class SellInterfaceAdapterTest {

    @Test
    void executeWithValidInput() {
        // Arrange
        SellInputBoundary sellInteractorMock = Mockito.mock(SellInputBoundary.class);
        SellController sellController = new SellController(sellInteractorMock);

        String amount = "100.0";
        String ticker = "AAPL";

        // Act
        sellController.execute(amount, ticker);

        // Assert
        ArgumentCaptor<SellInputData> argumentCaptor = ArgumentCaptor.forClass(SellInputData.class);
        Mockito.verify(sellInteractorMock).execute(argumentCaptor.capture());

        SellInputData capturedSellInputData = argumentCaptor.getValue();
        // Perform assertions on the capturedSellInputData as needed

        // Example assertion, you might need to adjust based on your SellInputData implementation
        assertEquals(Double.parseDouble(amount), capturedSellInputData.getAmount());
        assertEquals(ticker, capturedSellInputData.getTicker());
    }

    @Test
    void executeWithInvalidInput() {
        // Arrange
        SellInputBoundary sellInteractorMock = Mockito.mock(SellInputBoundary.class);
        SellController sellController = new SellController(sellInteractorMock);

        String invalidAmount = "invalid";
        String ticker = "AAPL";

        // Act
        sellController.execute(invalidAmount, ticker);

        // Assert
        ArgumentCaptor<SellInputData> argumentCaptor = ArgumentCaptor.forClass(SellInputData.class);
        Mockito.verify(sellInteractorMock).execute(argumentCaptor.capture());

        SellInputData capturedSellInputData = argumentCaptor.getValue();
        // Perform assertions on the capturedSellInputData as needed

        // Example assertion, you might need to adjust based on your SellInputData implementation
        assertEquals(-1.0, capturedSellInputData.getAmount());
        assertEquals(ticker, capturedSellInputData.getTicker());
    }

    @Test
    void executeWithoutInput() {
        // Arrange
        SellInputBoundary sellInteractorMock = Mockito.mock(SellInputBoundary.class);
        SellController sellController = new SellController(sellInteractorMock);

        // Act
        sellController.execute();

        // Assert
        ArgumentCaptor<SellInputData> argumentCaptor = ArgumentCaptor.forClass(SellInputData.class);
        Mockito.verify(sellInteractorMock).execute(argumentCaptor.capture());

        SellInputData capturedSellInputData = argumentCaptor.getValue();
        // Perform assertions on the capturedSellInputData as needed

        // Example assertion, you might need to adjust based on your SellInputData implementation
        assertNull(capturedSellInputData.getAmount());
        assertNull(capturedSellInputData.getTicker());
    }

    @Test
    void testPrepareSuccessViewExecuteTypeSell() {
        // Arrange
        ViewManagerModel viewManagerModelMock = Mockito.mock(ViewManagerModel.class);
        SellViewModel sellViewModelMock = Mockito.mock(SellViewModel.class);
        DashboardViewModel dashboardViewModelMock = Mockito.mock(DashboardViewModel.class);

        // Mock the behavior of sellViewModel.getState() to return a non-null SellState
        SellState sellStateMock = Mockito.mock(SellState.class);
        Mockito.when(sellViewModelMock.getState()).thenReturn(sellStateMock);

        SellPresenter sellPresenter = new SellPresenter(viewManagerModelMock, sellViewModelMock, dashboardViewModelMock);

        SellOutputData response = new SellOutputData(100.0, "AAPL");

        // Act
        sellPresenter.prepareSuccessView(response);

        // Assert
        ArgumentCaptor<SellState> argumentCaptor = ArgumentCaptor.forClass(SellState.class);
        Mockito.verify(sellViewModelMock).setState(argumentCaptor.capture());

        SellState capturedSellState = argumentCaptor.getValue();

        // Perform assertions on the capturedSellState as needed
        assert capturedSellState.getBalance() >= sellViewModelMock.getState().getBalance();
    }

    @Test
    void testPrepareSuccessViewNotExecuteTypeSell() {
        ViewManagerModel viewManagerModelMock = Mockito.mock(ViewManagerModel.class);
        SellViewModel sellViewModel = new SellViewModel();
        SellState sellState = new SellState();
        sellViewModel.setState(sellState);

        DashboardViewModel dashboardViewModel = new DashboardViewModel();
        DashboardState dashboardState1 = new DashboardState();
        dashboardViewModel.setState(dashboardState1);

        SellPresenter sellPresenter = new SellPresenter(viewManagerModelMock, sellViewModel, dashboardViewModel);

        HashMap<String, List<Double>> stocksPriceInformationTable = new HashMap<>();
        stocksPriceInformationTable.put(
                "AAPL",
                Arrays.asList(100.0, -2.0, -1.0)
        );
        dashboardState1.setStocksPriceInformationTable(stocksPriceInformationTable);

        List<String> ownedStocks = new ArrayList<>();
        ownedStocks.add("AAPL");
        List<Double> ownedAmounts = new ArrayList<>();
        ownedAmounts.add(2.0);
        Double balance = 10000.0;

        SellOutputData response = new SellOutputData(ownedStocks, ownedAmounts, balance);

        sellPresenter.prepareSuccessView(response);

        assertEquals(sellState.getOwnedAmounts(), ownedAmounts);
        assertEquals(sellState.getOwnedStocks(), ownedStocks);
        assertEquals(sellState.getBalance(), balance);
    }

//
    @Test
    void testPrepareFailView() {
        // Arrange
        ViewManagerModel viewManagerModelMock = Mockito.mock(ViewManagerModel.class);
        SellViewModel sellViewModelMock = Mockito.mock(SellViewModel.class);
        DashboardViewModel dashboardViewModelMock = Mockito.mock(DashboardViewModel.class);

        // Mock the behavior of sellViewModel.getState() to return a non-null SellState
        SellState sellStateMock = Mockito.mock(SellState.class);
        Mockito.when(sellViewModelMock.getState()).thenReturn(sellStateMock);

        SellPresenter sellPresenter = new SellPresenter(viewManagerModelMock, sellViewModelMock, dashboardViewModelMock);

        String error = "Invalid amount";

        // Act
        sellPresenter.prepareFailView(error);

        // Assert
        Mockito.verify(sellStateMock).setAmountError(error);
    }

    @Test
    void testSetAmountError() {
        // Arrange
        SellState sellState = new SellState(
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null
        );

        // Act
        sellState.setAmountError("Invalid amount");

        // Assert
        assertSame("Invalid amount", sellState.getAmountError());
    }

    @Test
    void testSetBalance() {
        // Arrange
        SellState sellState = new SellState(
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null
        );

        // Act
        sellState.setBalance(500.0);

        // Assert
        assertEquals(500.0, sellState.getBalance(), 0.001);
    }

    @Test
    void testSetSellAmounts() {
        // Arrange
        SellState sellState = new SellState(
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null
        );
        // Act
        sellState.setSellAmounts(List.of(100.0, 200.0, 300.0));

        // Assert
        assertEquals(List.of(100.0, 200.0, 300.0), sellState.getSellAmounts());
    }

    @Test
    void testSetAmount() {
        // Arrange
        SellState sellState = new SellState(
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null
        );
        // Act
        sellState.setAmount("50.0");

        // Assert
        assertEquals("50.0", sellState.getAmount());
    }

    @Test
    void testSetStockSelected() {
        // Arrange
        SellState sellState = new SellState(
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null
        );
        // Act
        sellState.setStockSelected("AAPL");

        // Assert
        assertEquals("AAPL", sellState.getStockSelected());
    }

    @Test
    void testSetOwnedStocks() {
        // Arrange
        SellState sellState = new SellState(
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null
        );
        // Act
        sellState.setOwnedStocks(List.of("AAPL", "GOOGL"));

        // Assert
        assertEquals(List.of("AAPL", "GOOGL"), sellState.getOwnedStocks());
    }

    @Test
    void testSetSellSuccess() {
        // Arrange
        SellState sellState = new SellState(
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null
        );
        // Act
        sellState.setSellSuccess("Sold successfully");

        // Assert
        assertEquals("Sold successfully", sellState.getSellSuccess());
    }

    @Test
    void testSetOwnedAmounts() {
        // Arrange
        SellState sellState = new SellState(
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null
        );
        // Act
        sellState.setOwnedAmounts(List.of(100.0, 200.0));

        // Assert
        assertEquals(List.of(100.0, 200.0), sellState.getOwnedAmounts());
    }

    @Test
    void testSetState() {
        // Arrange
        SellViewModel sellViewModel = new SellViewModel();
        SellState sellStateMock = Mockito.mock(SellState.class);

        // Act
        sellViewModel.setState(sellStateMock);

        // Assert
        assertEquals(sellStateMock, sellViewModel.getState());
    }

    @Test
    void testFirePropertyChanged() {
        // Arrange
        SellViewModel sellViewModel = new SellViewModel();
        PropertyChangeListener listenerMock = Mockito.mock(PropertyChangeListener.class);

        // Act
        sellViewModel.addPropertyChangeListener(listenerMock);
        sellViewModel.firePropertyChanged();

        // Assert
        Mockito.verify(listenerMock, Mockito.times(1)).propertyChange(any());
    }

    @Test
    void testFirePropertyChangedWithCapture() {
        // Arrange
        SellViewModel sellViewModel = new SellViewModel();
        PropertyChangeListener listenerMock = Mockito.mock(PropertyChangeListener.class);
        sellViewModel.addPropertyChangeListener(listenerMock);

        // Act
        sellViewModel.firePropertyChanged();

        // Assert
        ArgumentCaptor<PropertyChangeEvent> eventCaptor = ArgumentCaptor.forClass(PropertyChangeEvent.class);
        Mockito.verify(listenerMock, Mockito.times(1)).propertyChange(eventCaptor.capture());

        PropertyChangeEvent capturedEvent = eventCaptor.getValue();
        assertEquals("state", capturedEvent.getPropertyName());
        assertEquals(null, capturedEvent.getOldValue());
        assertEquals(sellViewModel.getState(), capturedEvent.getNewValue());
    }
}

