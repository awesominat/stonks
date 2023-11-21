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

    /**
     * Text fields, labels, and buttons
     */
    final JTextField ticker = new JTextField(4);
    final JButton search;
    final JButton back;
    private final GetNewsController getNewsController;

    public GetNewsView(GetNewsViewModel getNewsViewModel,
                       GetNewsController getNewsController,
                       ViewManagerModel viewManagerModel,
                       DashboardViewModel dashboardViewModel) {
        this.getNewsController = getNewsController;
        this.viewManagerModel = viewManagerModel;
        this.dashboardViewModel = dashboardViewModel;
        this.getNewsViewModel = getNewsViewModel;
        this.getNewsViewModel.addPropertyChangeListener(this);

        JLabel title = new JLabel("Get Company News Screen");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel title = new JLabel("Sell Screen");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    // TODO fill the rest in

}
