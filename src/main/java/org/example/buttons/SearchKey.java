package org.example.buttons;

import org.example.*;
import org.example.actors.Actor;
import org.example.productions.Production;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;
import java.util.Map;

public class SearchKey implements KeyListener {
    JTextField searchbar;
    CardLayout cardLayout;
    JPanel panelCont;
    Map<String, ImageIcon> images;
    ImageIcon imdb_logo;

    public SearchKey(JTextField searchbar, CardLayout cardLayout, JPanel panelCont, Map<String, ImageIcon> images, ImageIcon
                     imdb_logo) {
        this.searchbar = searchbar;
        this.cardLayout = cardLayout;
        this.panelCont = panelCont;
        this.images = images;
        this.imdb_logo = imdb_logo;
    }

    @Override
    public void keyTyped(KeyEvent e) { }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            List<Production> productions = IMDB.getInstance().getProductions();
            List<Actor> actors = IMDB.getInstance().getActors();
            int found = 0;
            for (Production p : productions) {
                if (searchbar.getText().equals(p.title)) {
                    searchbar.setText("");
                    cardLayout.show(panelCont, p.title);
                    found = 1;
                    break;
                }
            }
            SearchButton.search_result(actors, found, searchbar, cardLayout, panelCont, imdb_logo, images);
        }
    }


    @Override
    public void keyReleased(KeyEvent e) { }
}
