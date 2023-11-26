package view;
import interface_adapters.Dashboard.DashboardState;
import interface_adapters.GetTransactionHistory.GetTransactionHistoryState;
import interface_adapters.GetTransactionHistory.GetTransactionHistoryViewModel;
import interface_adapters.GetTransactionHistory.GetTransactionHistoryController;
import interface_adapters.Dashboard.DashboardViewModel;
import interface_adapters.Sell.SellController;
import interface_adapters.Sell.SellViewModel;
import interface_adapters.ViewManagerModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class TransactionHistoryView extends JFrame implements ActionListener, PropertyChangeListener {
    public final String viewName = "transactionHistory";
    private final JTable table;
    private final GetTransactionHistoryViewModel getTransactionHistoryViewModel;
    private final DashboardViewModel dashboardViewModel;
    private final ViewManagerModel viewManagerModel;

    private final GetTransactionHistoryController  getTransactionHistoryController;

    public TransactionHistoryView(GetTransactionHistoryViewModel getTransactionHistoryViewModel,
                              GetTransactionHistoryController getTransactionHistoryController,
                              ViewManagerModel viewManagerModel, DashboardViewModel dashboardViewModel) {
        this.getTransactionHistoryController = getTransactionHistoryController;
        this.viewManagerModel = viewManagerModel;
        this.getTransactionHistoryViewModel = getTransactionHistoryViewModel;
        this.dashboardViewModel = dashboardViewModel;

        // Set up the JFrame
        JLabel title = new JLabel("Transaction History");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);

        // Create a table model
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Stock");
        model.addColumn("Transaction Type");
        model.addColumn("Amount");
        model.addColumn("Price");
        model.addColumn("Date");

        GetTransactionHistoryState state = getTransactionHistoryViewModel.getState();
        for (Map.Entry<String, List<List<Object>>> entry : state.getUserRecord().entrySet()) {
            String stock = entry.getKey();
            List<List<Object>> dataList = entry.getValue();

            for (List<Object> rowData : dataList) {
                model.addRow(new Object[]{stock, rowData.get(0), rowData.get(1), rowData.get(2), rowData.get(3)});
            }
        }

        this.table = new JTable(model);

        // Add the table to a scroll pane
        JScrollPane scrollPane = new JScrollPane(table);

        // Add the scroll pane to the JFrame
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        // Display the JFrame
        setLocationRelativeTo(null);
        setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }
}
