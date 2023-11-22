package view;

import interface_adapters.ResetBalance.ResetBalanceViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class ResetBalanceView extends JPanel implements ActionListener, PropertyChangeListener {

    public final String viewName = "reset";
    private final ResetBalanceViewModel resetBalanceViewModel;
    private final JLabel statusErrorField = new JLabel();
    ;

    final JButton reset;

    /**
     * A window with a title and a JButton.
     */
    public ResetBalanceView(ResetBalanceViewModel resetBalanceViewModel) {
        this.resetBalanceViewModel = resetBalanceViewModel;
        this.resetBalanceViewModel.addPropertyChangeListener(this);

        JLabel title = new JLabel(resetBalanceViewModel.TITLE_LABEL);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel buttons = new JPanel();
        reset = new JButton(resetBalanceViewModel.RESET_BUTTON_LABEL);
        buttons.add(reset);

        reset.addActionListener(this);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.add(title);
        this.add(statusErrorField);
        this.add(buttons);
    }

    public void actionPerformed(ActionEvent evt) {
        System.out.println("Click " + evt.getActionCommand());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }
}
