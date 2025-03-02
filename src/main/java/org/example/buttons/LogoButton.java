package org.example.buttons;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LogoButton implements ActionListener {
    JButton logo_each_panel;
    CardLayout cardLayout;
    JPanel panelCont;

    public LogoButton(JButton logo_each_panel, CardLayout cardLayout, JPanel panelCont) {
        this.logo_each_panel = logo_each_panel;
        this.cardLayout = cardLayout;
        this.panelCont = panelCont;
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == logo_each_panel) {
            cardLayout.show(panelCont, "3");
        }
    }
}
