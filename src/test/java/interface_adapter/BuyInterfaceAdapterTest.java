package interface_adapter;

import interface_adapter.Buy.BuyController;
import interface_adapter.Buy.BuyPresenter;
import interface_adapter.Buy.BuyState;
import interface_adapter.Buy.BuyViewModel;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import use_case.Buy.BuyInputBoundary;
import use_case.Buy.BuyInputData;
import use_case.Buy.BuyOutputData;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BuyInterfaceAdapterTest {

    @Test
    void testExecuteWithValidAmountAndTicker() {
        // Arrange
        BuyInputBoundary buyInteractorMock = Mockito.mock(BuyInputBoundary.class);
        BuyController buyController = new BuyController(buyInteractorMock);
        String amount = "100.0";
        String ticker = "AAPL";

        // Act
        buyController.execute(amount, ticker);

        // Assert
        ArgumentCaptor<BuyInputData> argumentCaptor = ArgumentCaptor.forClass(BuyInputData.class);
        Mockito.verify(buyInteractorMock).execute(argumentCaptor.capture());

        BuyInputData capturedBuyInputData = argumentCaptor.getValue();
        assertEquals(Double.parseDouble(amount), capturedBuyInputData.getAmount());
        assertEquals(ticker, capturedBuyInputData.getTicker());
    }

    @Test
    void testExecuteWithInvalidAmount() {
        // Arrange
        BuyInputBoundary buyInteractorMock = Mockito.mock(BuyInputBoundary.class);
        BuyController buyController = new BuyController(buyInteractorMock);
        String invalidAmount = "invalid";
        String ticker = "AAPL";

        // Act
        buyController.execute(invalidAmount, ticker);

        // Assert
        ArgumentCaptor<BuyInputData> argumentCaptor = ArgumentCaptor.forClass(BuyInputData.class);
        Mockito.verify(buyInteractorMock).execute(argumentCaptor.capture());

        BuyInputData capturedBuyInputData = argumentCaptor.getValue();
        assertEquals(-1.0, capturedBuyInputData.getAmount());
        assertEquals(ticker, capturedBuyInputData.getTicker());
    }

    @Test
    void testExecuteWithTickerOnly() {
        // Arrange
        BuyInputBoundary buyInteractorMock = Mockito.mock(BuyInputBoundary.class);
        BuyController buyController = new BuyController(buyInteractorMock);
        String ticker = "AAPL";

        // Act
        buyController.execute(ticker);

        // Assert
        ArgumentCaptor<BuyInputData> argumentCaptor = ArgumentCaptor.forClass(BuyInputData.class);
        Mockito.verify(buyInteractorMock).execute(argumentCaptor.capture());

        BuyInputData capturedBuyInputData = argumentCaptor.getValue();
        assertEquals(null, capturedBuyInputData.getAmount());
        assertEquals(ticker, capturedBuyInputData.getTicker());
    }

    @Test
    void testExecuteWithoutArguments() {
        // Arrange
        BuyInputBoundary buyInteractorMock = Mockito.mock(BuyInputBoundary.class);
        BuyController buyController = new BuyController(buyInteractorMock);

        // Act
        buyController.execute();

        // Assert
        ArgumentCaptor<BuyInputData> argumentCaptor = ArgumentCaptor.forClass(BuyInputData.class);
        Mockito.verify(buyInteractorMock).execute(argumentCaptor.capture());

        BuyInputData capturedBuyInputData = argumentCaptor.getValue();
        assertEquals(null, capturedBuyInputData.getAmount());
        assertEquals(null, capturedBuyInputData.getTicker());
    }

    @Test
    void testGetterAndSetterMethods() {
        // Arrange
        BuyState buyState = new BuyState();

        // Mock data for testing
        String amount = "100.0";
        String amountError = "Invalid amount";
        String ticker = "AAPL";
        Map<String, String> stockInfo = new HashMap<>();
        stockInfo.put("Price", "150.0");
        Boolean renderNewInfo = true;
        Boolean boughtStock = true;
        Double currentlyHeld = 50.0;
        Double curBalance = 1000.0;

        // Act
        buyState.setAmount(amount);
        buyState.setAmountError(amountError);
        buyState.setTicker(ticker);
        buyState.setStockInfo(stockInfo);
        buyState.setRenderNewInfo(renderNewInfo);
        buyState.setBoughtStock(boughtStock);
        buyState.setCurrentlyHeld(currentlyHeld);
        buyState.setCurBalance(curBalance);

        // Assert
        assertEquals(amount, buyState.getAmount());
        assertEquals(amountError, buyState.getAmountError());
        assertEquals(ticker, buyState.getTicker());
        assertEquals(stockInfo, buyState.getStockInfo());
        assertEquals(renderNewInfo, buyState.getRenderNewInfo());
        assertEquals(boughtStock, buyState.getBoughtStock());
        assertEquals(currentlyHeld, buyState.getCurrentlyHeld());
        assertEquals(curBalance, buyState.getCurBalance());
    }

    @Test
    void testSetState() {
        // Arrange
        BuyViewModel buyViewModel = new BuyViewModel();
        BuyState buyStateMock = Mockito.mock(BuyState.class);

        // Act
        buyViewModel.setState(buyStateMock);

        // Assert
        assertEquals(buyStateMock, buyViewModel.getState());
    }

    @Test
    void testFirePropertyChanged() {
        // Arrange
        BuyViewModel buyViewModel = new BuyViewModel();
        PropertyChangeListener listenerMock = Mockito.mock(PropertyChangeListener.class);

        // Act
        buyViewModel.addPropertyChangeListener(listenerMock);
        buyViewModel.firePropertyChanged();

        // Assert
        Mockito.verify(listenerMock, Mockito.times(1)).propertyChange(Mockito.any());
    }

    @Test
    void testFirePropertyChangedWithCapture() {
        // Arrange
        BuyViewModel buyViewModel = new BuyViewModel();
        PropertyChangeListener listenerMock = Mockito.mock(PropertyChangeListener.class);
        buyViewModel.addPropertyChangeListener(listenerMock);

        // Act
        buyViewModel.firePropertyChanged();

        // Assert
        ArgumentCaptor<PropertyChangeEvent> eventCaptor = ArgumentCaptor.forClass(PropertyChangeEvent.class);
        Mockito.verify(listenerMock, Mockito.times(1)).propertyChange(eventCaptor.capture());

        PropertyChangeEvent capturedEvent = eventCaptor.getValue();
        assertEquals("state", capturedEvent.getPropertyName());
        assertEquals(null, capturedEvent.getOldValue());
        assertEquals(buyViewModel.getState(), capturedEvent.getNewValue());
    }

    @Test
    void testPrepareSuccessViewWithBuyOutputData() {
        // Arrange
        ViewManagerModel viewManagerModelMock = Mockito.mock(ViewManagerModel.class);
        BuyViewModel buyViewModelMock = Mockito.mock(BuyViewModel.class);
        BuyPresenter buyPresenter = new BuyPresenter(viewManagerModelMock, buyViewModelMock);
        BuyOutputData response = new BuyOutputData(1000.0, true);

        // Mock state and interaction
        BuyState stateMock = Mockito.mock(BuyState.class);
        Mockito.when(buyViewModelMock.getState()).thenReturn(stateMock);

        // Act
        buyPresenter.prepareSuccessView(response);

        // Assert
        Mockito.verify(stateMock).setCurBalance(response.getNewBalance());
        if (response.getExecutedPurchase()) {
            Mockito.verify(stateMock).setRenderNewInfo(true);
            Mockito.verify(stateMock).setBoughtStock(true);
            Mockito.verify(buyViewModelMock).firePropertyChanged();
        } else {
            Mockito.verifyNoMoreInteractions(stateMock);
            Mockito.verifyNoMoreInteractions(buyViewModelMock);
        }
    }

    @Test
    void testPrepareFailView() {
        // Arrange
        ViewManagerModel viewManagerModelMock = Mockito.mock(ViewManagerModel.class);
        BuyViewModel buyViewModelMock = Mockito.mock(BuyViewModel.class);
        BuyPresenter buyPresenter = new BuyPresenter(viewManagerModelMock, buyViewModelMock);
        String error = "Invalid amount";

        // Mock state and interaction
        BuyState stateMock = Mockito.mock(BuyState.class);
        Mockito.when(buyViewModelMock.getState()).thenReturn(stateMock);

        // Act
        buyPresenter.prepareFailView(error);

        // Assert
        Mockito.verify(stateMock).setAmountError(error);
        Mockito.verify(buyViewModelMock).firePropertyChanged();
    }

}
