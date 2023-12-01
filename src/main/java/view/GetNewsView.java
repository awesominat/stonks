package view;

import java.util.Map;
import java.util.List;

import driver.TableModel;
import interface_adapter.Dashboard.DashboardViewModel;
import interface_adapter.GetNews.GetNewsController;
import interface_adapter.GetNews.GetNewsState;
import interface_adapter.GetNews.GetNewsViewModel;
import interface_adapter.ViewManagerModel;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Represents the graphical user interface for the GetNews use case.
 * Allows users to search for news articles related to a specific company (that is publicly traded) and displays the results.
 */
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
    final JButton search;
    final JButton back;
    JPanel topPanel;
    JPanel middlePanel;
    JTabbedPane newsTabs;
    ImageIcon icon;

    /**
     * Custom cell renderer to allow displaying a clipped preview of a larger String using a tooltip.
     */
    private static class ClippedPreviewTableCellRenderer extends DefaultTableCellRenderer {
        private static final int MAX_PREVIEW_LENGTH = 60;

        @Override
        public Component getTableCellRendererComponent(
                JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            if (value != null) {
                String text = value.toString();
                if (text.length() > MAX_PREVIEW_LENGTH) {
                    String previewText = text.substring(0, MAX_PREVIEW_LENGTH) + " ...";
                    setToolTipText("<html><p width='300'>" + text + "</p></html>");
                    setText(previewText);
                }
            }
            return c;
        }
    }

    /**
     * Constructs a new GetNewsView with the specified view models and controller.
     *
     * @param getNewsViewModel   The view model for the GetNews use case.
     * @param getNewsController  The controller for the GetNews use case.
     * @param viewManagerModel   The model responsible for managing views.
     * @param dashboardViewModel The view model for the Dashboard.
     */
    public GetNewsView(
            GetNewsViewModel getNewsViewModel,
            GetNewsController getNewsController,
            ViewManagerModel viewManagerModel,
            DashboardViewModel dashboardViewModel
    ) {
        this.getNewsController = getNewsController;
        this.viewManagerModel = viewManagerModel;
        this.dashboardViewModel = dashboardViewModel;
        this.getNewsViewModel = getNewsViewModel;
        this.getNewsViewModel.addPropertyChangeListener(this);

        setPreferredSize(new Dimension(800, 400));

        back = new JButton(getNewsViewModel.BACK_BUTTON_LABEL);
        search = new JButton(getNewsViewModel.SEARCH_BUTTON_LABEL);

        LabelTextPanel tickerInput = new LabelTextPanel(
                new JLabel(getNewsViewModel.SEARCH_BAR_LABEL), tickerInputField);
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

        // Set layout.
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setLayout(new BorderLayout());

        // Set components for top panel.
        topPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Set grid bag constraints for the back button and add it to the top panel.
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        topPanel.add(back, gbc);

        // Tedious formatting stuff.
        gbc.gridx = 1;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        topPanel.add(Box.createHorizontalGlue(), gbc);

        // Set grid bag constraints for the ticker input field and add it to the top panel.
        gbc.gridx = 2;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        topPanel.add(tickerInput, gbc);

        // Set grid bag constraints for the search button and add it to the top panel.
        gbc.gridx = 3;
        gbc.weightx = 0;
        topPanel.add(search);

        // Add the ticker error field to the top panel.
        topPanel.add(tickerErrorField);

        // Finish formatting top panel and add it to the top panel.
        gbc.gridx = 4;
        gbc.weightx = 1.24;
        topPanel.add(Box.createHorizontalGlue(), gbc);
        add(topPanel, BorderLayout.NORTH);

        // Initialize middle panel.
        middlePanel = new JPanel(new GridBagLayout());
        newsTabs = new JTabbedPane();
        icon = new ImageIcon("images/news.png");

        // Prepare formatting for middle panel and add it to the view.
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
//        gbc.weighty = 10000;
        middlePanel.add(newsTabs, gbc);
        add(middlePanel);

        // Set middle panel to be initially invisible.
        middlePanel.setVisible(false);
    }

    /**
     * React to a button click that results in evt.
     *
     * @param evt The ActionEvent representing the button click.
     */
    public void actionPerformed(ActionEvent evt) {
        System.out.println("Click " + evt.getActionCommand());
    }

    /**
     * Responds to property change events, updating the view based on the new state.
     *
     * @param evt  The PropertyChangeEvent representing a change in one of the view's properties.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        // Get new state.
        GetNewsState state = (GetNewsState) evt.getNewValue();
        // Update fields of the view (in the state variable).
        setFields(state);

        // Get news items to be displayed.
        List<Map<String, String>> newsItems = state.getNewsItems();

        // Handle event where new news items have been fetched.
        if (newsItems != null && state.getRenderNewInfo() != null) {
            // Remove all existing tabs before adding new ones.
            newsTabs.removeAll();

            // Iterate through each of the five news items retrieved by the use case
            for (int i = 0; i < 5; i++) {
                Map<String, String> newsItem = newsItems.get(i);

                JTable table = new JTable();
                table.setPreferredSize(new Dimension(150, 250));
                table.setModel(new TableModel(newsItem));

                // Set a cell renderer for the second column that uses tooltip to show full String values.
                table.getColumnModel().getColumn(1).setCellRenderer(new ClippedPreviewTableCellRenderer());

                newsTabs.addTab(String.format("Article %d", i + 1), icon, table);
                newsTabs.setIconAt(i, icon);
            }

            // Show news item table for first news item (others available in their tabs).
            middlePanel.setVisible(true);
            // Reset the flag indicating there is new info to be rendered to null.
            state.setRenderNewInfo(null);
            // Update state in view model.
            getNewsViewModel.setState(state);
        }

        // Get ticker error (possibly null).
        String tickerError = state.getTickerError();

        // Handle case where there is a ticker error.
        if (tickerError != null) {
            // Show error pop-up message.
            JOptionPane.showMessageDialog(this, tickerError);

            // Reset ticker error flag to null.
            state.setTickerError(null);
            // Update state in view model.
            getNewsViewModel.setState(state);
        }
    }

    /**
     * Updates the fields of the view based on the state.
     *
     * @param state  The GetNewsState representing the current state.
     */
    private void setFields(GetNewsState state) {
        tickerInputField.setText(state.getTicker());
    }

}
