package view;

import interface_adapters.Buy.BuyViewModel;
import interface_adapters.GetNews.GetNewsState;
import interface_adapters.GetNews.GetNewsViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class GetNewsView extends JPanel implements ActionListener, PropertyChangeListener {
    public final String viewName = "Get Company News";
    private final GetNewsViewModel getNewsViewModel;

    public GetNewsView(GetNewsViewModel getNewsViewModel) {
        this.getNewsViewModel = getNewsViewModel;
    }

    public void actionPerformed(ActionEvent evt){
        System.out.println("Click " + evt.getActionCommand());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        GetNewsState state = (GetNewsState) evt.getNewValue();
    }

}
