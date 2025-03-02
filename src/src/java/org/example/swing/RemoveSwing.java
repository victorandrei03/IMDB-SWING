package org.example.swing;

import org.example.*;
import org.example.actors.Actor;
import org.example.painters.ProdIcon;
import org.example.productions.Movie;
import org.example.productions.Production;
import org.example.productions.Series;
import org.example.users.Staff;
import org.example.users.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RemoveSwing<T extends Comparable<T>> extends JPanel {
    public JComboBox choose_rem;
    JButton remove;
    User<T> user;
    Map<String, Component> child_panels;
    Map<String, ProdIcon> prod_icons;
    Map<String, Map<String, Component>> all_components;
    JButton back_button;
    JComboBox obj_type;
    public RemoveSwing(Map<String, Map<String, Component>> all_components, Map<String, ProdIcon> prod_icons,
                       Map<String, Component> child_panels, User<T> user, CardLayout cardLayout, JPanel panelCont,
                       Map<String, ImageIcon> images, Map<String, ProdIcon> fav_icons, JComboBox user_contributions,
                       JComboBox yours_favorites) {
        this.all_components = all_components;
        this.child_panels = child_panels;
        this.prod_icons = prod_icons;
        this.user = user;
        choose_rem = new JComboBox<>();
        back_button = new JButton();
        ImageIcon back_img = new ImageIcon("src/main/java/org/example/images/back.jpg");
        back_img.setImage(back_img.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH));
        back_button.setIcon(back_img);
        back_button.setBounds(30, 30, 40, 40);
        back_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                choose_rem.setVisible(false);
                cardLayout.show(panelCont, "3");
            }
        });
        this.add(back_button);
        remove = new JButton("Remove");
        Font calibri = new Font("Calibri", Font.PLAIN, 18);
        Color mustard = new Color(219, 165, 5);

        String[] objects = {"-", "Movie", "Series", "Actor"};
        obj_type = new JComboBox<>(objects);
        init_comp(obj_type, calibri, mustard, 400, 75, 300, 30);

        init_comp(choose_rem, calibri, mustard, 400, 115, 300, 30);
        init_comp(remove, calibri, mustard, 400, 155, 300, 30);
        JLabel mess = new JLabel("In order to remove an item, select it first. If you don't have any items to select" +
                ", you may not have contributed at the system.");
        init_comp(mess, calibri, Color.RED, 300, 205, 500, 25);
        obj_type.setSelectedItem("-");
        mess.setVisible(false);
        choose_rem.setVisible(false);

        obj_type.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (obj_type.getSelectedItem().equals("Actor")) {
                    List<String> obj_count = new ArrayList<>();
                    for (T o : ((Staff<T>) user).getContributions()) {
                        if (o instanceof Actor) {
                            obj_count.add(((Actor)o).getName());
                        }
                    }
                    String[] contributions_st = new String[obj_count.size() + 1];
                    contributions_st[0] = "-";
                    if (!obj_count.isEmpty()) {
                        for (int i = 1; i <= obj_count.size(); i++) {
                            contributions_st[i] = obj_count.get(i - 1);
                        }
                    }

                    DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(contributions_st);
                    choose_rem.setModel(model);
                    choose_rem.setVisible(true);

                }
                else if (obj_type.getSelectedItem().equals("Movie")) {
                    List<String> obj_count = new ArrayList<>();
                    for (T o : ((Staff<T>) user).getContributions()) {
                        if (o instanceof Movie) {
                            obj_count.add(((Movie)o).getTitle());
                        }
                    }
                    String[] contributions_st = new String[obj_count.size() + 1];
                    contributions_st[0] = "-";
                    if (!obj_count.isEmpty()) {
                        for (int i = 1; i <= obj_count.size(); i++) {
                            contributions_st[i] = obj_count.get(i - 1);
                        }
                    }
                    DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(contributions_st);
                    choose_rem.setModel(model);
                    choose_rem.setVisible(true);
                }
                else if (obj_type.getSelectedItem().equals("Series")) {
                    List<String> obj_count = new ArrayList<>();
                    for (T o : ((Staff<T>) user).getContributions()) {
                        if (o instanceof Series) {
                            obj_count.add(((Series)o).getTitle());
                        }
                    }
                    String[] contributions_st = new String[obj_count.size() + 1];
                    contributions_st[0] = "-";
                    if (!obj_count.isEmpty()) {
                        for (int i = 1; i <= obj_count.size(); i++) {
                            contributions_st[i] = obj_count.get(i - 1);
                        }
                    }
                    DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(contributions_st);
                    choose_rem.setModel(model);
                    choose_rem.setVisible(true);
                }
            }
        });

        remove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (obj_type.getSelectedItem().equals("Actor")) {
                    String res = (String) choose_rem.getSelectedItem();
                    if (res.equals("-") && e.getSource() == remove) {
                        mess.setVisible(true);
                    }
                    else if (e.getSource() == remove) {
                        List<Actor> actorList = IMDB.getInstance().getActors();
                        Actor actor = ParseInput.get_actor_byname(res);
                        actorList.remove(actor);
                        child_panels.remove(res);
                        all_components.remove(res);
                        if (prod_icons.containsKey(res)) {
                            ProdIcon prodIcon = prod_icons.get(res);
                            prodIcon.setProduction("removed");
                        }
                        images.remove(res);
                        mess.setVisible(false);
                        ((Staff<T>)user).getContributions().remove(actor);
                        List<String> obj_count = new ArrayList<>();
                        for (T o : ((Staff<T>) user).getContributions()) {
                            if (o instanceof Actor) {
                                obj_count.add(((Actor)o).getName());
                            }
                        }
                        String[] contributions_st = new String[obj_count.size() + 1];
                        contributions_st[0] = "-";
                        if (!obj_count.isEmpty()) {
                            for (int i = 1; i <= obj_count.size(); i++) {
                                contributions_st[i] = obj_count.get(i - 1);
                            }
                        }

                        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(contributions_st);
                        choose_rem.setModel(model);
                        choose_rem.setVisible(false);

                        String[] contributions_update = new String[((Staff)user).getContributions().size() + 1];
                        contributions_update[0] = "-";
                        int i = 1;
                        if (!((Staff)user).getContributions().isEmpty()) {
                            for (Object o : ((Staff)user).getContributions()) {
                                if (o instanceof Actor) {
                                    contributions_update[i] = ((Actor)o).name;
                                }
                                else {
                                    contributions_update[i] = ((Production)o).title;
                                }
                                i++;
                            }
                        }

                        if (user.getFavorites().contains(actor)) {
                            user.getFavorites().remove(actor);
                            String[] fav_str = new String[user.getFavorites().size() + 1];
                            fav_str[0] = "-";
                            int k = 1;
                            for (Object o : user.getFavorites()) {
                                if (o instanceof Actor) {
                                    fav_str[k] = ((Actor)o).name;
                                }
                                else {
                                    fav_str[k] = ((Production)o).title;
                                }
                                k++;
                            }
                            DefaultComboBoxModel<String> model2 = new DefaultComboBoxModel<>(fav_str);
                            yours_favorites.setModel(model2);
                            yours_favorites.setSelectedItem("-");
                        }

                        DefaultComboBoxModel<String> model_2 = new DefaultComboBoxModel<>(contributions_update);
                        user_contributions.setModel(model_2);
                        cardLayout.show(panelCont, "3");

                    }
                }
                else if (obj_type.getSelectedItem().equals("Movie")) {
                    String res = (String) choose_rem.getSelectedItem();
                    if (res.equals("-") && e.getSource() == remove) {
                        mess.setVisible(true);
                    }
                    else if (e.getSource() == remove) {
                        List<Production> productions = IMDB.getInstance().getProductions();
                        Production prod = ParseInput.get_production_byname(res);
                        productions.remove(prod);
                        ((Staff<T>)user).getContributions().remove(prod);
                        child_panels.remove(res);
                        all_components.remove(res);
                        if (prod_icons.containsKey(res)) {
                            ProdIcon prodIcon = prod_icons.get(res);
                            prodIcon.setProduction("removed");
                        }
                        if (fav_icons.containsKey(res)) {
                            ProdIcon prodIcon = fav_icons.get(res);
                            prodIcon.setProduction("removed");
                            MenuImdb.update_lists_menu(images, fav_icons);
                        }
                        images.remove(res);
                        mess.setVisible(false);
                        List<String> obj_count = new ArrayList<>();
                        for (T o : ((Staff<T>) user).getContributions()) {
                            if (o instanceof Movie) {
                                obj_count.add(((Movie)o).getTitle());
                            }
                        }
                        String[] contributions_st = new String[obj_count.size() + 1];
                        contributions_st[0] = "-";
                        if (!obj_count.isEmpty()) {
                            for (int i = 1; i <= obj_count.size(); i++) {
                                contributions_st[i] = obj_count.get(i - 1);
                            }
                        }

                        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(contributions_st);
                        choose_rem.setModel(model);

                        String[] contributions_update = new String[((Staff)user).getContributions().size() + 1];
                        contributions_update[0] = "-";
                        int i = 1;
                        if (!((Staff)user).getContributions().isEmpty()) {
                            for (Object o : ((Staff)user).getContributions()) {
                                if (o instanceof Actor) {
                                    contributions_update[i] = ((Actor)o).name;
                                }
                                else {
                                    contributions_update[i] = ((Production)o).title;
                                }
                                i++;
                            }
                        }

                        if (user.getFavorites().contains(prod)) {
                            user.getFavorites().remove(prod);
                            String[] fav_str = new String[user.getFavorites().size() + 1];
                            fav_str[0] = "-";
                            int k = 1;
                            for (Object o : user.getFavorites()) {
                                if (o instanceof Actor) {
                                    fav_str[k] = ((Actor)o).name;
                                }
                                else {
                                    fav_str[k] = ((Production)o).title;
                                }
                                k++;
                            }
                            DefaultComboBoxModel<String> model2 = new DefaultComboBoxModel<>(fav_str);
                            yours_favorites.setModel(model2);
                            yours_favorites.setSelectedItem("-");
                        }

                        DefaultComboBoxModel<String> model_2 = new DefaultComboBoxModel<>(contributions_update);
                        user_contributions.setModel(model_2);

                        MenuImdb.update_lists_menu(images, prod_icons);
                        choose_rem.setVisible(false);
                        cardLayout.show(panelCont, "3");

                    }
                }
                else if (obj_type.getSelectedItem().equals("Series")) {
                    String res = (String) choose_rem.getSelectedItem();
                    if (res.equals("-") && e.getSource() == remove) {
                        mess.setVisible(true);
                    }
                    else if (e.getSource() == remove) {
                        List<Production> productions = IMDB.getInstance().getProductions();
                        Production prod = ParseInput.get_production_byname(res);
                        productions.remove(prod);
                        ((Staff<T>)user).getContributions().remove(prod);
                        child_panels.remove(res);
                        all_components.remove(res);
                        if (prod_icons.containsKey(res)) {
                            ProdIcon prodIcon = prod_icons.get(res);
                            prodIcon.setProduction("removed");
                        }
                        if (fav_icons.containsKey(res)) {
                            ProdIcon prodIcon = fav_icons.get(res);
                            prodIcon.setProduction("removed");
                            MenuImdb.update_lists_menu(images, fav_icons);
                        }
                        images.remove(res);
                        mess.setVisible(false);
                        List<String> obj_count = new ArrayList<>();
                        for (T o : ((Staff<T>) user).getContributions()) {
                            if (o instanceof Series) {
                                obj_count.add(((Series)o).getTitle());
                            }
                        }
                        String[] contributions_st = new String[obj_count.size() + 1];
                        contributions_st[0] = "-";
                        if (!obj_count.isEmpty()) {
                            for (int i = 1; i <= obj_count.size(); i++) {
                                contributions_st[i] = obj_count.get(i - 1);
                            }
                        }

                        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(contributions_st);
                        choose_rem.setModel(model);

                        String[] contributions_update = new String[((Staff)user).getContributions().size() + 1];
                        contributions_update[0] = "-";
                        int i = 1;
                        if (!((Staff)user).getContributions().isEmpty()) {
                            for (Object o : ((Staff)user).getContributions()) {
                                if (o instanceof Actor) {
                                    contributions_update[i] = ((Actor)o).name;
                                }
                                else {
                                    contributions_update[i] = ((Production)o).title;
                                }
                                i++;
                            }
                        }

                        if (user.getFavorites().contains(prod)) {
                            user.getFavorites().remove(prod);
                            String[] fav_str = new String[user.getFavorites().size() + 1];
                            fav_str[0] = "-";
                            int k = 1;
                            for (Object o : user.getFavorites()) {
                                if (o instanceof Actor) {
                                    fav_str[k] = ((Actor)o).name;
                                }
                                else {
                                    fav_str[k] = ((Production)o).title;
                                }
                                k++;
                            }
                            DefaultComboBoxModel<String> model2 = new DefaultComboBoxModel<>(fav_str);
                            yours_favorites.setModel(model2);
                            yours_favorites.setSelectedItem("-");
                        }

                        DefaultComboBoxModel<String> model_2 = new DefaultComboBoxModel<>(contributions_update);
                        user_contributions.setModel(model_2);

                        MenuImdb.update_lists_menu(images, prod_icons);
                        choose_rem.setVisible(false);
                        cardLayout.show(panelCont, "3");
                    }
                }
            }
        });

        this.add(choose_rem);
        this.add(remove);
        this.add(obj_type);

        setLayout(null);
        setVisible(true);
        setBackground(Color.BLACK);
    }
    public void init_comp(Component component, Font calibri, Color grey, int x, int y, int width, int height) {
        component.setBounds(x, y, width, height);
        component.setFont(calibri);
        if (!(component instanceof JTextField)) {
            component.setBackground(Color.black);
        }
        component.setForeground(grey);
    }
}
