package view;

import drivers.TableModel;
import interface_adapters.Buy.BuyController;
import interface_adapters.Buy.BuyState;
import interface_adapters.Buy.BuyViewModel;
import interface_adapters.Dashboard.DashboardViewModel;
import interface_adapters.GetTransactionHistory.GetTransactionHistoryController;
import interface_adapters.GetTransactionHistory.GetTransactionHistoryViewModel;
import interface_adapters.ViewManagerModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.List;

public class TransactionHistoryView extends JPanel implements ActionListener, PropertyChangeListener {

    public final String viewName = "transactionHistory";
    private final GetTransactionHistoryViewModel getTransactionHistoryViewModel;
    JTable table;
    final JButton back;
    GetTransactionHistoryController getTransactionHistoryController;
    public TransactionHistoryView(GetTransactionHistoryController getTransactionHistoryController,
                                  GetTransactionHistoryViewModel getTransactionHistoryViewModel,
                                  ViewManagerModel viewManagerModel,
                                  DashboardViewModel dashboardViewModel
    ) {
        this.getTransactionHistoryController = getTransactionHistoryController;
        this.getTransactionHistoryViewModel = getTransactionHistoryViewModel;
        setPreferredSize(new Dimension(800, 400));

        this.getTransactionHistoryController.execute();
        back = new JButton(getTransactionHistoryViewModel.BACK_BUTTON_LABEL);

        back.addActionListener(
                evt -> {
                    dashboardViewModel.firePropertyChanged();
                    viewManagerModel.setActiveView(dashboardViewModel.getViewName());
                    viewManagerModel.firePropertyChanged();
                }
        );
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setLayout(new BorderLayout());

        DefaultTableModel tableModel = new DefaultTableModel();

        // Add columns to the model
        tableModel.addColumn("Stock");  // The key string
        tableModel.addColumn("Transaction Type");
        tableModel.addColumn("Amount");
        tableModel.addColumn("Price");
        tableModel.addColumn("Date");

        // Populate the model with data
        HashMap<String, List<List<String>>> userRecord = getTransactionHistoryViewModel.getState().getUserRecord();
        System.out.println(userRecord);
        for (HashMap.Entry<String, List<List<String>>> entry : userRecord.entrySet()) {
            String key = entry.getKey();
            List<List<String>> dataList = entry.getValue();

            for (List<String> rowData : dataList) {
                tableModel.addRow(new Object[]{key, rowData.get(0), rowData.get(1), rowData.get(2), rowData.get(3)});
            }
        }
//        for (HashMap.Entry<String, List<List<String>>> entry : userRecord.entrySet()) {
//            String key = entry.getKey();
//            List<List<String>> dataList = entry.getValue();
//            for (List<String> stringList : dataList) {
//                Object[] rowData = new Object[5];
//                rowData[0] = key;
//                for (int i = 0; i < stringList.size(); i++) {
//                    rowData[i + 1] = stringList.get(i);
//                }
//                tableModel.addRow(rowData);
//            }
//        }

        // Create the table using the model
        table = new JTable(tableModel);

        // Add the table to a scroll pane
        JScrollPane scrollPane = new JScrollPane(table);

        // Set layout and add components
        setLayout(new BorderLayout());
        add(back, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

//    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }
}
