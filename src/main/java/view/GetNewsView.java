package view;

import drivers.TableModel;
import interface_adapters.Buy.BuyState;
import interface_adapters.Dashboard.DashboardViewModel;
import interface_adapters.GetNews.GetNewsController;
import interface_adapters.GetNews.GetNewsState;
import interface_adapters.GetNews.GetNewsViewModel;
import interface_adapters.ViewManagerModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class GetNewsView extends JPanel implements ActionListener, PropertyChangeListener {
    public final String viewName = "news";
    private final GetNewsViewModel getNewsViewModel;
    private final DashboardViewModel dashboardViewModel;
    private final ViewManagerModel viewManagerModel;
    private final GetNewsController getNewsController;

    /**
     * Text fields, labels, and buttons
     */
    final JTextField tickerInputField = new JTextField(5);
    private final JLabel tickerErrorField = new JLabel();
    JTable table;
    final JButton search;
    final JButton back;
    JPanel topPanel;
    JPanel middlePanel;
    JPanel bottomPanel;

    public GetNewsView(GetNewsViewModel getNewsViewModel,
                       GetNewsController getNewsController,
                       ViewManagerModel viewManagerModel,
                       DashboardViewModel dashboardViewModel) {

        this.getNewsController = getNewsController;
        this.viewManagerModel = viewManagerModel;
        this.dashboardViewModel = dashboardViewModel;
        this.getNewsViewModel = getNewsViewModel;
        this.getNewsViewModel.addPropertyChangeListener(this);

        setPreferredSize(new Dimension(800, 400));

        back = new JButton(getNewsViewModel.BACK_BUTTON_LABEL);
        search = new JButton(getNewsViewModel.SEARCH_BUTTON_LABEL);

        // Create stock search bar.
        LabelTextPanel tickerInput = new LabelTextPanel(
                new JLabel("Stock ticker"), tickerInputField);
        tickerInput.setAlignmentX(Component.CENTER_ALIGNMENT);

        back.addActionListener(
                evt -> {
                    dashboardViewModel.firePropertyChanged();
                    viewManagerModel.setActiveView(dashboardViewModel.getViewName());
                    viewManagerModel.firePropertyChanged();
                }
        );

        search.addActionListener(
                evt -> {
                    GetNewsState currentState = getNewsViewModel.getState();

                    getNewsController.execute(currentState.getTicker());

                    // TODO: show news items (perhaps not implemented here)

                }
        );

        tickerInputField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                GetNewsState currentState = getNewsViewModel.getState();
                currentState.setTicker(tickerInputField.getText() + e.getKeyChar());
                getNewsViewModel.setState(currentState);
            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
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

        topPanel.add(tickerErrorField);

        gbc.gridx = 4;
        gbc.weightx = 1.24;
        topPanel.add(Box.createHorizontalGlue(), gbc);
        this.add(topPanel, BorderLayout.NORTH);

        gbc.gridx = 0;

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.weightx = 0;

        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.weightx = 0;

        middlePanel = new JPanel(new GridBagLayout());
        table = new JTable();
        table.setPreferredSize(new Dimension(100, 200));
//        table.setMinimumSize(new Dimension(100, 100));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
//        gbc.weighty = 10000;
        middlePanel.add(table, gbc);
        add(middlePanel);

        middlePanel.setVisible(false);

    }

    /**
     * React to a button click that results in evt.
     */
    public void actionPerformed(ActionEvent evt) {
        System.out.println("Click " + evt.getActionCommand());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        GetNewsState state = (GetNewsState) evt.getNewValue();
        setFields(state);

        if (state.getNewsItems() != null && state.getRenderNewInfo() != null) {
            table.setModel(new TableModel(state.getNewsItem()));

            middlePanel.setVisible(true);
            bottomPanel.setVisible(true);
            state.setRenderNewInfo(null);
            getNewsViewModel.setState(state);
        }

        String tickerError = state.getTickerError();

        if (tickerError != null) {
            JOptionPane.showMessageDialog(this, tickerError);
            state.setTickerError(null);
            getNewsViewModel.setState(state);
        }

    }

    private void setFields(GetNewsState state) {
        tickerInputField.setText(state.getTicker());
    }

}
