package view;

import driver.TableModel;
import interface_adapter.Buy.BuyController;
import interface_adapter.Buy.BuyState;
import interface_adapter.Buy.BuyViewModel;
import interface_adapter.Dashboard.DashboardViewModel;
import interface_adapter.ViewManagerModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Defines the view for the Buy use case.
 */
public class BuyView extends JPanel implements ActionListener, PropertyChangeListener {

    /** The name of the view. */
    public final String viewName = "buy";

    /** The name of the view. */
    private final BuyViewModel buyViewModel;

    /** The controller for the view. */
    private final BuyController buyController;

    final JTextField tickerInputField = new JTextField(15);
    final JTextField amountInputField = new JTextField(15);
    JTable table;
    final JButton purchase;
    final JButton search;
    final JButton back;
    JPanel topPanel;
    JPanel middlePanel;
    JPanel bottomPanel;
    private final JLabel balanceField = new JLabel();

    private void updateBalanceLabelColor() {
        BuyState state = buyViewModel.getState();
        if (state == null || state.getCurBalance() == null || state.getStockInfo() == null) {
            return;
        }

        Double currentBalance = state.getCurBalance();
        Double purchaseAmount;
        try {
            purchaseAmount = Double.parseDouble(amountInputField.getText());
        } catch (NumberFormatException e) {
            purchaseAmount = 0.0;
        }

        Double stockPrice = Double.valueOf(state.getStockInfo().get("price"));

        if (currentBalance >= purchaseAmount * stockPrice) {
            balanceField.setForeground(Color.GREEN);
        } else {
            balanceField.setForeground(Color.RED);
        }
    }

    /**
     * @param buyController the controller for the view's use case
     * @param buyViewModel the view model for the view
     * @param viewManagerModel the view manager model
     * @param dashboardViewModel the dashboard view model
     */
    public BuyView(
            BuyController buyController,
            BuyViewModel buyViewModel,
            ViewManagerModel viewManagerModel,
            DashboardViewModel dashboardViewModel
    ) {
        this.buyController = buyController;
        this.buyViewModel = buyViewModel;
        this.buyViewModel.addPropertyChangeListener(this);

        setPreferredSize(new Dimension(800, 400));

        back = new JButton(buyViewModel.BACK_BUTTON_LABEL);
        search = new JButton(buyViewModel.SEARCH_BUTTON_LABEL);

        LabelTextPanel tickerInput = new LabelTextPanel(
                new JLabel("Ticker"), tickerInputField);

        LabelTextPanel amountInput = new LabelTextPanel(
                new JLabel("Amount"), amountInputField);

        purchase = new JButton(buyViewModel.PURCHASE_BUTTON_LABEL);


        purchase.addActionListener(
                evt -> {
                    BuyState buyState = buyViewModel.getState();
                    buyController.execute(buyState.getAmount(), buyState.getTicker());
                }
        );
        search.addActionListener(
                evt -> {
                    BuyState buyState = buyViewModel.getState();
                    buyController.execute(buyState.getTicker());
                }
        );
        back.addActionListener(
                evt -> {
                    dashboardViewModel.firePropertyChanged();
                    viewManagerModel.setActiveView(dashboardViewModel.getViewName());
                    viewManagerModel.firePropertyChanged();
                }
        );

        tickerInputField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                BuyState currentState = buyViewModel.getState();
                currentState.setTicker(tickerInputField.getText() + e.getKeyChar());
                buyViewModel.setState(currentState);
            }

            @Override
            public void keyPressed(KeyEvent e) {}

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
        amountInputField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                BuyState currentState = buyViewModel.getState();
                currentState.setAmount(amountInputField.getText() + e.getKeyChar());
                buyViewModel.setState(currentState);
                updateBalanceLabelColor();
            }

            @Override
            public void keyPressed(KeyEvent e) {}

            @Override
            public void keyReleased(KeyEvent e) {}
        });
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setLayout(new BorderLayout());
        topPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        topPanel.add(back, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        topPanel.add(Box.createHorizontalGlue(), gbc);

        gbc.gridx = 2;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        topPanel.add(tickerInput, gbc);

        gbc.gridx = 3;
        gbc.weightx = 0;
        topPanel.add(search);

        gbc.gridx = 4;
        gbc.weightx = 1.24;
        topPanel.add(Box.createHorizontalGlue(), gbc);
        this.add(topPanel, BorderLayout.NORTH);

//        gbc.gridheight = 1;
//        gbc.gridx = 2;
//
//        JLabel title = new JLabel("Buy Stocks");
//
//        title.setPreferredSize(new Dimension(160, 20));
//        title.setText("Buy Stocks");
//        title.setHorizontalAlignment(JLabel.CENTER);
//        title.setFont(new Font("Helvetica", Font.BOLD, 18));
//        topPanel.add(title, gbc);

        bottomPanel = new JPanel(new GridBagLayout());

        gbc.gridx = 0;
        bottomPanel.add(amountInput, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.weightx = 0;
        bottomPanel.add(balanceField, gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.weightx = 0;
        purchase.setPreferredSize(new Dimension(350, 40));
        bottomPanel.add(purchase, gbc);


        this.add(bottomPanel, BorderLayout.SOUTH);

        middlePanel = new JPanel(new GridBagLayout());
        table = new JTable();
        table.setPreferredSize(new Dimension(100, 200));

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.weightx = 1;
        middlePanel.add(table, gbc);
        add(middlePanel);

        middlePanel.setVisible(false);
        bottomPanel.setVisible(false);

    }

    /**
     * @param evt the event to be processed by the controller
     */
    public void actionPerformed(ActionEvent evt) {
        System.out.println("Click " + evt.getActionCommand());
    }

    /**
     * @param evt A PropertyChangeEvent object describing the event source
     *            and the property that has changed.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        buyController.execute();
        BuyState state = (BuyState) evt.getNewValue();
        setFields(state);

        balanceField.setText("Current Balance: " + String.format("%.2f", state.getCurBalance()));
        balanceField.setToolTipText(String.valueOf(state.getCurBalance()));
        if (state.getStockInfo() != null && state.getRenderNewInfo() != null) {
            table.setModel(new TableModel(state.getStockInfo()));

            updateBalanceLabelColor();

            balanceField.setToolTipText(String.valueOf(state.getCurBalance()));

            middlePanel.setVisible(true);
            bottomPanel.setVisible(true);
            state.setRenderNewInfo(null);
            buyViewModel.setState(state);
        }
        if (state.getBoughtStock() != null) {
            updateBalanceLabelColor();

            JOptionPane.showMessageDialog(
                    this, String.format(
                            buyViewModel.JUST_BOUGHT_MESSAGE,
                            state.getAmount(),
                            state.getTicker())
            );

            state.setBoughtStock(null);

            buyViewModel.setState(state);
        }
        String amountError = state.getAmountError();
        if (amountError != null) {
            JOptionPane.showMessageDialog(this, amountError);
            state.setAmountError(null);
            buyViewModel.setState(state);
        }
    }

    /**
     * @param state the state used to set the fields of the view
     */
    private void setFields(BuyState state) {
        tickerInputField.setText(state.getTicker());
        amountInputField.setText(state.getAmount());
    }

}
