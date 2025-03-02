package org.example.buttons;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class PasswordBox extends JCheckBox implements ItemListener {
    JPasswordField passwordField;
    public PasswordBox(JPasswordField passwordField) {
        this.passwordField = passwordField;
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
            passwordField.setEchoChar((char) 0);
        }
        else {
            passwordField.setEchoChar('\u2022');
        }
    }
}
