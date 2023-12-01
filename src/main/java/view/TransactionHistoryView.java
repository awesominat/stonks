package view;

import interface_adapter.Dashboard.DashboardViewModel;
import interface_adapter.GetTransactionHistory.GetTransactionHistoryController;
import interface_adapter.GetTransactionHistory.GetTransactionHistoryState;
import interface_adapter.GetTransactionHistory.GetTransactionHistoryViewModel;
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
import java.util.HashMap;
import java.util.List;

public class TransactionHistoryView extends JPanel implements ActionListener, PropertyChangeListener {

    public final String viewName = "transactionHistory";
    private final GetTransactionHistoryViewModel getTransactionHistoryViewModel;
    JTable table;
    final JButton back;
    GetTransactionHistoryController getTransactionHistoryController;
    final JComboBox<String> stockInputFieldFilter = new JComboBox<String>();
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

        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setLayout(new BorderLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JPanel topPanel = new JPanel(new GridBagLayout());
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;

        setPreferredSize(new Dimension(400, 400));

        this.getTransactionHistoryController.execute();

        JLabel title = new JLabel("Transaction History");

        JTableHeader header = new JTableHeader();
        header.add(title);
        header.setBackground(Color.LIGHT_GRAY);

        title.setPreferredSize(new Dimension(160, 20));
        Border border = BorderFactory.createLineBorder(Color.BLACK);
        title.setBorder(border);
        title.setText("Transaction History");
        title.setAlignmentX(SwingConstants.NORTH);
        title.setFont(new Font("Helvetica", Font.BOLD, 18));
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setLayout(new BorderLayout());

        topPanel.add(title);

        back = new JButton(getTransactionHistoryViewModel.BACK_BUTTON_LABEL);
        back.setMaximumSize(new Dimension(20, 10));

        back.addActionListener(
                evt -> {
                    dashboardViewModel.firePropertyChanged();
                    viewManagerModel.setActiveView(dashboardViewModel.getViewName());
                    viewManagerModel.firePropertyChanged();
                }
        );

        topPanel.add(back);
        table = new JTable();

        JScrollPane scrollPane = new JScrollPane(table);

        JLabel stockInputFieldFilterTitle = new JLabel("Select an owned stock");
        stockInputFieldFilterTitle.setMaximumSize(new Dimension(100,100));
        stockInputFieldFilterTitle.setAlignmentX(SwingConstants.SOUTH_WEST);

        for (String stock: getTransactionHistoryViewModel.getState().allStocksInHistory()){
            stockInputFieldFilter.addItem(stock);
        }

        stockInputFieldFilter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GetTransactionHistoryState currentState = getTransactionHistoryViewModel.getState();
                selectedStock = String.valueOf(stockInputFieldFilter.getSelectedItem());
                getTransactionHistoryViewModel.firePropertyChanged();
            }
        });
        topPanel.add(stockInputFieldFilter);

//
//        this.add(titleLabel);
        add(topPanel, BorderLayout.NORTH);
        add(stockInputFieldFilter, BorderLayout.NORTH);
//        add(title, BorderLayout.NORTH);
        setLayout(new BorderLayout());
//        add(back, BorderLayout.BEFORE_FIRST_LINE);
        add(scrollPane, BorderLayout.CENTER);
    }

    public void actionPerformed(ActionEvent evt) {
        System.out.println("Click " + evt.getActionCommand());
    }

    public void propertyChange(PropertyChangeEvent evt) {
        getTransactionHistoryController.execute();
        System.out.println(selectedStock);

        GetTransactionHistoryState state = getTransactionHistoryViewModel.getState();

        DefaultTableModel tableModel = new DefaultTableModel();


        tableModel.addColumn("Stock");

        tableModel.addColumn("Transaction Type");

        tableModel.addColumn("Amount");

        tableModel.addColumn("Price");

        tableModel.addColumn("Date");


        List<List<String>> userRecord = state.getUserRecord();

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
