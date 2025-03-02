package org.example.buttons;

import org.example.*;
import org.example.actors.Actor;
import org.example.painters.CellPainterSearch;
import org.example.painters.ProdIcon;
import org.example.productions.Production;
import org.example.swing.MenuImdb;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

public class SearchButton implements ActionListener {

    JButton searchButton;
    JPanel panelCont;
    CardLayout cardLayout;
    JTextField searchbar;
    Map<String, ImageIcon> images;
    ImageIcon imdb_logo;

    public SearchButton(JButton searchButton, JPanel panelCont, CardLayout cardLayout, JTextField searchbar,
                        Map<String, ImageIcon> images, ImageIcon imdb_logo) {
        this.searchButton = searchButton;
        this.panelCont = panelCont;
        this.cardLayout = cardLayout;
        this.searchbar = searchbar;
        this.images = images;
        this.imdb_logo = imdb_logo;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == searchButton) {
            List<Production> productions = IMDB.getInstance().getProductions();
            List<Actor> actors = IMDB.getInstance().getActors();
            int found = 0;
            for (Production p : productions) {
                if (searchbar.getText().equals(p.title)) {
                    cardLayout.show(panelCont, p.title);
                    searchbar.setText("");
                    found = 1;
                    break;
                }
            }
            search_result(actors, found, searchbar, cardLayout, panelCont, imdb_logo, images);
        }
    }

    static void search_result(List<Actor> actors, int found, JTextField searchbar, CardLayout cardLayout, JPanel panelCont,
                              ImageIcon imdbLogo, Map<String, ImageIcon> images) {
        for (Actor a : actors) {
            if (searchbar.getText().equals(a.getName())) {
                cardLayout.show(panelCont, a.getName());
                searchbar.setText("");
                found = 1;
                break;
            }
        }
        if (found == 0) {
            DefaultListModel topPicks= new DefaultListModel();

            JPanel list_search = MenuImdb.createPanel(new Font("Calibri", Font.PLAIN, 15), imdbLogo,
                    images, cardLayout, panelCont);
            list_search.setBackground(Color.black);
            for (Map.Entry<String, ImageIcon> entry : images.entrySet()) {
                if (entry.getKey().startsWith(searchbar.getText())) {
                    ProdIcon prodIcon = new ProdIcon(entry.getKey(), entry.getValue());
                    topPicks.addElement(prodIcon);
                }
            }
            if (!topPicks.isEmpty()) {
                if (topPicks.size() == 1) {
                    topPicks.addElement(new ProdIcon("", new ImageIcon()));
                }
                CellPainterSearch cellPainter = new CellPainterSearch(cardLayout, panelCont);
                JList jList = new JList<>(topPicks);
                jList.setCellRenderer(cellPainter);
                jList.setFixedCellWidth(600);
                jList.setFixedCellHeight(700);
                jList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                jList.setVisibleRowCount(1);
                jList.setLayoutOrientation(JList.VERTICAL_WRAP);

                JScrollPane jp = new JScrollPane(jList, ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER,
                        ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
                jp.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 15));
                jp.getHorizontalScrollBar().setBackground(Color.BLACK);
                jp.setBackground(Color.black);
                jp.setForeground(Color.BLACK);
                list_search.add(jp, BorderLayout.CENTER);
                panelCont.add(list_search, "searched_results");
                cardLayout.show(panelCont, "searched_results");
                searchbar.setText("");
            }
            else {
                searchbar.setText("");
                cardLayout.show(panelCont, "Not found");
            }
        }
    }
}
