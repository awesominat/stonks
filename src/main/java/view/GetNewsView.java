package view;

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
    final JTextField tickerIn = new JTextField(4);
    final JButton search;
    final JButton back;

    public GetNewsView(GetNewsViewModel getNewsViewModel,
                       GetNewsController getNewsController,
                       ViewManagerModel viewManagerModel,
                       DashboardViewModel dashboardViewModel) {

        this.getNewsController = getNewsController;
        this.viewManagerModel = viewManagerModel;
        this.dashboardViewModel = dashboardViewModel;
        this.getNewsViewModel = getNewsViewModel;
        this.getNewsViewModel.addPropertyChangeListener(this);

        JLabel title = new JLabel(getNewsViewModel.TITLE_LABEL);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setAlignmentY(Component.TOP_ALIGNMENT);

        // Create stock search bar.
        LabelTextPanel tickerField = new LabelTextPanel(
                new JLabel("Stock ticker"), tickerIn);
        tickerField.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel back_panel = new JPanel();
        back = new JButton(getNewsViewModel.BACK_BUTTON_LABEL);
        back_panel.add(back);

        JPanel search_panel = new JPanel();
        search = new JButton(getNewsViewModel.SEARCH_BUTTON_LABEL);
        search_panel.add(search);

        back.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(back)) {
                            dashboardViewModel.firePropertyChanged();
                            viewManagerModel.setActiveView(dashboardViewModel.getViewName());
                            viewManagerModel.firePropertyChanged();
                        }
                    }
                }
        );

        search.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(search)) {
                            GetNewsState currentState = getNewsViewModel.getState();
                            getNewsController.execute(currentState.getTicker());
                        }
                    }
                }
        );

        tickerField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                GetNewsState currentState = getNewsViewModel.getState();
                currentState.setTicker(tickerIn.getText() + e.getKeyChar());
                getNewsViewModel.setState(currentState);
            }

            @Override
            public void keyPressed(KeyEvent e) {}

            @Override
            public void keyReleased(KeyEvent e) {}
        });
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.add(title);
        this.add(tickerField);
        this.add(back_panel);
        this.add(search_panel);
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
    }

    private void setFields(GetNewsState state) {}

}
