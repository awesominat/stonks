package view;

import interface_adapters.Buy.BuyViewModel;
import interface_adapters.Dashboard.DashboardState;
import interface_adapters.Dashboard.DashboardViewModel;
import interface_adapters.GetNews.GetNewsViewModel;
import interface_adapters.ResetBalance.ResetBalanceViewModel;
import interface_adapters.Sell.SellViewModel;
import interface_adapters.ViewManagerModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class DashboardView extends JPanel implements ActionListener, PropertyChangeListener {
    public final String viewName = "dashboard";
    private final DashboardViewModel dashboardViewModel;
    private final BuyViewModel buyViewModel;
    private final SellViewModel sellViewModel;
    private final GetNewsViewModel getNewsViewModel;
    private final ResetBalanceViewModel resetBalanceViewModel;
    private final ViewManagerModel viewManagerModel;

    // Buttons to be displayed along the left side of the screen for various options the user has.
    final JButton buy;
    final JButton sell;
    final JButton news;
    final JButton reset;
    final JButton history;

    /**
     * The homepage (dashboard) window with a title, a welcome message, a panel of action buttons, statistics about
     * the user, and information about stocks the user has invested in.
     */
    public DashboardView(DashboardViewModel dashboardViewModel,
                         BuyViewModel buyViewModel,
                         SellViewModel sellViewModel,
                         GetNewsViewModel getNewsViewModel,
                         ResetBalanceViewModel resetBalanceViewModel,
                         ViewManagerModel viewManagerModel) {

        this.viewManagerModel = viewManagerModel;
        this.dashboardViewModel = dashboardViewModel;
        this.buyViewModel = buyViewModel;
        this.sellViewModel = sellViewModel;
        this.getNewsViewModel = getNewsViewModel;
        this.resetBalanceViewModel = resetBalanceViewModel;
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
        history = new JButton(dashboardViewModel.TRANSACTION_HISTORY_BUTTON_LABEL);
        buttons.add(history);

        // Create an anonymous subclass of ActionListener and instantiate it.
        buy.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(buy)) {
                            buyViewModel.firePropertyChanged();
                            viewManagerModel.setActiveView(buyViewModel.getViewName());
                            viewManagerModel.firePropertyChanged();
                        }
                    }
                }
        );

        sell.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(sell)) {
                            sellViewModel.firePropertyChanged();
                            viewManagerModel.setActiveView(sellViewModel.getViewName());
                            viewManagerModel.firePropertyChanged();
                        }
                    }
                }
        );

        news.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(news)) {
                            sellViewModel.firePropertyChanged();
                            viewManagerModel.setActiveView(sellViewModel.getViewName());
                            viewManagerModel.firePropertyChanged();
                        }
                    }
                }
        );

        reset.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(reset)) {
                            resetBalanceViewModel.firePropertyChanged();
                            viewManagerModel.setActiveView(resetBalanceViewModel.getViewName());
                            viewManagerModel.firePropertyChanged();
                        }
                    }
                }
        );

        history.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(history)) {
                            // TODO: implement once Transaction History has either a use case or a view
                            return;
                        }
                    }
                }
        );

        // TODO: Display user stats, once they are available.

        // TODO: Display table of information about stocks owned by the user.

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