package view;

import interface_adapter.Dashboard.DashboardViewModel;
import interface_adapter.GetTransactionHistory.*;
import interface_adapter.Sell.SellState;
import interface_adapter.ViewManagerModel;

import javax.swing.*;
import javax.swing.border.Border;
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

public class TransactionHistoryView extends JPanel implements ActionListener, PropertyChangeListener {

    public final String viewName = "transactionHistory";
    private final GetTransactionHistoryViewModel getTransactionHistoryViewModel;
    JTable table;
    final JButton back;
    GetTransactionHistoryController getTransactionHistoryController;
    DefaultComboBoxModel<String> stockInputFieldFilterModel;
    final JComboBox<String> stockInputFieldFilter;
    String selectedStock = null;
    final JComboBox<String> typeInputFieldFilter = new JComboBox<String>();

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


        back = new JButton(getTransactionHistoryViewModel.BACK_BUTTON_LABEL);
        back.setMaximumSize(new Dimension(20, 10));

        stockInputFieldFilterModel = new DefaultComboBoxModel<>();
        stockInputFieldFilter = new JComboBox<>(stockInputFieldFilterModel);

        back.addActionListener(
                evt -> {
                    dashboardViewModel.firePropertyChanged();

                    viewManagerModel.setActiveView(dashboardViewModel.getViewName());

                    viewManagerModel.firePropertyChanged();
                }
        );

        this.getTransactionHistoryController.execute();
        table = new JTable();

        JScrollPane scrollPane = new JScrollPane(table);

//        JLabel stockInputFieldFilterTitle = new JLabel("Select an owned stock");
//        stockInputFieldFilterTitle.setMaximumSize(new Dimension(100,100));
//        stockInputFieldFilterTitle.setAlignmentX(SwingConstants.SOUTH_WEST);

        stockInputFieldFilter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GetTransactionHistoryState currentState = getTransactionHistoryViewModel.getState();

                selectedStock = String.valueOf(stockInputFieldFilter.getSelectedItem());

                getTransactionHistoryViewModel.firePropertyChanged();
            }
        });

        this.add(stockInputFieldFilter);
        this.add(back);
        add(scrollPane, BorderLayout.CENTER);
    }

    public void actionPerformed(ActionEvent evt) {
        System.out.println("Click " + evt.getActionCommand());
    }

    public void propertyChange(PropertyChangeEvent evt) {
        getTransactionHistoryController.execute();

        stockInputFieldFilterModel.removeAllElements();

        stockInputFieldFilterModel.addElement("No filter");

        for (String stock: getTransactionHistoryViewModel.getState().allStocksInHistory()){

            if (stockInputFieldFilterModel.getIndexOf(stock) == -1 && !stock.equals("RESET")) {

                stockInputFieldFilterModel.addElement(stock);

            }

        }
        stockInputFieldFilterModel.setSelectedItem(selectedStock);

        GetTransactionHistoryState state = getTransactionHistoryViewModel.getState();

        DefaultTableModel tableModel = new DefaultTableModel();


        tableModel.addColumn("Stock");

        tableModel.addColumn("Transaction Type");

        tableModel.addColumn("Amount");

        tableModel.addColumn("Price");

        tableModel.addColumn("Date");

        List<List<String>> userRecord = new ArrayList<>();
        if (selectedStock == "No filter") {
             userRecord = state.getUserRecord();
        } else if (getTransactionHistoryViewModel.getState().allStocksInHistory().contains(selectedStock)) {
            Filter filterForStockName = new FilterByStockName();
            userRecord = filterForStockName.filter(state.getUserRecord(), selectedStock);
        }

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

//        if (selectedStock == "No filter") {
//            for (List<String> rowData : userRecord) {
//
//                String stock = rowData.get(0);
//                String type = rowData.get(1);
//                String amount = rowData.get(2);
//                String price = rowData.get(3);
//                String date = rowData.get(4);
//                tableModel.addRow(new Object[]{stock,
//                                type, amount, "$" + price, date
//                        }
//                );
//            }
//        } else if (getTransactionHistoryViewModel.getState().allStocksInHistory().contains(selectedStock)) {
//
//            Filter filterForStockName = new FilterByStockName();
//
//            List<List<String>> filteredTransactions = filterForStockName.filter(userRecord, selectedStock);
//
//            for (List<String> rowData : filteredTransactions) {
//
//                if (rowData.get(0) == selectedStock) {
//
//                    String stock = rowData.get(0);
//                    String type = rowData.get(1);
//                    String amount = rowData.get(2);
//                    String price = rowData.get(3);
//                    String date = rowData.get(4);
//
//                    tableModel.addRow(new Object[]{stock,
//                                    type, amount, "$" + price, date
//                            }
//                    );
//                }
//            }
//        }

        table.setModel(tableModel);
        JTableHeader header = table.getTableHeader();
        header.setBackground(Color.LIGHT_GRAY);
    }
}
