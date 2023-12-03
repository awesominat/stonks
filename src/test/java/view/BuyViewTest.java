package view;

import driver.TableModel;
import interface_adapter.Buy.BuyController;
import interface_adapter.Buy.BuyState;
import interface_adapter.Buy.BuyViewModel;
import interface_adapter.Dashboard.DashboardViewModel;
import interface_adapter.ViewManagerModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

public class BuyViewTest {

    private BuyController buyController;
    private BuyViewModel buyViewModel;
    private DashboardViewModel dashboardViewModel;
    private ViewManagerModel viewManagerModel;
    private BuyView buyView;
    private JFrame jf;

    @BeforeEach
    public void setUp() {
        buyViewModel = new BuyViewModel();

        DashboardViewModel dashboardViewModel = new DashboardViewModel();
        buyController = Mockito.mock(BuyController.class);
        viewManagerModel = Mockito.mock(ViewManagerModel.class);

        buyView = new BuyView(buyController, buyViewModel, viewManagerModel, dashboardViewModel);

        jf = new JFrame();
        jf.setContentPane(buyView);
        jf.pack();
        jf.setVisible(true);
    }

    @Test
    public void testSearchStockUpdatesView() {
        Mockito.doAnswer(invocation -> {
            HashMap<String, String> companyNews = new HashMap<>();
            companyNews.put("country", "US");
            companyNews.put("weburl", "https://www.apple.com/");
            companyNews.put("currently held", "0.0");

            buyViewModel.getState().setRenderNewInfo(true);
            buyViewModel.getState().setStockInfo(companyNews);
            buyViewModel.firePropertyChanged();
            return null;
        }).when(buyController).execute(any(String.class));

        JTextField tickerInputField = buyView.tickerInputField;
        tickerInputField.setText("AAPL");
        buyViewModel.getState().setTicker("AAPL");

        JButton searchButton = buyView.search;
        searchButton.doClick();

        Map<String, String> companyNews = buyViewModel.getState().getStockInfo();
        assertEquals(companyNews.get("country"), "US");
        assertEquals(companyNews.get("weburl"), "https://www.apple.com/");
        assertEquals(companyNews.get("currently held"), "0.0");

        TableModel tableModel = (TableModel) buyView.table.getModel();

        assertEquals("US", tableModel.getValueAt(0, 1));
        assertEquals("https://www.apple.com/", tableModel.getValueAt(1, 1));
        assertEquals("0.0", tableModel.getValueAt(2, 1));
    }

    @Test
    public void testBuyStockTriggersControllerExecution() {
        JTextField tickerInputField = buyView.tickerInputField;
        JTextField amountInputField = buyView.amountInputField;
        tickerInputField.setText("AAPL");
        amountInputField.setText("10");

        BuyState currentState = buyViewModel.getState();
        currentState.setAmount("10");
        currentState.setTicker("AAPL");
        buyViewModel.setState(currentState);

        JButton purchaseButton = buyView.purchase;
        purchaseButton.doClick();

        Mockito.verify(buyController, Mockito.times(1)).execute(eq("10"), eq("AAPL"));
    }


    @Test
    public void testBackButtonNavigatesToDashboard() {
        JButton backButton = findButton(buyView, "Back");
        assertNotNull(backButton);

        backButton.doClick();
        Mockito.verify(viewManagerModel, Mockito.times(1)).setActiveView("dashboard");
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
}