package org.example.painters;

import org.example.actors.Actor;
import org.example.IMDB;
import org.example.productions.Production;

import javax.swing.*;
import java.awt.*;

public class CellPainter extends JLabel implements ListCellRenderer {

    CardLayout cardLayout;
    JPanel panelCont;
    public CellPainter(CardLayout cardLayout, JPanel panelCont) {
        this.cardLayout = cardLayout;
        this.panelCont = panelCont;
        setOpaque(true);
    }

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index
            , boolean isSelected, boolean cellHasFocus) {

        ProdIcon prodIcon = (ProdIcon) value;
        setIcon(prodIcon.getImageIcon());
        setBackground(Color.BLACK);
        java.util.List<Production> productions = IMDB.getInstance().getProductions();
        java.util.List<Actor> actors = IMDB.getInstance().getActors();
        if (isSelected) {
            for (Production p : productions) {
                if (((ProdIcon) value).getProduction().equals(p.title)) {
                    cardLayout.show(panelCont, p.title);
                    list.clearSelection();
                    break;
                }
            }
            for (Actor p : actors) {
                if (((ProdIcon) value).getProduction().equals(p.name)) {
                    cardLayout.show(panelCont, p.name);
                    list.clearSelection();
                    break;
                }
            }
        }
        return this;
    }
}
