package view;

import data_access.InMemoryUserDataAccessObject;
import entity.*;
import interface_adapter.Dashboard.DashboardViewModel;
import interface_adapter.Sell.SellController;
import interface_adapter.Sell.SellState;
import interface_adapter.Sell.SellViewModel;
import interface_adapter.ViewManagerModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SellViewTest {

    private SellController sellController;
    private SellViewModel sellViewModel;
    private DashboardViewModel dashboardViewModel;
    private ViewManagerModel viewManagerModel;
    private SellView sellView;
    private JFrame jf;

    @BeforeEach
    public void setUp() {
        System.setProperty("java.awt.headless", "true");
        InMemoryUserDataAccessObject inMemoryUserDataAccessObject = new InMemoryUserDataAccessObject(new CommonUserFactory());
        User user = inMemoryUserDataAccessObject.get();
        user.addToPortfolio("AAPL", 10.0);

        List<Transaction> transactionList = new ArrayList<>();
        transactionList.add(new BuyTransaction(10.0, new PricePoint(LocalDateTime.now(), 100.0)));

        user.getHistory().put("AAPL", new TransactionHistory(
                new Stock(100.0, "Apple Inc", "AAPL"),
                transactionList
        ));

        List<String> ownedStocks = new ArrayList<>();
        ownedStocks.add("AAPL");
        List<Double> ownedAmounts = new ArrayList<>();
        ownedAmounts.add(10.0);
        List<Double> sellAmounts = new ArrayList<>();
        sellAmounts.add(100.0);

        sellController = Mockito.mock(SellController.class);
        SellState sellState = new SellState(
                "AAPL", "5", null, ownedStocks,
                null, ownedAmounts, 10000.0, sellAmounts);

        sellViewModel = new SellViewModel();
        sellViewModel.setState(sellState);
        DashboardViewModel dashboardViewModel = new DashboardViewModel();
        viewManagerModel = Mockito.mock(ViewManagerModel.class);

        sellView = new SellView(sellViewModel, sellController, viewManagerModel, dashboardViewModel);
    }

    private JButton findButton(Component component, String query) {
        if (component instanceof JButton) {
            JButton button = (JButton) component;
            if (query.equals(button.getText())) {
                return button;
            }
        }
        if (component instanceof Container) {
            for (Component child : ((Container) component).getComponents()) {
                JButton button = findButton(child, query);
                if (button != null) {
                    return button;
                }
            }
        }
        return null;
    }

    @Test
    public void testSellViewBehavior() {
        JButton sellButton = findButton(sellView, "Sell Stocks");
        assertNotNull(sellButton);
        sellButton.doClick();

        Mockito.verify(sellController, Mockito.times(1)).execute();
    }

    @Test
    public void testStockSelectionUpdatesSellState() {
        JComboBox<String> stockInputField = (JComboBox<String>) findComponentByName(sellView, "stockInputField");
        assertNotNull(stockInputField);

        stockInputField.setSelectedItem("AAPL");
        assertEquals("AAPL", sellViewModel.getState().getStockSelected());
    }

    @Test
    public void testSellAmountInputUpdatesSellState() {
        JTextField amountInputField = (JTextField) findComponentByName(sellView, "amountInputField");
        assertNotNull(amountInputField);

        amountInputField.setText("5");
        assertEquals("5", sellViewModel.getState().getAmount());
    }

    @Test
    public void testPropertyChangeUpdatesView() {
        sellViewModel.getState().setBalance(5000.0);
        sellViewModel.firePropertyChanged();

        JLabel currentBalanceLabel = (JLabel) findComponentByName(sellView, "currentBalance");
        assertNotNull(currentBalanceLabel);

        assertEquals("Current Balance: $5000.00", currentBalanceLabel.getText());
    }

    @Test
    public void testBackButtonNavigatesToDashboard() {
        JButton backButton = findButton(sellView, "Back");
        assertNotNull(backButton);

        backButton.doClick();
        Mockito.verify(viewManagerModel, Mockito.times(1)).setActiveView("dashboard");
    }

    private Component findComponentByName(Component component, String name) {
        if (name.equals(component.getName())) {
            return component;
        }
        if (component instanceof Container) {
            for (Component child : ((Container) component).getComponents()) {
                Component found = findComponentByName(child, name);
                if (found != null) {
                    return found;
                }
            }
        }
        return null;
    }
}
