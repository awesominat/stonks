package view;

import interface_adapters.Sell.SellState;
import interface_adapters.Sell.SellViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class SellView extends JPanel implements ActionListener, PropertyChangeListener {

    public final String viewName = "Sell Stock";
    private final SellViewModel sellViewModel;

    /**
     * The username chosen by the user
     */
    final JTextField amountInputField = new JTextField(15);
    private final JLabel amountErrorField = new JLabel();
    /**
     * The password
     */
    final JButton purchase;
    final JButton cancel;

    /**
     * A window with a title and a JButton.
     */
    public SellView(SellViewModel sellViewModel) {
        this.sellViewModel = sellViewModel;
        this.sellViewModel.addPropertyChangeListener(this);

        JLabel title = new JLabel("Sell Stock");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        LabelTextPanel amountInfo = new LabelTextPanel(
                new JLabel("Username"), amountInputField);

        JPanel buttons = new JPanel();
        purchase = new JButton(sellViewModel.PURCHASE_BUTTON_LABEL);
        buttons.add(purchase);
        cancel = new JButton(sellViewModel.CANCEL_BUTTON_LABEL);
        buttons.add(cancel);

        purchase.addActionListener(this);
        cancel.addActionListener(this);

        amountInfo.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                SellState currentState = sellViewModel.getState();
                currentState.setAmount(amountInputField.getText());
                sellViewModel.setState(currentState);
            }

            @Override
            public void keyPressed(KeyEvent e) {}

            @Override
            public void keyReleased(KeyEvent e) {}
        });
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.add(title);
        this.add(amountInfo);
        this.add(amountErrorField);
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
        SellState state = (SellState) evt.getNewValue();
        setFields(state);
    }

    private void setFields(SellState state) {
        amountInputField.setText(state.getAmount());
    }

}
