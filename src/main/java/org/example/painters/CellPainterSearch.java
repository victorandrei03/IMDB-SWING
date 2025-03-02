package org.example.painters;

import org.example.actors.Actor;
import org.example.IMDB;
import org.example.productions.Production;

import javax.swing.*;
import java.awt.*;

public class CellPainterSearch extends JLabel implements ListCellRenderer {

    CardLayout cardLayout;
    JPanel panelCont;
    public CellPainterSearch(CardLayout cardLayout, JPanel panelCont) {
        this.cardLayout = cardLayout;
        this.panelCont = panelCont;
        setOpaque(true);
    }

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index
            , boolean isSelected, boolean cellHasFocus) {

        ProdIcon prodIcon = (ProdIcon) value;
        setText(prodIcon.getProduction());
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
        setFont(new Font("Arial", Font.PLAIN, 17));
        setForeground(new Color(211, 211, 211));
        setHorizontalTextPosition(JLabel.CENTER);
        setVerticalTextPosition(JLabel.BOTTOM);
        setIconTextGap(50);
        return this;
    }
}
