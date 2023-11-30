package view;

import interface_adapter.Dashboard.DashboardViewModel;
import interface_adapter.GetTransactionHistory.GetTransactionHistoryController;
import interface_adapter.GetTransactionHistory.GetTransactionHistoryState;
import interface_adapter.GetTransactionHistory.GetTransactionHistoryViewModel;
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
    public TransactionHistoryView(
            GetTransactionHistoryController getTransactionHistoryController,
            GetTransactionHistoryViewModel getTransactionHistoryViewModel,
            ViewManagerModel viewManagerModel,
            DashboardViewModel dashboardViewModel
    ) {
        this.getTransactionHistoryController = getTransactionHistoryController;

        this.getTransactionHistoryViewModel = getTransactionHistoryViewModel;

        this.getTransactionHistoryViewModel.addPropertyChangeListener(this);

        setPreferredSize(new Dimension(1600, 800));

        this.getTransactionHistoryController.execute();

        JLabel title = new JLabel("Transaction History");

        JTableHeader header = new JTableHeader();
        header.add(title);
        header.setBackground(Color.LIGHT_GRAY);

        title.setPreferredSize(new Dimension(160, 20));
        Border border = BorderFactory.createLineBorder(Color.BLACK);
        title.setBorder(border);
        title.setText("Transaction History");
//        title.setHorizontalAlignment(JLabel.NORTH);
        title.setAlignmentX(SwingConstants.NORTH);
        title.setFont(new Font("Helvetica", Font.BOLD, 18));
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setLayout(new BorderLayout());

        this.add(title);

//        JFrame frame = new JFrame("Transaction History");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//
//        // Create a JPanel with BorderLayout
//        JPanel mainPanel = new JPanel(new BorderLayout());
//
//        // Create a JLabel for the title
//        JLabel titleLabel = new JLabel("Transaction History");
//        titleLabel.setFont(new Font("Helvetica", Font.BOLD, 16));
//
//        // Add the title label to the west (left) side of the main panel
//        mainPanel.add(titleLabel, BorderLayout.NORTH);
//
//        frame.getContentPane().add(mainPanel);
//        frame.setSize(400, 300);
//        frame.setLocationRelativeTo(null);
//        frame.setVisible(false);


        back = new JButton(getTransactionHistoryViewModel.BACK_BUTTON_LABEL);
        back.setMaximumSize(new Dimension(20, 10));

        back.addActionListener(
                evt -> {
                    dashboardViewModel.firePropertyChanged();
                    viewManagerModel.setActiveView(dashboardViewModel.getViewName());
                    viewManagerModel.firePropertyChanged();
                }
        );

        table = new JTable();

        JScrollPane scrollPane = new JScrollPane(table);

//
//        this.add(titleLabel);
        this.add(title, BorderLayout.NORTH);
        setLayout(new BorderLayout());
        add(back, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    public void actionPerformed(ActionEvent evt) {
        System.out.println("Click " + evt.getActionCommand());
    }

    public void propertyChange(PropertyChangeEvent evt) {
        getTransactionHistoryController.execute();

        GetTransactionHistoryState state = getTransactionHistoryViewModel.getState();

        DefaultTableModel tableModel = new DefaultTableModel();


        tableModel.addColumn("Stock");

        tableModel.addColumn("Transaction Type");

        tableModel.addColumn("Amount");

        tableModel.addColumn("Price");

        tableModel.addColumn("Date");


        HashMap<String, List<List<String>>> userRecord = state.getUserRecord();

      
        for (HashMap.Entry<String, List<List<String>>> entry : userRecord.entrySet()) {
            String key = entry.getKey();
            List<List<String>> dataList = entry.getValue();

            for (List<String> rowData : dataList) {
                String stock = rowData.get(0);
                String amount = rowData.get(1);
                String price = rowData.get(2);
                String date = rowData.get(3);
                tableModel.addRow(new Object[]{key,
                        stock, amount, "$" + price, date
                }
                );
            }
        }

//        table.setPreferredSize(new Dimension(1440, 800));
        table.setModel(tableModel);
        JTableHeader header = table.getTableHeader();
        header.setBackground(Color.LIGHT_GRAY);
    }
}
