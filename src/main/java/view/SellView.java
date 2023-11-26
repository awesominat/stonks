package view;

import interface_adapters.Dashboard.DashboardState;
import interface_adapters.Dashboard.DashboardViewModel;
import interface_adapters.ViewManagerModel;
import interface_adapters.Sell.SellController;
import interface_adapters.Sell.SellViewModel;
import interface_adapters.Sell.SellState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import java.util.List;

public class SellView extends JPanel implements ActionListener, PropertyChangeListener {
    public final String viewName = "sell";

    private final SellController sellController;
    private final SellViewModel sellViewModel;
    private final DashboardViewModel dashboardViewModel;
    private final ViewManagerModel viewManagerModel;

    final JComboBox<String> stockInputField = new JComboBox<String>();
    final JTextField amountInputField = new JTextField(3);

    final JButton sell;
    final JButton back;

    public SellView(SellViewModel sellViewModel, SellController sellController, ViewManagerModel viewManagerModel, DashboardViewModel dashboardViewModel) {
        this.sellController = sellController;
        this.viewManagerModel = viewManagerModel;
        this.dashboardViewModel = dashboardViewModel;
        this.sellViewModel = sellViewModel;
        this.sellViewModel.addPropertyChangeListener(this);

        JLabel title = new JLabel("Sell Screen");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel stockSelectionLabel = new JLabel("Select an owned stock");
        LabelTextPanel stockAmountInfo = new LabelTextPanel(
                new JLabel("Select amount to sell"), amountInputField);


        JPanel buttons = new JPanel();
        back = new JButton("Back");
        buttons.add(back);
        sell = new JButton("Sell Stocks");
        buttons.add(sell);

        sell.addActionListener(                // This creates an anonymous subclass of ActionListener and instantiates it.
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(sell)) {
                            SellState currentState = sellViewModel.getState();

                            sellController.execute(
                                    currentState.getAmount(),
                                    currentState.getStockSelected()
                            );
                        }
                    }
                }
        );

        back.addActionListener(                // This creates an anonymous subclass of ActionListener and instantiates it.
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(back)) {
                            dashboardViewModel.firePropertyChanged();
                            viewManagerModel.setActiveView(dashboardViewModel.getViewName());
                            viewManagerModel.firePropertyChanged();
                        }
                    }
                }
        );

        amountInputField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                SellState currentState = sellViewModel.getState();
                currentState.setAmount(amountInputField.getText() + e.getKeyChar());
                sellViewModel.setState(currentState);
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

        stockInputField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SellState currentState = sellViewModel.getState();
                currentState.setStockSelected(String.valueOf(stockInputField.getSelectedItem()));
                sellViewModel.setState(currentState);
            }
        });
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));


        this.add(title);
        this.add(stockSelectionLabel);
        this.add(stockInputField);
        this.add(stockAmountInfo);
        this.add(buttons);
    }

    /**
     * React to a button click that results in evt.
     */
    public void actionPerformed(ActionEvent evt) {
        System.out.println("Click " + evt.getActionCommand());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        // Display any potential errors
        SellState state = (SellState) evt.getNewValue();
        String amountError = state.getAmountError();
        if (amountError != null) {
            JOptionPane.showMessageDialog(this, amountError);
        }
        state.setAmountError(null);
        sellViewModel.setState(state);

        // Get owned stocks list from dashboard for dropdown menu on sell page
        DashboardState dashboardState = dashboardViewModel.getState();
        List<String> ownedStocks = dashboardState.getOwnedTickers();
        stockInputField.removeAllItems();
        if (ownedStocks != null && !ownedStocks.isEmpty()) {
            for (String s: ownedStocks) {
                stockInputField.addItem(s);
            }
        }
    }

}
