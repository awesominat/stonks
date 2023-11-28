package view;

import drivers.TableModel;

// Imports for dashboard
import interface_adapters.Dashboard.DashboardController;
import interface_adapters.Dashboard.DashboardState;
import interface_adapters.Dashboard.DashboardViewModel;
import interface_adapters.GetNews.GetNewsViewModel;
import interface_adapters.GetTransactionHistory.GetTransactionHistoryViewModel;
import interface_adapters.ResetBalance.ResetBalanceController;
import interface_adapters.Buy.BuyViewModel;
import interface_adapters.Sell.SellViewModel;
import interface_adapters.ViewManagerModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DashboardView extends JPanel implements ActionListener, PropertyChangeListener {
    public final String viewName = "dashboard";
    private final DashboardController dashboardController;
    private final DashboardViewModel dashboardViewModel;
    private final BuyViewModel buyViewModel;
    private final SellViewModel sellViewModel;
    private final GetNewsViewModel getNewsViewModel;
    private final ResetBalanceController resetBalanceController;
    private final GetTransactionHistoryViewModel historyViewModel;
    private final ViewManagerModel viewManagerModel;

    // Buttons to be displayed along the left side of the screen for various options the user has.
    final JButton refresh;
    final JButton buy;
    final JButton sell;
    final JButton news;
    final JButton reset;
    final JButton history;

    // Panels to organize the view.
    JPanel topPanel;
    JPanel middlePanel;
    JPanel bottomPanel;

    // Field to show User's balance.
    private final JLabel balanceField = new JLabel();

    // Table showing user stats
    final JTable userStatsTable;

    // Table to show owned stocks and the statuses thereof.
    final JTable ownedStocksTable;

    /**
     * The homepage (dashboard) window with a title, a welcome message, a panel of action buttons, statistics about
     * the user, and information about stocks the user has invested in.
     */
    public DashboardView(DashboardViewModel dashboardViewModel,
                         DashboardController dashboardController,
                         BuyViewModel buyViewModel,
                         SellViewModel sellViewModel,
                         GetNewsViewModel getNewsViewModel,
                         ResetBalanceController resetBalanceController,
                         GetTransactionHistoryViewModel historyViewModel,
                         ViewManagerModel viewManagerModel) {

        this.viewManagerModel = viewManagerModel;
        this.dashboardController = dashboardController;
        this.dashboardViewModel = dashboardViewModel;
        this.buyViewModel = buyViewModel;
        this.sellViewModel = sellViewModel;
        this.getNewsViewModel = getNewsViewModel;
        this.resetBalanceController = resetBalanceController;
        this.historyViewModel = historyViewModel;
        this.dashboardViewModel.addPropertyChangeListener(this);

        // Create all the buttons in this view.
        JPanel buttons = new JPanel(new GridLayout(6, 1));
        refresh = new JButton(dashboardViewModel.REFRESH_BUTTON_LABEL);
        buttons.add(refresh);
        buy = new JButton(dashboardViewModel.PURCHASE_BUTTON_LABEL);
        buttons.add(buy);
        sell = new JButton(dashboardViewModel.SELL_BUTTON_LABEL);
        buttons.add(sell);
        news = new JButton(dashboardViewModel.NEWS_BUTTON_LABEL);
        buttons.add(news);
        reset = new JButton(dashboardViewModel.RESET_BUTTON_LABEL);
        buttons.add(reset);
        history = new JButton(dashboardViewModel.TRANSACTION_HISTORY_BUTTON_LABEL);
        buttons.add(history);

        // Add label for balanceField.
        JLabel balanceLabel = new JLabel("Your Balance: ");

        // Initialize an empty table for the User's stats
        userStatsTable = new JTable();

        // Initialize empty table for User's stock portfolio data
        ownedStocksTable = new JTable();

        // Create an anonymous subclass of ActionListener and instantiate it.
        refresh.addActionListener(
                evt -> {
                    dashboardController.execute();
                    dashboardViewModel.firePropertyChanged();
                }
        );

        buy.addActionListener(
                evt -> {
                    buyViewModel.firePropertyChanged();
                    viewManagerModel.setActiveView(buyViewModel.getViewName());
                    viewManagerModel.firePropertyChanged();
                }
        );

        sell.addActionListener(
                evt -> {
                    sellViewModel.firePropertyChanged();
                    viewManagerModel.setActiveView(sellViewModel.getViewName());
                    viewManagerModel.firePropertyChanged();
                }
        );

        news.addActionListener(
                evt -> {
                    getNewsViewModel.firePropertyChanged();
                    viewManagerModel.setActiveView(getNewsViewModel.getViewName());
                    viewManagerModel.firePropertyChanged();
                }
        );

        reset.addActionListener(
                evt -> {
                    resetBalanceController.execute();
                    dashboardViewModel.firePropertyChanged();

                    // TODO: add reset balance pop-up
                }
        );

        history.addActionListener(
                evt -> {
                    historyViewModel.firePropertyChanged();
                    viewManagerModel.setActiveView(historyViewModel.getViewName());
                    viewManagerModel.firePropertyChanged();
                }
        );

        // Make the various display items visible to the user on the display.
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setLayout(new BorderLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        topPanel = new JPanel(new GridBagLayout());
        middlePanel = new JPanel(new GridBagLayout());
        bottomPanel = new JPanel(new GridBagLayout());

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        topPanel.add(buttons, gbc);

        // Add the user's current balance to the view.
        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.weightx = 0;
        topPanel.add(balanceField, gbc);

        // Top Panel formatting stuff
        gbc.gridx = 1;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        topPanel.add(Box.createHorizontalGlue(), gbc);
        gbc.gridx = 4;
        gbc.weightx = 1.24;
        topPanel.add(Box.createHorizontalGlue(), gbc);

        // Spice up the labels a bit.
        Font labelFont = balanceLabel.getFont();
//        balanceLabel.setForeground(new Color(14, 20, 138));
        balanceLabel.setSize(25, 5);
        balanceLabel.setFont(labelFont.deriveFont(Font.BOLD));

        // Spice up the actual balance (field) as well.
        Font fieldFont = balanceField.getFont();
        balanceField.setForeground(Color.DARK_GRAY);
        balanceField.setFont(fieldFont.deriveFont(Font.ITALIC));
        balanceField.setSize(30, 8);

        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        topPanel.add(balanceLabel, gbc);

        // Add panels to the view.
        add(topPanel, BorderLayout.NORTH);
        add(middlePanel);
        add(bottomPanel, BorderLayout.SOUTH);

        middlePanel.setVisible(true);
        bottomPanel.setVisible(true);

//        dashboardViewModel.getState().setRenderNewInfo(true);
    }

    /**
     * React to a button click that results in evt.
     */
    public void actionPerformed(ActionEvent evt) {
        System.out.println("Click " + evt.getActionCommand());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        DashboardState state = (DashboardState) evt.getNewValue();
        setFields(state);

        // Avoid executing full use case unless necessary
        if (state.getRenderNewInfo() != null) {
            // Run Dashboard use case
            dashboardController.execute();

            // Fill data into userStatsTable
            HashMap<String, String> userStats = state.getUserStats();
            if (userStats != null) {
                userStatsTable.setModel(new TableModel(userStats));
            }
            userStatsTable.setName("User Information");

            // Execute setup for User's Stock Holdings Information table.
            // Create new default table model for displaying stock information.
            DefaultTableModel tableModel = new DefaultTableModel();

            // Add columns to the stock table model
            tableModel.addColumn("Ticker");
            tableModel.addColumn("Full Name");
            tableModel.addColumn("Amount");
            tableModel.addColumn("Market Price");

            // Get stock info
            List<String> tickers = state.getOwnedTickers();
            List<String> names = state.getOwnedFullNames();
            List<String> amounts = state.getOwnedAmountsStrings();
            List<String> prices = state.getPricesStrings();

            // Fill in rows of stock info table.
            if (tickers != null) {
                for (int i = 0; i < tickers.size(); i++) {
                    List<Object> row = new ArrayList<>();
                    row.add(tickers.get(i));
                    row.add(names.get(i));
                    row.add(amounts.get(i));
                    row.add(prices.get(i));
                    // Cast List object `row` to Object array, then pass it as argument to `tableModel.addRow`
                    tableModel.addRow(row.toArray());
                }
            }

            // Create GridBagConstraints for all the additions to the main three panels about to be executed.
            GridBagConstraints gbc = new GridBagConstraints();

            // Add Table of User Statistics to the view in the middle panel
            gbc.gridx = 0;
            gbc.gridy = 2;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.weightx = 1;
            middlePanel.add(userStatsTable, gbc);

            ownedStocksTable.setModel(tableModel);
            JTableHeader header = ownedStocksTable.getTableHeader();
            header.setBackground(Color.LIGHT_GRAY);

            gbc.gridx = 0;
            gbc.gridy = 4;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.weightx = 1;

            // Use Scroll Pane to make the table look nicer
            JScrollPane ownedStocksScrollPane = new JScrollPane(ownedStocksTable);
            bottomPanel.add(ownedStocksScrollPane, gbc);

            middlePanel.setVisible(true);
            bottomPanel.setVisible(true);
            // Reset the setRenderNewInfo property
            state.setRenderNewInfo(null);
            dashboardViewModel.setState(state);
        }
    }

    private void setFields(DashboardState state) {
        Double balance = state.getCurBalance();
        if (balance != null) {
            balanceField.setText(String.format("%.2f", state.getCurBalance().floatValue()));
        }
    }

}