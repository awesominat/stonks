package view;

import interface_adapter.Dashboard.DashboardViewModel;
import interface_adapter.ViewManagerModel;
import interface_adapter.Sell.SellController;
import interface_adapter.Sell.SellViewModel;
import interface_adapter.Sell.SellState;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
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

    // interface adapters required
    private final SellController sellController;
    private final SellViewModel sellViewModel;
    private final DashboardViewModel dashboardViewModel;
    private final ViewManagerModel viewManagerModel;

    // input fields (dropdown for stock selection and text input for amount)
    final JComboBox<String> stockInputField = new JComboBox<String>();
    final JTextField amountInputField = new JTextField(3);

    // buttons and labels
    final JButton sell;
    final JButton back;
    final JLabel currentBalance;

    // currently owned stocks table with prices
    final JTable table;

    /**
     * Constructor for the sell view. Initializes all tables, labels, and fields displayed on the screen
     *
     * @param sellViewModel         used to fire property changed when a refresh needs to be done
     * @param sellController        used to run the sell use case interactor
     * @param viewManagerModel      used to navigate to other pages (i.e. the back button)
     * @param dashboardViewModel    used to grab the name of the page that will be navigated back to
     *                              (since the back button takes the user back to the dashboard)
     */
    public SellView(
            SellViewModel sellViewModel,
            SellController sellController,
            ViewManagerModel viewManagerModel,
            DashboardViewModel dashboardViewModel
    ) {
        this.sellController = sellController;
        this.viewManagerModel = viewManagerModel;
        this.dashboardViewModel = dashboardViewModel;
        this.sellViewModel = sellViewModel;
        this.sellViewModel.addPropertyChangeListener(this);
        this.setLayout(new BorderLayout());

        currentBalance = new JLabel();
        currentBalance.setFont(new Font("Helvetica", Font.ROMAN_BASELINE, 16));
        currentBalance.setHorizontalAlignment(SwingConstants.CENTER);
        currentBalance.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        GridBagConstraints gbc = new GridBagConstraints();

        JPanel topPanel = new JPanel(new GridBagLayout());
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;

        JLabel stockSelectionLabel = new JLabel("Select an owned stock");
        LabelTextPanel stockAmountInfo = new LabelTextPanel(
                new JLabel("Select amount to sell"), amountInputField);

        stockInputField.setMaximumSize(new Dimension(100,100));
        stockInputField.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel buttons = new JPanel();
        back = new JButton("Back");
        buttons.add(back);
        sell = new JButton("Sell Stocks");
        buttons.add(sell);
        table = new JTable();
        table.setPreferredSize(new Dimension(30, 200));

        sell.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(sell)) {
                            SellState currentState = sellViewModel.getState();
                            // this is the first execution type, since the controller is called with the
                            // constructor for a sell execution type
                            sellController.execute(
                                    currentState.getAmount(),
                                    currentState.getStockSelected()
                            );
                            sellViewModel.firePropertyChanged();
                        }
                    }
                }
        );

        // navigates back to the dashboard
        back.addActionListener(
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

        this.add(currentBalance);
        this.add(new JScrollPane(table));
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

    /**
     * Function that populates the table, labels and fields on the screen.
     * This is run whenever anything on the screen is changed as a result of
     * either execution type.
     *
     * @param evt A PropertyChangeEvent object describing the event source
     *          and the property that has changed.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        // this is the second execution type, which causes refreshes to occur
        sellController.execute();
        SellState state = sellViewModel.getState();

        // success popup
        String sellSuccess = state.getSellSuccess();
        if (sellSuccess != null) {
            JOptionPane.showMessageDialog(this, sellSuccess);
        }
        state.setSellSuccess(null);

        // error popup
        String amountError = state.getAmountError();
        if (amountError != null) {
            JOptionPane.showMessageDialog(this, amountError);
        }
        state.setAmountError(null);
        sellViewModel.setState(state);

        // populating the dropdown menu with currently owned stocks
        List<String> ownedStocks = state.getOwnedStocks();
        stockInputField.removeAllItems();
        if (ownedStocks != null && !ownedStocks.isEmpty()) {
            for (String s: ownedStocks) {
                stockInputField.addItem(s);
            }
        }

        // populating the currently owned stocks table
        List<Double> ownedAmounts = state.getOwnedAmounts();
        List<Double> sellPrices = state.getSellAmounts();

        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("Stock");
        tableModel.addColumn("Amount Owned");
        tableModel.addColumn("Sell Price (Single Unit)");
        tableModel.addColumn("Sell Price (All Units)");

        for (int i = 0; i < ownedStocks.size(); i++) {
            String stockTicker = ownedStocks.get(i);
            Double amountOwned = ownedAmounts.get(i);
            Double sellPriceSingle = sellPrices.get(i);
            Double sellPriceAll = sellPriceSingle * amountOwned;
            if (sellPriceSingle > 0) {
                tableModel.addRow(new Object[] {
                        stockTicker, String.format("%.2f", amountOwned),
                        String.format("$%.2f", sellPriceSingle),
                        String.format("$%.2f", sellPriceAll)
                });
            } else {
                tableModel.addRow(new Object[] {
                        stockTicker, String.format("%.2f", amountOwned),
                        "Refresh to Update",
                        "Refresh to Update"
                });
            }
        }
        table.setModel(tableModel);
        JTableHeader header = table.getTableHeader();
        header.setBackground(Color.LIGHT_GRAY);

        // setting the current user balance
        Double userBalance = state.getBalance();
        currentBalance.setText(String.format("Current Balance: $%.2f", userBalance));
    }

}
