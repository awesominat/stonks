package view;

// Imports for dashboard
import interface_adapter.Dashboard.DashboardController;
import interface_adapter.Dashboard.DashboardState;
import interface_adapter.Dashboard.DashboardViewModel;
import interface_adapter.GetNews.GetNewsViewModel;
import interface_adapter.GetTransactionHistory.GetTransactionHistoryViewModel;
import interface_adapter.ResetBalance.ResetBalanceController;
import interface_adapter.Buy.BuyViewModel;
import interface_adapter.Sell.SellViewModel;
import interface_adapter.ViewManagerModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

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
                    dashboardController.execute(true);
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
                    resetBalanceController.execute(true);
                    dashboardViewModel.firePropertyChanged();
                    viewManagerModel.setActiveView(dashboardViewModel.getViewName());
                    viewManagerModel.firePropertyChanged();
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

        // Add Table of User Statistics to the view in the middle panel
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        middlePanel.add(userStatsTable, gbc);

        JTableHeader header = ownedStocksTable.getTableHeader();
        header.setBackground(Color.LIGHT_GRAY);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        // Use Scroll Pane to make the table look nicer
        bottomPanel.add(new JScrollPane(ownedStocksTable), gbc);

        // Add panels to the view.
        add(topPanel, BorderLayout.NORTH);
        add(middlePanel);
        add(bottomPanel, BorderLayout.SOUTH);

        middlePanel.setVisible(true);
        bottomPanel.setVisible(true);
    }

    /**
     * React to a button click that results in evt.
     */
    public void actionPerformed(ActionEvent evt) {
        System.out.println("Click " + evt.getActionCommand());
    }


    public void propertyChange(PropertyChangeEvent evt) {
        dashboardController.execute(false);

        DashboardState state = dashboardViewModel.getState();
        if (state.getResetPressed()) {
            JOptionPane.showMessageDialog(this, "Balance has been Reset!");
        }

        // Rendering user balance
        balanceField.setText(String.format("$%.2f", state.getUserStats().get("balance")));


        // Rendering user stats table
        DefaultTableModel userStatsTableModel = new DefaultTableModel();
        HashMap<String, Double> userStats = state.getUserStats();

        userStatsTableModel.addColumn("Attribute");
        userStatsTableModel.addColumn("Value");

        userStatsTableModel.addRow(new Object[]{"Balance", String.format("%.2f", userStats.get("balance"))});
        userStatsTableModel.addRow(new Object[]{"Portfolio Net worth", String.format("%.2f", userStats.get("Portfolio net worth"))});
        userStatsTableModel.addRow(new Object[]{"Net Worth", String.format("%.2f", userStats.get("Net worth"))});
        Double daysSinceLastReset = userStats.get("Days since last reset");
        if (daysSinceLastReset >= 0) {
            userStatsTableModel.addRow(new Object[]{"Days Since Last Reset", String.format("%.2f", daysSinceLastReset)});
        } else {
            userStatsTableModel.addRow(new Object[]{"Days Since Last Reset", "Never Reset"});
        }
        userStatsTable.setModel(userStatsTableModel);


        // Rendering owned stocks table
        DefaultTableModel ownedStocksTableModel = new DefaultTableModel();

        ownedStocksTableModel.addColumn("Ticker");
        ownedStocksTableModel.addColumn("Amount owned");
        ownedStocksTableModel.addColumn("Current Price");
        ownedStocksTableModel.addColumn("Price change");
        ownedStocksTableModel.addColumn("Percent change");

        List<String> ownedTickers = state.getOwnedTickers();
        List<Double> ownedAmounts = state.getOwnedAmounts();
        List<List<Double>> priceStats = state.getCurrentPriceStats();
        for (int i = 0; i < ownedTickers.size(); i++) {
            String ticker = ownedTickers.get(i);
            Double amount = ownedAmounts.get(i);
            List<Double> priceStatsForTicker = priceStats.get(i);

            Double currentPrice = priceStatsForTicker.get(0);
            Double priceChange = priceStatsForTicker.get(1);
            Double percentChange = priceStatsForTicker.get(2);

            if (currentPrice < 0) {
                ownedStocksTableModel.addRow(new Object[] {
                        ticker, String.format("%.2f", amount),
                        "Refresh to Update", "Refresh to Update", "Refresh to Update"
                });
            } else {
                ownedStocksTableModel.addRow(new Object[] {
                        ticker,
                        String.format("%.2f", amount),
                        String.format("%.2f", currentPrice),
                        String.format("%.2f", priceChange),
                        String.format("%.2f", percentChange),
                });
            }
        }
        ownedStocksTable.setModel(ownedStocksTableModel);


    }
}
