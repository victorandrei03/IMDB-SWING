package org.example.swing;

import org.example.actors.Actor;
import org.example.ParseInput;
import org.example.productions.Production;
import org.example.users.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.TreeSet;

public class AddFavoritesSwin extends JPanel {
    public JComboBox yours_favorites;
    JButton add_favorites;
    JButton remove_favorites;
    JLabel favorites_label;
    JLabel mess;
    JButton back_button;
    JTextField favorites;
    public AddFavoritesSwin(CardLayout cardLayout, JPanel panelCont, User user) {

        add_favorites = new JButton("Add to favorites");
        remove_favorites = new JButton("Remove favorites");
        favorites_label = new JLabel("Favorite: ");
        back_button = new JButton();
        favorites = new JTextField();
        mess = new JLabel();

        back_button = new JButton();
        Color mustard = new Color(219, 165, 5);
        Font calibri = new Font("Calibri", Font.PLAIN, 15);

        favorites_label.setBounds(300, 300, 100, 20);
        favorites.setBounds(440, 300, 400, 20);
        add_favorites.setBounds(300, 350, 150, 20);
        remove_favorites.setBounds(300, 400, 150, 20);
        mess.setBounds(300, 500, 400, 20);
        set_comp(mess, mustard, calibri);
        mess.setVisible(false);

        if (user.getFavorites() == null) {
            user.setFavorites(new TreeSet<>());
        }
        String[] fav_str = new String[user.getFavorites().size() + 1];
        fav_str[0] = "-";
        int i = 1;
        for (Object o : user.getFavorites()) {
            if (o instanceof Actor) {
                fav_str[i] = ((Actor)o).name;
            }
            else {
                fav_str[i] = ((Production)o).title;
            }
        }
        yours_favorites = new JComboBox<>(fav_str);
        yours_favorites.setBounds(300, 200, 700, 30);

        yours_favorites.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!yours_favorites.getSelectedItem().equals("-")) {
                    cardLayout.show(panelCont, (String) yours_favorites.getSelectedItem());
                }
            }
        });

        add_favorites.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == add_favorites) {
                    Production p = ParseInput.get_production_byname(favorites.getText());
                    int found = 0;
                    if (p == null) {
                        Actor a = ParseInput.get_actor_byname(favorites.getText());
                        if (a == null) {
                            mess.setVisible(true);
                            mess.setText("The production/actor you are looking for is not in the system!");
                            mess.setForeground(Color.RED);
                        }
                        else {
                            mess.setVisible(true);
                            mess.setText("Actor added to your favorites list");
                            favorites.setText("");
                            found = 1;
                            user.getFavorites().add(a);
                            mess.setForeground(Color.GREEN);
                        }
                    }
                    else {
                        found = 1;
                        mess.setVisible(true);
                        mess.setText("Production added to your favorites list");
                        favorites.setText("");
                        user.getFavorites().add(p);
                        mess.setForeground(Color.GREEN);
                    }
                    if (found == 1) {
                        String[] fav_str = new String[user.getFavorites().size() + 1];
                        fav_str[0] = "-";
                        int i = 1;
                        for (Object o : user.getFavorites()) {
                            if (o instanceof Actor) {
                                fav_str[i] = ((Actor)o).name;
                            }
                            else {
                                fav_str[i] = ((Production)o).title;
                            }
                            i++;
                        }
                        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(fav_str);
                        yours_favorites.setModel(model);
                        yours_favorites.setSelectedItem("-");
                    }
                }
            }
        });
        remove_favorites.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == remove_favorites) {
                    Production p = null;
                    for (Object o : user.getFavorites()) {
                        if (o instanceof Production) {
                            if (((Production) o).title.equals(favorites.getText())) {
                                p = (Production) o;
                                break;
                            }
                        }
                    }
                    int found = 0;
                    if (p == null) {
                        Actor a = null;
                        for (Object o : user.getFavorites()) {
                            if (o instanceof Actor) {
                                if (((Actor) o).name.equals(favorites.getText())) {
                                    a = (Actor) o;
                                    break;
                                }
                            }
                        }
                        if (a == null) {
                            mess.setVisible(true);
                            mess.setText("The production/actor you are looking for is not in the system!");
                        }
                        else {
                            mess.setVisible(true);
                            mess.setText("Actor removed from your favorites list");
                            mess.setForeground(Color.GREEN);
                            favorites.setText("");
                            found = 1;
                            user.getFavorites().remove(a);
                        }
                    }
                    else {
                        found = 1;
                        mess.setVisible(true);
                        mess.setText("Production removed from your favorites list");
                        mess.setForeground(Color.GREEN);
                        favorites.setText("");
                        user.getFavorites().remove(p);
                    }
                    if (found == 1) {
                        String[] fav_str = new String[user.getFavorites().size() + 1];
                        fav_str[0] = "-";
                        int i = 1;
                        for (Object o : user.getFavorites()) {
                            if (o instanceof Actor) {
                                fav_str[i] = ((Actor)o).name;
                            }
                            else {
                                fav_str[i] = ((Production)o).title;
                            }
                            i++;
                        }

                        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(fav_str);
                        yours_favorites.setModel(model);
                        yours_favorites.setSelectedItem("-");
                    }
                }
            }
        });

        set_comp(yours_favorites, mustard, calibri);
        set_comp(favorites_label, mustard, calibri);
        set_comp(favorites, mustard, calibri);
        set_comp(add_favorites, mustard, calibri);
        set_comp(remove_favorites, mustard, calibri);
        add_favorites.setBackground(Color.BLACK);
        remove_favorites.setBackground(Color.BLACK);

        ImageIcon back_img = new ImageIcon("src/main/java/org/example/images/back.jpg");
        back_img.setImage(back_img.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH));
        back_button.setIcon(back_img);
        back_button.setBounds(30, 30, 40, 40);
        back_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(panelCont, "3");
            }
        });

        add(yours_favorites);
        add(add_favorites);
        add(remove_favorites);
        add(favorites_label);
        add(back_button);
        add(favorites);
        add(mess);
        setVisible(true);
        setBackground(Color.BLACK);
        setLayout(null);
    }
    public void set_comp(Component component, Color mustard, Font calibri) {
        component.setBackground(Color.black);
        component.setFont(calibri);
        component.setForeground(mustard);
    }
}
