package view;

// Imports for dashboard

import drivers.TableModel;
import interface_adapters.Dashboard.DashboardController;
import interface_adapters.Dashboard.DashboardState;
import interface_adapters.Dashboard.DashboardViewModel;
import interface_adapters.ResetBalance.ResetBalanceController;
import interface_adapters.ViewManagerModel;

// Imports to allow dashboard to switch to other views
import interface_adapters.Buy.BuyViewModel;
import interface_adapters.Sell.SellViewModel;
import interface_adapters.GetNews.GetNewsViewModel;
import interface_adapters.GetTransactionHistory.GetTransactionHistoryViewModel;

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
                    DashboardState currentState = dashboardViewModel.getState();
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
        JPanel intermediatePanel1 = new JPanel(new GridBagLayout());
        middlePanel = new JPanel(new GridBagLayout());
        JPanel intermediatePanel2 = new JPanel(new GridBagLayout());
        bottomPanel = new JPanel(new GridBagLayout());

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
//        buttons.setPreferredSize(new Dimension(30, 80));
        topPanel.add(buttons, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
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

        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        topPanel.add(balanceLabel, gbc);

        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.weightx = 0;
        topPanel.add(balanceField, gbc);

        // Get DashboardState after running the use case
        dashboardController.execute();
        DashboardState state = dashboardViewModel.getState();

        HashMap<String, String> userStats = state.getUserStats();
        if (userStats != null) {
//            userStatsTable.setPreferredSize(new Dimension(100, 200));
            userStatsTable.setModel(new TableModel(userStats));
        }

        userStatsTable.setName("User Information");

//        userStatsTable.setMinimumSize(new Dimension(100, 100));

        // Create new default table model for displaying stock information.
        DefaultTableModel tableModel = new DefaultTableModel();

        // Add columns to the model
        tableModel.addColumn("Tickers");
        tableModel.addColumn("Full Names");
        tableModel.addColumn("Amounts");

        List<String> tickers = state.getOwnedTickers();
        List<String> names = state.getOwnedFullNames();
        List<String> amounts = state.getOwnedAmountsStrings();

        if (tickers != null) {
            for (int i = 0; i < tickers.size(); i++) {
                List<Object> row = new ArrayList<>();
                row.add(tickers.get(i));
                row.add(names.get(i));
                row.add(amounts.get(i));
                // Convert List object `row` to Object array and then pass it as argument to `tableModel.addRow`
                tableModel.addRow(row.toArray());
            }
        }

        // Add top panel
        gbc.gridx = 4;
        gbc.weightx = 1.24;
        topPanel.add(Box.createHorizontalGlue(), gbc);
        this.add(topPanel, BorderLayout.NORTH);

//        // Add invisible intermediate panel (number one)
//        gbc.gridx = 0;
//        gbc.gridy = 1;
//        gbc.fill = GridBagConstraints.HORIZONTAL;
//        gbc.weightx = 1;
//        this.add(intermediatePanel1);
//        intermediatePanel1.setVisible(false);

        // Add middle panel
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        middlePanel.add(userStatsTable, gbc);
        this.add(middlePanel);
        middlePanel.setVisible(true);

//        // Add invisible intermediate panel (number two)
//        gbc.gridx = 0;
//        gbc.gridy = 3;
//        gbc.fill = GridBagConstraints.HORIZONTAL;
//        gbc.weightx = 1;
//        this.add(intermediatePanel2);
//        intermediatePanel2.setVisible(false);

//        int currentHeight = Double.valueOf(this.getPreferredSize().getHeight()).intValue();
//        System.out.println(currentHeight);
//        ownedStocksTable.setPreferredSize(new Dimension(this.getWidth(), currentHeight));
//        ownedStocksTable.setPreferredSize(new Dimension(300, 200));
        ownedStocksTable.setModel(tableModel);
        JTableHeader header = ownedStocksTable.getTableHeader();
        header.setBackground(Color.LIGHT_GRAY);
//        header.setVisible(true);

        // Add bottom panel
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        // Use Scroll Pane to make the table look nicer
        JScrollPane ownedStocksScrollPane = new JScrollPane(ownedStocksTable);
        bottomPanel.add(ownedStocksScrollPane, gbc);
        this.add(bottomPanel, BorderLayout.SOUTH);
        bottomPanel.setVisible(true);

        // Note: `.floatValue()` may not be necessary
        balanceField.setText(String.format("%.2f", state.getCurBalance().floatValue()));
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
//        DashboardState state = dashboardViewModel.getState();

        // Avoid executing full use case unless necessary
        if (state.getRenderNewInfo() != null) {
            dashboardController.execute();
            state.setRenderNewInfo(null);
        }
        setFields(state);
    }

    private void setFields(DashboardState state) {
        balanceField.setText(String.format("%.2f", state.getCurBalance().floatValue()));
    }

}