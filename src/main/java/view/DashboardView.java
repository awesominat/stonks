package view;

import interface_adapters.Dashboard.DashboardState;
import interface_adapters.Dashboard.DashboardViewModel;
import interface_adapters.ViewManagerModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class DashboardView extends JPanel implements ActionListener, PropertyChangeListener {
    public final String viewName = "Dashboard";
    private final DashboardViewModel dashboardViewModel;
    private final ViewManagerModel viewManagerModel;

    // Buttons to be displayed along the left side of the screen for various options the user has.
    final JButton buy;
    final JButton sell;
    final JButton news;
    final JButton reset;
    final JButton quit;
    final JButton history;

    /**
     * The homepage (dashboard) window with a title, a welcome message, a panel of action buttons, statistics about
     * the user, and information about stocks the user has invested in.
     */
    public DashboardView(DashboardViewModel dashboardViewModel, ViewManagerModel viewManagerModel) {

        this.viewManagerModel = viewManagerModel;
        this.dashboardViewModel = dashboardViewModel;
        this.dashboardViewModel.addPropertyChangeListener(this);

        JLabel title = new JLabel(dashboardViewModel.TITLE_LABEL);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel welcome = new JLabel(dashboardViewModel.WELCOME_LABEL);

        // Create all the buttons in this view.
        JPanel buttons = new JPanel();
        buy = new JButton(dashboardViewModel.PURCHASE_BUTTON_LABEL);
        buttons.add(buy);
        sell = new JButton(dashboardViewModel.SELL_BUTTON_LABEL);
        buttons.add(sell);
        news = new JButton(dashboardViewModel.NEWS_BUTTON_LABEL);
        buttons.add(news);
        reset = new JButton(dashboardViewModel.RESET_BUTTON_LABEL);
        buttons.add(reset);
        quit = new JButton(dashboardViewModel.QUIT_BUTTON_LABEL);
        buttons.add(quit);
        history = new JButton(dashboardViewModel.TRANSACTION_HISTORY_BUTTON_LABEL);
        buttons.add(history);

        // Create an anonymous subclass of ActionListener and instantiate it.
        buy.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(buy)) {
                            // TODO: Note that some viewNames are capitalized and some are not. Check this carefully!
                            // (Also some are longer/multi-word while others are single-word).
                            viewManagerModel.setActiveView("Buy");
                            viewManagerModel.firePropertyChanged();
                        }
                    }
                }
        );

        sell.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(sell)) {
                            viewManagerModel.setActiveView("sell");
                            viewManagerModel.firePropertyChanged();
                        }
                    }
                }
        );

        news.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(news)) {
                            viewManagerModel.setActiveView("Get Company News");
                            viewManagerModel.firePropertyChanged();
                        }
                    }
                }
        );

        reset.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(reset)) {
                            viewManagerModel.setActiveView("Reset Balance");
                            viewManagerModel.firePropertyChanged();
                        }
                    }
                }
        );

        quit.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(quit)) {
                            // TODO: Implement quit.
                            return;
                        }
                    }
                }
        );

        history.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(history)) {
                            viewManagerModel.setActiveView("Transaction History");
                            viewManagerModel.firePropertyChanged();
                        }
                    }
                }
        );

        // TODO: Display user stats.

        // TODO: Display table of information about stocks owned by the user.

        // Pay attention to the input from the buttons in this view.
        buy.addActionListener(this);
        sell.addActionListener(this);
        news.addActionListener(this);
        reset.addActionListener(this);
        quit.addActionListener(this);

        // NOTE: may or may not be necessary
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Make the various display items visible to the user on the display.
        this.add(title);
        this.add(buttons);
    }

    /**
     * React to a button click that results in evt.
     */
    public void actionPerformed(ActionEvent evt) {
        System.out.println("Click " + evt.getActionCommand());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
//        String event_name = evt.getPropertyName();
//        DashboardState state = (DashboardState) evt.getNewValue();
        // TODO: Determine whether this method needs to be implemented at all.
        return;
    }

}