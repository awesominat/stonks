package view;

import interface_adapter.Dashboard.DashboardViewModel;
import interface_adapter.GetTransactionHistory.*;
import interface_adapter.ViewManagerModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

public class TransactionHistoryView extends JPanel implements ActionListener, PropertyChangeListener {
    public final String viewName = "transactionHistory";

    // transaction history view model that stores that transaction history state
    private final GetTransactionHistoryViewModel getTransactionHistoryViewModel;
    // Transaction history controller that executes the use case
    GetTransactionHistoryController getTransactionHistoryController;

    // Transaction history table
    final JTable table;

    // back button to return to the dashboard view
    final JButton back;

    // dropdown menus for filter selection
    DefaultComboBoxModel<String> stockInputFieldFilterModel;
    final JComboBox<String> stockInputFieldFilter;
    DefaultComboBoxModel<String> typeInputFieldFilterModel;
    final JComboBox<String> typeInputFieldFilter;

    // the filter collection interface which allows us to apply the filters
    FilterCollectionInterface filterCollection = new FilterCollection();

    // currently selected stock and transaction type in the filter
    String selectedStock = null;
    String selectedType = null;

    /**
     * Constructs a {@code TransactionHistoryView} with the specified dependencies.
     *
     * @param getTransactionHistoryController the controller for transaction history actions
     * @param getTransactionHistoryViewModel  the view model for transaction history data
     * @param viewManagerModel                the model for managing views
     * @param dashboardViewModel              the view model for the dashboard
     */
    public TransactionHistoryView(
            GetTransactionHistoryController getTransactionHistoryController,
            GetTransactionHistoryViewModel getTransactionHistoryViewModel,
            ViewManagerModel viewManagerModel,
            DashboardViewModel dashboardViewModel
    ) {

        this.getTransactionHistoryController = getTransactionHistoryController;
        this.getTransactionHistoryViewModel = getTransactionHistoryViewModel;
        this.getTransactionHistoryViewModel.addPropertyChangeListener(this);
        setPreferredSize(new Dimension(400, 400));
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        filterCollection.add(new FilterByStockName());
        filterCollection.add(new FilterByTransactionType());

        back = new JButton(getTransactionHistoryViewModel.BACK_BUTTON_LABEL);
        table = new JTable();
        JLabel filterLabel = new JLabel("Filter settings:");
        filterLabel.setFont(new Font(filterLabel.getFont().getName(), Font.BOLD, 20));
        JLabel stockFilterLabel = new JLabel("Stock filter");
        JLabel typeFilterLabel = new JLabel("Transaction type filter");
        JScrollPane scrollPane = new JScrollPane(table);

        stockInputFieldFilterModel = new DefaultComboBoxModel<>();
        stockInputFieldFilter = new JComboBox<>(stockInputFieldFilterModel);

        typeInputFieldFilterModel = new DefaultComboBoxModel<>();
        typeInputFieldFilter = new JComboBox<>(typeInputFieldFilterModel);

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));

        leftPanel.add(back);
        leftPanel.add(filterLabel);
        leftPanel.add(stockFilterLabel);
        leftPanel.add(stockInputFieldFilter);
        leftPanel.add(typeFilterLabel);
        leftPanel.add(typeInputFieldFilter);

        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.add(scrollPane);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
        splitPane.setResizeWeight(0.05);

        back.addActionListener(
                evt -> {
                    dashboardViewModel.firePropertyChanged();
                    viewManagerModel.setActiveView(dashboardViewModel.getViewName());
                    viewManagerModel.firePropertyChanged();
                }
        );

        stockInputFieldFilter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedStock = String.valueOf(stockInputFieldFilter.getSelectedItem());
                getTransactionHistoryViewModel.firePropertyChanged();
            }
        });

        typeInputFieldFilter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedType = String.valueOf(typeInputFieldFilter.getSelectedItem());
                getTransactionHistoryViewModel.firePropertyChanged();
            }
        });
        typeInputFieldFilterModel.addElement("No filter");
        typeInputFieldFilterModel.addElement("BUY");
        typeInputFieldFilterModel.addElement("SELL");
        typeInputFieldFilterModel.addElement("TOPUP");

        this.add(splitPane);
    }

    /**
     * Handles the action performed event, e.g., button clicks.
     *
     * @param evt the action event
     */
    public void actionPerformed(ActionEvent evt) {
        System.out.println("Click " + evt.getActionCommand());
    }

    /**
     * Responds to property change events, updating the view based on changes in the
     * associated view model and applying filters to the displayed transaction data.
     *
     * @param evt the property change event
     */
    public void propertyChange(PropertyChangeEvent evt) {
        getTransactionHistoryController.execute();

        if (stockInputFieldFilterModel.getIndexOf("No filter") == -1) {
            stockInputFieldFilterModel.addElement("No filter");
        }
        for (String stock: getTransactionHistoryViewModel.getState().allStocksInHistory()){
            if (stockInputFieldFilterModel.getIndexOf(stock) == -1 && !stock.equals("RESET")) {
                stockInputFieldFilterModel.addElement(stock);
            }
        }
        if (selectedStock != null) {
            System.out.println(selectedStock);
            stockInputFieldFilterModel.setSelectedItem(selectedStock);
        }

        typeInputFieldFilterModel.setSelectedItem(selectedType);

        GetTransactionHistoryState state = getTransactionHistoryViewModel.getState();

        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("Stock");
        tableModel.addColumn("Transaction Type");
        tableModel.addColumn("Amount");
        tableModel.addColumn("Price");
        tableModel.addColumn("Date");
        List<List<String>> userRecord = state.getUserRecord();
        filterCollection.applyFilters(
                userRecord,
                new String[] {selectedStock, selectedType}
        );

        for (List<String> rowData : userRecord) {
                String stock = rowData.get(0);
                String type = rowData.get(1);
                String amount = rowData.get(2);
                String price = rowData.get(3);
                String date = rowData.get(4);
                tableModel.addRow(new Object[]{stock,
                                type, amount, "$" + price, date
                        }
                );
            }

        table.setModel(tableModel);
        JTableHeader header = table.getTableHeader();
        header.setBackground(Color.LIGHT_GRAY);
    }
}
