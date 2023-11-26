package view;

// Imports for dashboard
import interface_adapters.Dashboard.DashboardController;
import interface_adapters.Dashboard.DashboardState;
import interface_adapters.Dashboard.DashboardViewModel;
import interface_adapters.ResetBalance.ResetBalanceController;
import interface_adapters.ViewManagerModel;

// Imports to allow dashboard to switch to other views
import interface_adapters.Buy.BuyViewModel;
import interface_adapters.Sell.SellViewModel;
import interface_adapters.GetNews.GetNewsViewModel;
import interface_adapters.ResetBalance.ResetBalanceViewModel;
import interface_adapters.GetTransactionHistory.GetTransactionHistoryViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class DashboardView extends JPanel implements ActionListener, PropertyChangeListener {
    public final String viewName = "dashboard";
    private final DashboardController dashboardController;
    private final DashboardViewModel dashboardViewModel;
    private final BuyViewModel buyViewModel;
    private final SellViewModel sellViewModel;
    private final GetNewsViewModel getNewsViewModel;
    private final ResetBalanceController resetBalanceController;
    private final GetTransactionHistoryViewModel historyViewModel;
    private final ViewManagerModel viewManagerModel;

    // Buttons to be displayed along the left side of the screen for various options the user has.
    final JButton refresh;
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
                         DashboardController dashboardController,
                         BuyViewModel buyViewModel,
                         SellViewModel sellViewModel,
                         GetNewsViewModel getNewsViewModel,
                         ResetBalanceController resetBalanceController,
                         GetTransactionHistoryViewModel historyViewModel,
                         ViewManagerModel viewManagerModel) {

        this.viewManagerModel = viewManagerModel;
        this.dashboardController = dashboardController;
        this.dashboardViewModel = dashboardViewModel;
        this.buyViewModel = buyViewModel;
        this.sellViewModel = sellViewModel;
        this.getNewsViewModel = getNewsViewModel;
        this.resetBalanceController = resetBalanceController;
        this.historyViewModel = historyViewModel;
        this.dashboardViewModel.addPropertyChangeListener(this);

        JLabel title = new JLabel(dashboardViewModel.TITLE_LABEL);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel welcome = new JLabel(dashboardViewModel.WELCOME_LABEL);

        // Create all the buttons in this view.
        JPanel buttons = new JPanel();
        refresh = new JButton(dashboardViewModel.REFRESH_BUTTON_LABEL);
        buttons.add(refresh);
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
        refresh.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(refresh)) {
                            DashboardState currentState = dashboardViewModel.getState();
                            dashboardController.execute();
                            dashboardViewModel.firePropertyChanged();
                        }
                    }
                }
        );

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
                            getNewsViewModel.firePropertyChanged();
                            viewManagerModel.setActiveView(getNewsViewModel.getViewName());
                            viewManagerModel.firePropertyChanged();

                            // TODO: Do we then have to call GetNewsController.execute() to obtain
                            //  the needed information?
                        }
                    }
                }
        );

        reset.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(reset)) {
                            resetBalanceController.execute();
                            dashboardViewModel.firePropertyChanged();

                            // TODO: add reset balance pop-up

                        }
                    }
                }
        );

        history.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(history)) {
                            historyViewModel.firePropertyChanged();
                            viewManagerModel.setActiveView(historyViewModel.getViewName());
                            viewManagerModel.firePropertyChanged();

                            // TODO: Do we then need to call GetTransactionHistoryController.execute()
                            //  to obtain the needed information?
                        }
                    }
                }
        );

        // NOTE: may or may not be necessary
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Make the various display items visible to the user on the display.
        this.add(title);
        this.add(welcome);
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
        dashboardController.execute();
        DashboardState state = dashboardViewModel.getState();

        // TODO: Display user stats, once they are available.

        // TODO: Display table of information about stocks owned by the user.

    }

}