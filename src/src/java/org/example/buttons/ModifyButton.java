package org.example.buttons;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

public class ModifyButton implements ActionListener {
    Map<String, Map<String, Component>> all_components;
    JButton modify_button;
    CardLayout cardLayout;
    JPanel panelCont;

    public ModifyButton(Map<String, Map<String, Component>> all_components, JButton modify_button, CardLayout cardLayout, JPanel panelCont) {
        this.all_components = all_components;
        this.modify_button = modify_button;
        this.cardLayout = cardLayout;
        this.panelCont = panelCont;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
