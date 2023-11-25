package view;

import interface_adapters.Buy.BuyController;
import interface_adapters.Buy.BuyState;
import interface_adapters.Buy.BuyViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class BuyView extends JPanel implements ActionListener, PropertyChangeListener {

    public final String viewName = "buy";
    private final BuyViewModel buyViewModel;

    final JTextField amountInputField = new JTextField(15);
    private final JLabel amountErrorField = new JLabel();
    final JTextField tickerInputField = new JTextField(15);
    private final JLabel tickerErrorField = new JLabel();
    final JButton purchase;
    final JButton cancel;
    BuyController buyController;

    /**
     * A window with a title and a JButton.
     */
    public BuyView(BuyController buyController, BuyViewModel buyViewModel) {
        this.buyController = buyController;
        this.buyViewModel = buyViewModel;
        this.buyViewModel.addPropertyChangeListener(this);

        JLabel title = new JLabel("Buy Stock");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        LabelTextPanel amountInfo = new LabelTextPanel(
                new JLabel("Amount"), amountInputField);
        LabelTextPanel tickerInfo = new LabelTextPanel(
                new JLabel("Ticker"), tickerInputField);

        JPanel buttons = new JPanel();
        purchase = new JButton(buyViewModel.PURCHASE_BUTTON_LABEL);
        buttons.add(purchase);
        cancel = new JButton(buyViewModel.CANCEL_BUTTON_LABEL);
        buttons.add(cancel);

        purchase.addActionListener(
                // This creates an anonymous subclass of ActionListener and instantiates it.
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        BuyState buyState = buyViewModel.getState();
                        buyController.execute(buyState.getAmount(), buyState.getTicker());
                    }
                }
        );
        cancel.addActionListener(this);

        amountInputField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                BuyState currentState = buyViewModel.getState();
                currentState.setAmount(amountInputField.getText() + e.getKeyChar());
                buyViewModel.setState(currentState);
            }

            @Override
            public void keyPressed(KeyEvent e) {}

            @Override
            public void keyReleased(KeyEvent e) {}
        });

        tickerInputField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                BuyState currentState = buyViewModel.getState();
                currentState.setTicker(tickerInputField.getText() + e.getKeyChar());
                buyViewModel.setState(currentState);
            }

            @Override
            public void keyPressed(KeyEvent e) {}

            @Override
            public void keyReleased(KeyEvent e) {}
        });
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.add(title);
        this.add(tickerInfo);
        this.add(tickerErrorField);
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
        BuyState state = (BuyState) evt.getNewValue();
        setFields(state);

        String amountError = state.getAmountError();
        if (amountError != null) {
            JOptionPane.showMessageDialog(this, amountError);
            state.setAmountError(null);
            buyViewModel.setState(state);
        }
    }

    private void setFields(BuyState state) {
        amountInputField.setText(state.getAmount());
    }

}
