package org.example.swing;

import org.example.*;
import org.example.actors.Actor;
import org.example.actors.Performance;
import org.example.painters.ProdIcon;
import org.example.productions.*;
import org.example.users.Staff;
import org.example.users.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UpdatePanel extends JPanel {
    JComboBox chosen_obj;
    JComboBox choose_type;
    JTextField name;
    JLabel name_label;
    JTextField directors;
    JLabel directors_label;
    JTextField actors;
    JLabel actors_label;
    List<JCheckBox> genres;
    JTextField plot;
    JLabel plot_label;
    JTextField seasons;
    JLabel seasons_label;
    JTextField episode;
    JLabel episode_label;
    JButton add_actor;
    JButton add_director;
    JButton add_season;
    JButton add_episode;
    JButton add_episode_duration;
    JPanel this_panel;
    JButton add_name;
    JButton add_plot;
    JLabel ep_duration_label;
    JTextField ep_duration;
    JButton modify;
    CardLayout cardLayout;
    JPanel panelCont;
    JTextField searched;
    JButton searched_but;
    JLabel searched_label;
    JLabel not_found;
    JLabel performance_label;
    JTextField performance;
    JButton performance_but;
    Map<String, Map<String, Component>> all_components;
    Map<String, Component> child_panels;
    Map<String, ImageIcon> images;
    JComboBox add_remove;
    JComboBox user_contributions;
    JButton remove_actor;
    JButton remove_director;
    JButton remove_performance;
    JButton genres_but;
    JButton remove_season;
    JButton remove_episode;
    JButton change_ep_name;
    User user;
    JButton back_button;
    Map<String, ProdIcon> prod_icons;

    public UpdatePanel() {
    }

    public UpdatePanel(Map<String, Map<String, Component>> all_components, CardLayout cardLayout, JPanel panelCont,
                       Map<String, Component> child_panels, Map<String, ImageIcon> images, User user, Map<String, ProdIcon> prod_icons,
                       JComboBox choose_rem, JComboBox yours_favorites, JComboBox user_contributions) {
        this.all_components = all_components;
        this.cardLayout = cardLayout;
        this.panelCont = panelCont;
        this.child_panels = child_panels;
        this.user = user;
        this.images = images;
        this.prod_icons = prod_icons;
        this.user_contributions = user_contributions;
        back_button = new JButton();
        remove_episode = new JButton("Remove episode");
        add_remove = new JComboBox<>();
        performance = new JTextField();
        performance_but = new JButton("Add performance");
        performance_label = new JLabel("Performances");
        remove_season = new JButton("Remove season");
        searched = new JTextField();
        seasons_label = new JLabel("Search:");
        searched_label = new JLabel();
        searched_but = new JButton("Search");
        name = new JTextField();
        name_label = new JLabel("Name:");
        directors_label = new JLabel("Directors:");
        add_name = new JButton("Enter");
        add_plot = new JButton("Enter");
        modify = new JButton("Update");
        directors = new JTextField();
        actors = new JTextField();
        actors_label = new JLabel("Actors");
        this_panel = this;
        genres = new ArrayList<>();
        plot = new JTextField();
        plot_label = new JLabel("Plot:");
        ep_duration_label = new JLabel("Episode duration:");
        ep_duration = new JTextField();
        seasons = new JTextField();
        seasons_label = new JLabel("Season:");
        not_found = new JLabel();
        episode = new JTextField();
        episode_label = new JLabel("Episode name:");
        add_actor = new JButton("Add actor");
        add_director = new JButton("Add director");
        add_season = new JButton("Add season");
        add_episode = new JButton("Add episode");
        add_episode_duration = new JButton("Change time");
        remove_actor = new JButton("Remove actor");
        remove_director = new JButton("Remove director");
        remove_performance = new JButton("Remove performance");
        genres_but = new JButton("Add genres");
        change_ep_name = new JButton("Change name");

        Font calibri = new Font("Calibri", Font.PLAIN, 18);
        Color mustard = new Color(219, 165, 5);

        ImageIcon imageIcon = new ImageIcon("src/main/java/org/example/images/back.jpg");
        imageIcon.setImage(imageIcon.getImage().getScaledInstance(40, 30, Image.SCALE_DEFAULT));
        back_button.setBounds(40, 40, 40, 30);
        back_button.setIcon(imageIcon);
        back_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == back_button) {
                    cardLayout.show(panelCont, "3");
                }
            }
        });
        this.add(back_button);

        JLabel your_cont = new JLabel("Your contributions:");
        init_comp(your_cont, calibri, mustard, 40, 100, 200, 20);
        init_comp(user_contributions, calibri, mustard, 40, 120, 430, 30);

        user_contributions.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!user_contributions.getSelectedItem().equals("-")) {
                    cardLayout.show(panelCont, (String) user_contributions.getSelectedItem());
                }
            }
        });

        String[] objects = {"Movie", "Series", "Actor"};
        String[] types = {"Movie", "Series"};
        chosen_obj = new JComboBox<>(objects);
        choose_type = new JComboBox<>(types);
        init_comp(choose_type, calibri, mustard, 230, 450, 200, 30);
        init_comp(chosen_obj, calibri, mustard, 550, 75, 300, 30);
        int x = 400;
        int y = 600;
        int count = 0;
        for (Genre g : Genre.values()) {
            JCheckBox genre = new JCheckBox(g.name());
            if (count == 4) {
                y += 40;
                count = 0;
                x = 400;
            }
            init_comp(genre, calibri, mustard, x, y, 100, 20);
            count++;
            x += 110;
            genres.add(genre);
            this.add(genre);
        }
        init_comp(genres_but, calibri, mustard, 970, 660, 200, 25);
        init_comp(searched_label, calibri, mustard, 270, 160, 100, 25);
        init_comp(searched, calibri, mustard, 550, 160, 300, 25);
        init_comp(searched_but, calibri, mustard, 880, 160, 100, 25);


        searched_but.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (chosen_obj.getSelectedItem().equals("Actor")) {
                    int found = 0;
                    for (Object a : ((Staff) user).getContributions()) {
                        if (a instanceof Actor) {
                            if (((Actor) a).name.equals(searched.getText())) {
                                found = 1;
                            }
                        }
                    }
                    if (found == 1 && e.getSource() == searched_but) {
                        plot_label.setText("Biography:");
                        not_found.setText("");
                        add_remove.setVisible(true);
                        String[] s = {"Change name", "Change biography", "Add performance", "Remove performance"};
                        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(s);
                        add_remove.setModel(model);
                        init_comp(add_remove, calibri, mustard, 550, 115, 300, 30);
                        add_remove.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                if (add_remove.getSelectedItem().equals("Change name")) {
                                    set_components_invisible();
                                    name.setVisible(true);
                                    name_label.setVisible(true);
                                    add_name.setVisible(true);
                                    add_name.addActionListener(new ActionListener() {
                                        @Override
                                        public void actionPerformed(ActionEvent e) {
                                            if (e.getSource() == add_name && !name.getText().isEmpty() && !searched.getText().isEmpty()) {
                                                List<Actor> actorList = IMDB.getInstance().getActors();
                                                List<Production> productions = IMDB.getInstance().getProductions();
                                                for (Actor a : actorList) {
                                                    if (a.name.equals(name.getText())) {
                                                        init_comp(not_found, calibri, Color.RED,200, 300, 300, 25);
                                                        not_found.setVisible(true);
                                                        not_found.setText("This name is already taken!");
                                                        return;
                                                    }
                                                }
                                                for (Production a : productions) {
                                                    if (a.title.equals(name.getText())) {
                                                        init_comp(not_found, calibri, Color.RED,200, 300, 300, 25);
                                                        not_found.setVisible(true);
                                                        not_found.setText("This name is already taken!");
                                                        return;
                                                    }
                                                }

                                                Actor a = ParseInput.get_actor_byname(searched.getText());
                                                Map<String, Component> componentMap = all_components.get(searched.getText());
                                                JLabel name_actor = (JLabel) componentMap.get("actor_name");
                                                if (prod_icons.containsKey(searched.getText())) {
                                                    ProdIcon prodIcon = prod_icons.get(searched.getText());
                                                    prodIcon.setProduction(name.getText());
                                                    prod_icons.remove(searched.getText());
                                                    prod_icons.put(name.getText(), prodIcon);
                                                }
                                                name_actor.setText(name.getText());
                                                ((Staff)user).getContributions().remove(a);
                                                a.name = name.getText();
                                                ((Staff)user).getContributions().add(a);
                                                JPanel panel = (JPanel) child_panels.get(searched.getText());
                                                ImageIcon imageIcon = images.get(searched.getText());
                                                images.remove(searched.getText());
                                                images.put(name.getText(), imageIcon);
                                                panelCont.remove(panel);

                                                all_components.remove(searched.getText());
                                                all_components.put(name.getText(), componentMap);
                                                child_panels.remove(searched.getText());
                                                child_panels.put(name.getText(), panel);

                                                panelCont.add(panel, name.getText());
                                                name.setText("");
                                                int i = 1;
                                                String[] contributions_str = new String[((Staff)user).getContributions().size() + 1];
                                                contributions_str[0] = "-";
                                                for (Object o : ((Staff) user).getContributions()) {
                                                    if (o instanceof Actor) {
                                                        contributions_str[i] = ((Actor) o).name;
                                                    } else {
                                                        contributions_str[i] = ((Production) o).title;
                                                    }
                                                    i++;
                                                }
                                                DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(contributions_str);
                                                searched.setText("");
                                                set_components_invisible();
                                                user_contributions.setModel(model);

                                                if (choose_rem != null) {
                                                    List<String> obj_count = new ArrayList<>();
                                                    for (Object o : ((Staff) user).getContributions()) {
                                                        if (o instanceof Actor) {
                                                            obj_count.add(((Actor) o).getName());
                                                        }
                                                    }
                                                    String[] contributions_st = new String[obj_count.size() + 1];
                                                    contributions_st[0] = "-";
                                                    if (!obj_count.isEmpty()) {
                                                        for (int j = 1; j <= obj_count.size(); j++) {
                                                            contributions_st[j] = obj_count.get(j - 1);
                                                        }
                                                    }

                                                    DefaultComboBoxModel<String> model_rem = new DefaultComboBoxModel<>(contributions_st);
                                                    choose_rem.setModel(model_rem);
                                                }
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
                                                DefaultComboBoxModel<String> model_fav = new DefaultComboBoxModel<>(fav_str);
                                                yours_favorites.setModel(model_fav);
                                                yours_favorites.setSelectedItem("-");

                                            }
                                        }
                                    });
                                } else if (add_remove.getSelectedItem().equals("Change biography")) {
                                    set_components_invisible();
                                    plot.setVisible(true);
                                    plot_label.setVisible(true);
                                    add_plot.setVisible(true);
                                    add_plot.addActionListener(new ActionListener() {
                                        @Override
                                        public void actionPerformed(ActionEvent e) {
                                            if (e.getSource() == add_plot && !plot.getText().isEmpty() && !searched.getText().isEmpty()) {
                                                Actor a = ParseInput.get_actor_byname(searched.getText());
                                                Map<String, Component> componentMap = all_components.get(searched.getText());
                                                JLabel biography = (JLabel) componentMap.get("biography");

                                                String plot_str = "<html>";
                                                int remain = 0;
                                                int count = 0;
                                                for (int j = 0; j < plot.getText().length(); j++) {
                                                    if (count > 50 && plot.getText().charAt(j) == ' ') {
                                                        plot_str += plot.getText().substring(remain, j) + "<br>";
                                                        remain = j;
                                                        count = 0;
                                                    }
                                                    count++;
                                                }
                                                plot_str += plot.getText().substring(remain);
                                                plot_str += "</html>";
                                                biography.setText(plot_str);
                                                a.biography = plot.getText();
                                                biography.setText("");
                                                not_found.setText("");
                                            }
                                        }
                                    });
                                } else if (add_remove.getSelectedItem().equals("Add performance")) {
                                    set_components_invisible();
                                    performance.setVisible(true);
                                    performance_label.setVisible(true);
                                    performance_but.setText("Add performance");
                                    performance_but.setVisible(true);
                                    choose_type.setVisible(true);
                                    performance_but.addActionListener(new ActionListener() {
                                        @Override
                                        public void actionPerformed(ActionEvent e) {
                                            if (e.getSource() == performance_but && !performance.getText().isEmpty() && !searched.getText().isEmpty()) {
                                                Actor a = ParseInput.get_actor_byname(searched.getText());
                                                Map<String, Component> componentMap = all_components.get(searched.getText());
                                                JLabel performances = (JLabel) componentMap.get("performances");
                                                String str = performances.getText();
                                                str = str.substring(0, str.length() - 7);
                                                str += "<br>Title: ";
                                                str += performance.getText();
                                                str += ";  Type: " + choose_type.getSelectedItem();
                                                str += "</html>";
                                                performances.setText(str);
                                                a.performances.add(new Performance(performance.getText(),
                                                        (String) choose_type.getSelectedItem()));
                                                performance.setText("");
                                                not_found.setText("");
                                            }
                                        }
                                    });

                                } else if (add_remove.getSelectedItem().equals("Remove performance")) {
                                    set_components_invisible();
                                    performance.setVisible(true);
                                    performance_label.setVisible(true);
                                    remove_performance.setVisible(true);
                                    choose_type.setVisible(true);
                                    remove_performance.addActionListener(new ActionListener() {
                                        @Override
                                        public void actionPerformed(ActionEvent e) {
                                            if (e.getSource() == remove_performance && !performance.getText().isEmpty() && !searched.getText().isEmpty()) {
                                                Actor a = ParseInput.get_actor_byname(searched.getText());
                                                Map<String, Component> componentMap = all_components.get(searched.getText());
                                                JLabel performances = (JLabel) componentMap.get("performances");
                                                String str = performances.getText();
                                                int found = 0;
                                                int count = 0;
                                                for (Performance p : a.performances) {
                                                    if (p.title.equals(performance.getText()) && p.type.equals(choose_type.getSelectedItem())) {
                                                        if (count < a.performances.size() - 1) {
                                                            str = str.replace("Title: " + performance.getText() + ";  Type:" + choose_type.getSelectedItem() + "<br>", "");
                                                        } else if (a.performances.size() - 1 == count) {
                                                            str = str.replace("Title: " + performance.getText() + ";  Type:" + choose_type.getSelectedItem(), "");
                                                        } else {
                                                            performances.setText("");
                                                        }
                                                        found = 1;
                                                        a.performances.remove(p);
                                                        break;
                                                    }
                                                    count++;
                                                }
                                                if (found == 0) {
                                                    not_found.setText("No results!");
                                                    init_comp(not_found, calibri, Color.RED, 230, 600, 200, 30);
                                                } else {
                                                    performances.setText(str);
                                                    not_found.setText("");
                                                }
                                            } else {
                                                not_found.setText("No results!");
                                                init_comp(not_found, calibri, Color.RED, 230, 600, 200, 30);
                                            }
                                        }
                                    });
                                }
                            }
                        });
                    } else {
                        not_found.setText("No results!");
                        set_components_invisible();
                        add_remove.setVisible(false);
                        init_comp(not_found, calibri, Color.RED, 400, 200, 200, 30);
                    }
                } else if (chosen_obj.getSelectedItem().equals("Movie")) {
                    plot_label.setText("Plot:");
                    int found = 0;
                    for (Object p : ((Staff) user).getContributions()) {
                        if (p instanceof Production) {
                            if (((Production) p).title.equals(searched.getText())) {
                                if (p instanceof Movie && chosen_obj.getSelectedItem().equals("Movie")) {
                                    found = 1;
                                }
                            }
                        }
                    }
                    if (found == 1 && e.getSource() == searched_but) {
                        add_remove.setVisible(true);
                        not_found.setText("");
                        String[] s = {"Change title", "Change plot", "Add director", "Remove director", "Add actor", "Remove actor", "Change genres"};
                        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(s);
                        add_remove.setModel(model);
                        init_comp(add_remove, calibri, mustard, 550, 115, 300, 30);
                        add_remove.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                if (add_remove.getSelectedItem().equals("Change title")) {
                                    set_components_invisible();
                                    name.setVisible(true);
                                    name_label.setVisible(true);
                                    add_name.setVisible(true);
                                    add_name.addActionListener(new ActionListener() {
                                        @Override
                                        public void actionPerformed(ActionEvent e) {
                                            change_title();
                                            int i = 1;
                                            String[] contributions_st = new String[((Staff) user).getContributions().size() + 1];
                                            contributions_st[0] = "-";
                                            for (Object o : ((Staff) user).getContributions()) {
                                                if (o instanceof Actor) {
                                                    contributions_st[i] = ((Actor) o).name;
                                                } else {
                                                    contributions_st[i] = ((Production) o).title;
                                                }
                                                i++;
                                            }
                                            DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(contributions_st);
                                            user_contributions.setModel(model);

                                            if (choose_rem != null) {
                                                List<String> obj_count = new ArrayList<>();
                                                for (Object o : ((Staff) user).getContributions()) {
                                                    if (o instanceof Movie) {
                                                        obj_count.add(((Movie) o).getTitle());
                                                    }
                                                }
                                                String[] cont = new String[obj_count.size() + 1];
                                                cont[0] = "-";
                                                if (!obj_count.isEmpty()) {
                                                    for (int j = 1; j <= obj_count.size(); j++) {
                                                        cont[j] = obj_count.get(j - 1);
                                                    }
                                                }
                                                DefaultComboBoxModel<String> model_rem = new DefaultComboBoxModel<>(contributions_st);
                                                choose_rem.setModel(model_rem);
                                            }
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
                                            DefaultComboBoxModel<String> model_fav = new DefaultComboBoxModel<>(fav_str);
                                            yours_favorites.setModel(model_fav);
                                            yours_favorites.setSelectedItem("-");
                                        }
                                    });
                                } else if (add_remove.getSelectedItem().equals("Change plot")) {
                                    set_components_invisible();
                                    plot.setVisible(true);
                                    plot_label.setVisible(true);
                                    add_plot.setVisible(true);
                                    add_plot.addActionListener(new ActionListener() {
                                        @Override
                                        public void actionPerformed(ActionEvent e) {
                                            if (e.getSource() == add_plot && !plot.getText().isEmpty()) {
                                                change_plot();
                                            }
                                        }
                                    });
                                } else if (add_remove.getSelectedItem().equals("Add director")) {
                                    set_components_invisible();
                                    directors.setVisible(true);
                                    directors_label.setVisible(true);
                                    add_director.setText("Add director");
                                    add_director.setVisible(true);
                                    add_director.addActionListener(new ActionListener() {
                                        @Override
                                        public void actionPerformed(ActionEvent e) {
                                            if (e.getSource() == add_director && !directors.getText().isEmpty()) {
                                                add_directors();
                                            }
                                        }
                                    });
                                } else if (add_remove.getSelectedItem().equals("Add actor")) {
                                    set_components_invisible();
                                    actors.setVisible(true);
                                    actors_label.setVisible(true);
                                    add_actor.setText("Add actor");
                                    add_actor.setVisible(true);
                                    add_actor.addActionListener(new ActionListener() {
                                        @Override
                                        public void actionPerformed(ActionEvent e) {
                                            if (e.getSource() == add_actor && !actors.getText().isEmpty()) {
                                                add_actors();
                                            }
                                        }
                                    });
                                } else if (add_remove.getSelectedItem().equals("Remove actor")) {
                                    set_components_invisible();
                                    actors.setVisible(true);
                                    actors_label.setVisible(true);
                                    remove_actor.setVisible(true);
                                    remove_actor.addActionListener(new ActionListener() {
                                        @Override
                                        public void actionPerformed(ActionEvent e) {
                                            if (e.getSource() == remove_actor && !actors.getText().isEmpty()) {
                                                remove_actors(calibri);
                                            } else {
                                                not_found.setText("No results!");
                                                init_comp(not_found, calibri, Color.RED, 230, 600, 200, 30);
                                            }
                                        }
                                    });
                                } else if (add_remove.getSelectedItem().equals("Remove director")) {
                                    set_components_invisible();
                                    directors.setVisible(true);
                                    directors_label.setVisible(true);
                                    remove_director.setVisible(true);
                                    remove_director.addActionListener(new ActionListener() {
                                        @Override
                                        public void actionPerformed(ActionEvent e) {
                                            if (e.getSource() == remove_director && !directors.getText().isEmpty()) {
                                                remove_directors(calibri);
                                            } else {
                                                not_found.setText("No results!");
                                                init_comp(not_found, calibri, Color.RED, 230, 510, 200, 30);
                                            }
                                        }
                                    });
                                } else if (add_remove.getSelectedItem().equals("Change genres")) {
                                    set_components_invisible();
                                    for (JCheckBox checkBox : genres) {
                                        checkBox.setVisible(true);
                                    }
                                    genres_but.setVisible(true);
                                    genres_but.addActionListener(new ActionListener() {
                                        @Override
                                        public void actionPerformed(ActionEvent e) {
                                            if (e.getSource() == genres_but) {
                                                set_genres(calibri);
                                            }
                                        }
                                    });
                                }
                            }
                        });
                    } else {
                        not_found.setText("No results!");
                        add_remove.setVisible(false);
                        init_comp(not_found, calibri, Color.RED, 400, 200, 200, 30);
                        set_components_invisible();
                    }

                } else if (chosen_obj.getSelectedItem().equals("Series")) {
                    plot_label.setText("Plot:");
                    int found = 0;
                    for (Object p : ((Staff) user).getContributions()) {
                        if (p instanceof Production) {
                            if (((Production) p).title.equals(searched.getText())) {
                                if (p instanceof Series && chosen_obj.getSelectedItem().equals("Series")) {
                                    found = 1;
                                }
                            }
                        }
                    }
                    if (found == 1 && e.getSource() == searched_but) {
                        add_remove.setVisible(true);
                        not_found.setText("");
                        String[] s = {"Change title", "Change plot", "Add director", "Remove director", "Add actor",
                                "Remove actor", "Change genres", "Add season", "Remove season", "Add episode",
                                "Remove episode", "Change duration episode", "Change name episode"};
                        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(s);
                        add_remove.setModel(model);
                        init_comp(add_remove, calibri, mustard, 550, 115, 300, 30);
                        add_remove.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                if (add_remove.getSelectedItem().equals("Change title")) {
                                    set_components_invisible();
                                    name.setVisible(true);
                                    name_label.setVisible(true);
                                    add_name.setVisible(true);
                                    add_name.addActionListener(new ActionListener() {
                                        @Override
                                        public void actionPerformed(ActionEvent e) {
                                            change_title();
                                            int i = 1;
                                            String[] contributions_st = new String[((Staff) user).getContributions().size() + 1];
                                            contributions_st[0] = "-";
                                            for (Object o : ((Staff) user).getContributions()) {
                                                if (o instanceof Actor) {
                                                    contributions_st[i] = ((Actor) o).name;
                                                } else {
                                                    contributions_st[i] = ((Production) o).title;
                                                }
                                                i++;
                                            }
                                            DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(contributions_st);
                                            user_contributions.setModel(model);

                                            if (choose_rem != null) {
                                                List<String> obj_count = new ArrayList<>();
                                                for (Object o : ((Staff) user).getContributions()) {
                                                    if (o instanceof Movie) {
                                                        obj_count.add(((Movie) o).getTitle());
                                                    }
                                                }
                                                String[] cont = new String[obj_count.size() + 1];
                                                cont[0] = "-";
                                                if (!obj_count.isEmpty()) {
                                                    for (int j = 1; j <= obj_count.size(); j++) {
                                                        cont[j] = obj_count.get(j - 1);
                                                    }
                                                }
                                                DefaultComboBoxModel<String> model_rem = new DefaultComboBoxModel<>(contributions_st);
                                                choose_rem.setModel(model_rem);

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
                                                DefaultComboBoxModel<String> model_fav = new DefaultComboBoxModel<>(fav_str);
                                                yours_favorites.setModel(model_fav);
                                                yours_favorites.setSelectedItem("-");
                                            }
                                        }
                                    });
                                } else if (add_remove.getSelectedItem().equals("Change plot")) {
                                    set_components_invisible();
                                    plot.setVisible(true);
                                    plot_label.setVisible(true);
                                    add_plot.setVisible(true);
                                    add_plot.addActionListener(new ActionListener() {
                                        @Override
                                        public void actionPerformed(ActionEvent e) {
                                            if (e.getSource() == add_plot && !plot.getText().isEmpty()) {
                                                change_plot();
                                            }
                                        }
                                    });
                                } else if (add_remove.getSelectedItem().equals("Add director")) {
                                    set_components_invisible();
                                    directors.setVisible(true);
                                    directors_label.setVisible(true);
                                    add_director.setText("Add director");
                                    add_director.setVisible(true);
                                    add_director.addActionListener(new ActionListener() {
                                        @Override
                                        public void actionPerformed(ActionEvent e) {
                                            if (e.getSource() == add_director && !directors.getText().isEmpty()) {
                                                add_directors();
                                            }
                                        }
                                    });
                                } else if (add_remove.getSelectedItem().equals("Add actor")) {
                                    set_components_invisible();
                                    actors.setVisible(true);
                                    actors_label.setVisible(true);
                                    add_actor.setText("Add actor");
                                    add_actor.setVisible(true);
                                    add_actor.addActionListener(new ActionListener() {
                                        @Override
                                        public void actionPerformed(ActionEvent e) {
                                            if (e.getSource() == add_actor && !actors.getText().isEmpty()) {
                                                add_actors();
                                            }
                                        }
                                    });
                                } else if (add_remove.getSelectedItem().equals("Remove actor")) {
                                    set_components_invisible();
                                    actors.setVisible(true);
                                    actors_label.setVisible(true);
                                    remove_actor.setVisible(true);
                                    remove_actor.addActionListener(new ActionListener() {
                                        @Override
                                        public void actionPerformed(ActionEvent e) {
                                            if (e.getSource() == remove_actor && !actors.getText().isEmpty()) {
                                                remove_actors(calibri);
                                            } else {
                                                not_found.setText("No results!");
                                                init_comp(not_found, calibri, Color.RED, 230, 600, 200, 30);
                                            }
                                        }
                                    });
                                } else if (add_remove.getSelectedItem().equals("Remove director")) {
                                    set_components_invisible();
                                    directors.setVisible(true);
                                    directors_label.setVisible(true);
                                    remove_director.setVisible(true);
                                    remove_director.addActionListener(new ActionListener() {
                                        @Override
                                        public void actionPerformed(ActionEvent e) {
                                            if (e.getSource() == remove_director && !directors.getText().isEmpty()) {
                                                remove_directors(calibri);
                                            } else {
                                                not_found.setText("No results!");
                                                init_comp(not_found, calibri, Color.RED, 230, 510, 200, 30);
                                            }
                                        }
                                    });
                                } else if (add_remove.getSelectedItem().equals("Change genres")) {
                                    set_components_invisible();
                                    for (JCheckBox checkBox : genres) {
                                        checkBox.setVisible(true);
                                    }
                                    genres_but.setVisible(true);
                                    genres_but.addActionListener(new ActionListener() {
                                        @Override
                                        public void actionPerformed(ActionEvent e) {
                                            if (e.getSource() == genres_but) {
                                                set_genres(calibri);
                                            }
                                        }
                                    });
                                } else if (add_remove.getSelectedItem().equals("Add season")) {
                                    set_components_invisible();
                                    seasons.setVisible(true);
                                    seasons_label.setVisible(true);
                                    add_season.setVisible(true);
                                    add_season.addActionListener(new ActionListener() {
                                        @Override
                                        public void actionPerformed(ActionEvent e) {
                                            if (e.getSource() == add_season && !seasons.getText().isEmpty() && !searched.getText().isEmpty()) {
                                                Production a = ParseInput.get_production_byname(searched.getText());
                                                Map<String, Component> componentMap = all_components.get(searched.getText());
                                                JPanel holder = (JPanel) componentMap.get("holder");

                                                JPanel season = new JPanel();
                                                season.setLayout(new BoxLayout(season, BoxLayout.Y_AXIS));
                                                season.setBackground(Color.black);
                                                JLabel season_name = new JLabel(seasons.getText() + ":");
                                                season_name.setForeground(new Color(211, 211, 211));
                                                season_name.setFont(new Font("Calibri", Font.BOLD, 25));
                                                componentMap.put("panel" + seasons.getText(), season);

                                                season.add(season_name);
                                                ((Series) a).setNumSeasons(((Series) a).getNumSeasons() + 1);
                                                ((Series) a).getSeasons().put(seasons.getText(), new ArrayList<>());

                                                holder.add(season);
                                                seasons.setText("");
                                            }
                                        }
                                    });
                                } else if (add_remove.getSelectedItem().equals("Remove season")) {
                                    set_components_invisible();
                                    seasons.setVisible(true);
                                    seasons_label.setVisible(true);
                                    remove_season.setVisible(true);
                                    remove_season.addActionListener(new ActionListener() {
                                        @Override
                                        public void actionPerformed(ActionEvent e) {
                                            if (e.getSource() == remove_season && !searched.getText().isEmpty()) {
                                                Production a = ParseInput.get_production_byname(searched.getText());
                                                Map<String, Component> componentMap = all_components.get(searched.getText());
                                                JPanel holder = (JPanel) componentMap.get("holder");
                                                if (componentMap.containsKey("panel" + seasons.getText())) {
                                                    JPanel season_pan = (JPanel) componentMap.get("panel" + seasons.getText());

                                                    componentMap.remove("panel" + seasons.getText());
                                                    holder.remove(season_pan);
                                                    ((Series) a).setNumSeasons(((Series) a).getNumSeasons() - 1);
                                                    ((Series) a).getSeasons().remove(seasons.getText());
                                                    seasons.setText("");
                                                    not_found.setText("");
                                                } else {
                                                    not_found.setText("No results!");
                                                    init_comp(not_found, calibri, Color.RED, 1000, 250, 200, 30);
                                                }
                                            }
                                        }
                                    });
                                } else if (add_remove.getSelectedItem().equals("Add episode")) {
                                    set_components_invisible();
                                    episode.setVisible(true);
                                    seasons.setVisible(true);
                                    seasons_label.setVisible(true);
                                    episode_label.setVisible(true);
                                    ep_duration.setVisible(true);
                                    ep_duration_label.setVisible(true);
                                    add_episode.setVisible(true);
                                    add_episode.addActionListener(new ActionListener() {
                                        @Override
                                        public void actionPerformed(ActionEvent e) {
                                            if (e.getSource() == add_episode && !searched.getText().isEmpty()) {
                                                Production a = ParseInput.get_production_byname(searched.getText());
                                                Map<String, Component> componentMap = all_components.get(searched.getText());
                                                if (componentMap.containsKey("panel" + seasons.getText()) && !episode.getText().isEmpty() &&
                                                        !ep_duration.getText().isEmpty()) {
                                                    if (ep_duration.getText().endsWith("minutes")) {
                                                        try {
                                                            int x = Integer.parseInt(ep_duration.getText().split(" ")[0]);
                                                            JPanel season_panel = (JPanel) componentMap.get("panel" + seasons.getText());

                                                            JLabel episode_lab_add = new JLabel("         " + episode.getText() + ";  duration: " + ep_duration.getText());
                                                            episode_lab_add.setForeground(new Color(211, 211, 211));
                                                            episode_lab_add.setFont(new Font("Calibri", Font.PLAIN, 15));
                                                            componentMap.put(episode.getText(), episode_lab_add);

                                                            season_panel.add(episode_lab_add);
                                                            List<Episode> episodes = ((Series) a).getSeasons().get(seasons.getText());
                                                            episodes.add(new Episode(episode.getText(), ep_duration.getText()));

                                                            seasons.setText("");
                                                            episode.setText("");
                                                            ep_duration.setText("");
                                                            not_found.setText("");
                                                        } catch (NumberFormatException exception) {
                                                            not_found.setText("Duration should be in the format -number minutes-");
                                                            init_comp(not_found, calibri, Color.RED, 600, 440, 500, 20);
                                                        }
                                                    }
                                                    else {
                                                        not_found.setText("Duration should be in the format -number minutes-");
                                                        init_comp(not_found, calibri, Color.RED, 600, 440, 500, 20);
                                                    }
                                                } else {
                                                    not_found.setText("No results!");
                                                    init_comp(not_found, calibri, Color.RED, 1000, 300, 200, 30);
                                                }
                                            }
                                        }
                                    });
                                } else if (add_remove.getSelectedItem().equals("Remove episode")) {
                                    set_components_invisible();
                                    seasons.setVisible(true);
                                    seasons_label.setVisible(true);
                                    episode.setVisible(true);
                                    episode_label.setVisible(true);
                                    remove_episode.setVisible(true);
                                    remove_episode.addActionListener(new ActionListener() {
                                        @Override
                                        public void actionPerformed(ActionEvent e) {
                                            if (e.getSource() == remove_episode && !searched.getText().isEmpty()) {
                                                Production a = ParseInput.get_production_byname(searched.getText());
                                                Map<String, Component> componentMap = all_components.get(searched.getText());
                                                JPanel holder = (JPanel) componentMap.get("panel" + seasons.getText());
                                                if (componentMap.containsKey("panel" + seasons.getText()) && componentMap.containsKey(episode.getText())) {
                                                    JLabel episode_rem_mod = (JLabel) componentMap.get(episode.getText());

                                                    holder.remove(episode_rem_mod);
                                                    List<Episode> episodes = ((Series) a).getSeasons().get(seasons.getText());
                                                    for (Episode ep : episodes) {
                                                        if (ep.getEpisodeName().equals(episode.getText())) {
                                                            episodes.remove(ep);
                                                            break;
                                                        }
                                                    }
                                                    episode.setText("");
                                                    seasons.setText("");
                                                    not_found.setVisible(false);
                                                } else {
                                                    not_found.setText("No results!");
                                                    init_comp(not_found, calibri, Color.RED, 1000, 250, 200, 30);
                                                    not_found.setVisible(true);
                                                }
                                            }
                                        }
                                    });
                                } else if (add_remove.getSelectedItem().equals("Change duration episode")) {
                                    set_components_invisible();
                                    episode.setVisible(true);
                                    seasons.setVisible(true);
                                    seasons_label.setVisible(true);
                                    episode_label.setVisible(true);
                                    ep_duration.setVisible(true);
                                    ep_duration_label.setVisible(true);
                                    ep_duration_label.setText("Episode duration");
                                    add_episode_duration.setVisible(true);
                                    add_episode_duration.addActionListener(new ActionListener() {
                                        @Override
                                        public void actionPerformed(ActionEvent e) {
                                            if (e.getSource() == add_episode_duration && !searched.getText().isEmpty()) {
                                                Production a = ParseInput.get_production_byname(searched.getText());
                                                Map<String, Component> componentMap = all_components.get(searched.getText());
                                                if (componentMap.containsKey("panel" + seasons.getText()) && componentMap.containsKey(episode.getText()) &&
                                                        !ep_duration.getText().isEmpty()) {
                                                    if (ep_duration.getText().endsWith("minutes")) {
                                                        try {
                                                            int x = Integer.parseInt(ep_duration.getText().split(" ")[0]);
                                                            JLabel ep_change = (JLabel) componentMap.get(episode.getText());

                                                            ep_change.setText(ep_change.getText().replaceFirst("(?<=duration: ).*", ep_duration.getText()));

                                                            List<Episode> episodes = ((Series) a).getSeasons().get(seasons.getText());
                                                            for (Episode ep : episodes) {
                                                                if (ep.getEpisodeName().equals(episode.getText())) {
                                                                    ep.setDuration(ep_duration.getText());
                                                                }
                                                            }
                                                            seasons.setText("");
                                                            episode.setText("");
                                                            ep_duration.setText("");
                                                            not_found.setText("");
                                                        } catch (NumberFormatException exception) {
                                                            not_found.setText("Duration should be in the format -number minutes-");
                                                            init_comp(not_found, calibri, Color.RED, 600, 440, 500, 20);
                                                        }
                                                    }
                                                    else {
                                                        not_found.setText("Duration should be in the format -number minutes-");
                                                        init_comp(not_found, calibri, Color.RED, 600, 440, 500, 20);
                                                    }

                                                } else {
                                                    not_found.setText("No results!");
                                                    init_comp(not_found, calibri, Color.RED, 1000, 300, 200, 30);
                                                }
                                            }
                                        }
                                    });
                                } else if (add_remove.getSelectedItem().equals("Change name episode")) {
                                    set_components_invisible();
                                    episode.setVisible(true);
                                    seasons.setVisible(true);
                                    seasons_label.setVisible(true);
                                    episode_label.setVisible(true);
                                    ep_duration.setVisible(true);
                                    ep_duration_label.setVisible(true);
                                    ep_duration_label.setText("Change name");
                                    change_ep_name.setVisible(true);
                                    change_ep_name.addActionListener(new ActionListener() {
                                        @Override
                                        public void actionPerformed(ActionEvent e) {
                                            if (e.getSource() == change_ep_name && !searched.getText().isEmpty()) {
                                                Production a = ParseInput.get_production_byname(searched.getText());
                                                Map<String, Component> componentMap = all_components.get(searched.getText());
                                                if (componentMap.containsKey("panel" + seasons.getText()) && componentMap.containsKey(episode.getText()) &&
                                                        !ep_duration.getText().isEmpty()) {
                                                    JLabel ep_change = (JLabel) componentMap.get(episode.getText());

                                                    String str = ep_change.getText();
                                                    str = str.replace(episode.getText(), ep_duration.getText());
                                                    ep_change.setText(str);

                                                    List<Episode> episodes = ((Series) a).getSeasons().get(seasons.getText());
                                                    for (Episode ep : episodes) {
                                                        if (ep.getEpisodeName().equals(episode.getText())) {
                                                            ep.setDuration(ep_duration.getText());
                                                        }
                                                    }
                                                    seasons.setText("");
                                                    episode.setText("");
                                                    ep_duration.setText("");
                                                    not_found.setText("");
                                                    add_remove.setVisible(false);
                                                } else {
                                                    not_found.setText("No results!");
                                                    init_comp(not_found, calibri, Color.RED, 1000, 300, 200, 30);
                                                }
                                            }
                                        }
                                    });
                                }
                            }
                        });
                    } else {
                        not_found.setText("No results!");
                        init_comp(not_found, calibri, Color.RED, 400, 200, 200, 30);
                        set_components_invisible();
                        add_remove.setVisible(false);
                    }

                }

            }
        });


        init_comp(name_label, calibri, mustard, 100, 220, 100, 20);
        init_comp(name, calibri, mustard, 230, 220, 200, 20);
        init_comp(add_name, calibri, mustard, 230, 250, 160, 20);

        init_comp(plot_label, calibri, mustard, 100, 290, 100, 20);
        init_comp(plot, calibri, mustard, 230, 290, 300, 20);
        init_comp(add_plot, calibri, mustard, 230, 320, 160, 20);

        init_comp(performance_label, calibri, mustard, 100, 370, 120, 20);
        init_comp(performance, calibri, mustard, 230, 370, 200, 20);
        init_comp(performance_but, calibri, mustard, 230, 400, 160, 20);
        init_comp(remove_performance, calibri, mustard, 230, 500, 160, 20);

        init_comp(directors_label, calibri, mustard, 100, 370, 100, 20);
        init_comp(directors, calibri, mustard, 230, 370, 200, 20);
        init_comp(add_director, calibri, mustard, 230, 400, 160, 20);
        init_comp(remove_director, calibri, mustard, 230, 450, 160, 20);

        init_comp(actors_label, calibri, mustard, 100, 450, 100, 20);
        init_comp(actors, calibri, mustard, 230, 450, 200, 20);
        init_comp(add_actor, calibri, mustard, 230, 490, 160, 20);
        init_comp(remove_actor, calibri, mustard, 230, 540, 160, 20);

        init_comp(seasons_label, calibri, mustard, 630, 220, 100, 20);
        init_comp(seasons, calibri, mustard, 780, 220, 200, 20);
        init_comp(add_season, calibri, mustard, 1000, 210, 160, 20);
        init_comp(remove_season, calibri, mustard, 1000, 230, 160, 20);

        init_comp(episode_label, calibri, mustard, 630, 270, 150, 20);
        init_comp(episode, calibri, mustard, 780, 270, 200, 20);
        init_comp(add_episode, calibri, mustard, 1000, 270, 160, 20);
        init_comp(remove_episode, calibri, mustard, 1000, 290, 160, 20);

        init_comp(ep_duration_label, calibri, mustard, 630, 320, 150, 20);
        init_comp(ep_duration, calibri, mustard, 780, 320, 200, 20);
        init_comp(add_episode_duration, calibri, mustard, 1000, 320, 160, 20);
        init_comp(change_ep_name, calibri, mustard, 1000, 345, 160, 20);

        set_components_invisible();

        this.add(chosen_obj);
        this.add(add_remove);
        this.add(genres_but);
        this.add(remove_season);
        this.add(remove_episode);

        this.add(name_label);
        this.add(name);
        this.add(add_name);

        this.add(performance);
        this.add(performance_label);
        this.add(performance_but);
        this.add(choose_type);

        this.add(remove_actor);
        this.add(remove_director);
        this.add(remove_performance);

        this.add(not_found);
        this.add(change_ep_name);

        this.add(searched);
        this.add(searched_but);
        this.add(searched_label);

        this.add(plot_label);
        this.add(plot);
        this.add(add_plot);

        this.add(directors_label);
        this.add(directors);
        this.add(add_director);

        this.add(actors_label);
        this.add(actors);
        this.add(add_actor);

        this.add(seasons_label);
        this.add(seasons);
        this.add(add_season);

        this.add(episode);
        this.add(episode_label);
        this.add(add_episode);
        this.add(user_contributions);

        this.add(ep_duration_label);
        this.add(ep_duration);
        this.add(add_episode_duration);
        setLayout(null);
        setVisible(true);
        this.setBackground(Color.black);
    }

    public void change_title() {
        if (!searched.getText().isEmpty()) {
            List<Actor> actorList = IMDB.getInstance().getActors();
            List<Production> productions = IMDB.getInstance().getProductions();
            for (Actor a : actorList) {
                if (a.name.equals(name.getText())) {
                    init_comp(not_found, new Font("Calibri", Font.PLAIN, 15), Color.RED,200, 300, 300, 25);
                    not_found.setVisible(true);
                    not_found.setText("This name is already taken!");
                    return;
                }
            }
            for (Production a : productions) {
                if (a.title.equals(name.getText())) {
                    init_comp(not_found, new Font("Calibri", Font.PLAIN, 15), Color.RED,200, 300, 300, 25);
                    not_found.setVisible(true);
                    not_found.setText("This name is already taken!");
                    return;
                }
            }

            Production a = ParseInput.get_production_byname(searched.getText());
            Map<String, Component> componentMap = all_components.get(searched.getText());
            if (prod_icons.containsKey(searched.getText())) {
                ProdIcon prodIcon = prod_icons.get(searched.getText());
                prodIcon.setProduction(name.getText());
                prod_icons.remove(searched.getText());
                prod_icons.put(name.getText(), prodIcon);
            }
            JLabel name_prod = (JLabel) componentMap.get("title");
            name_prod.setText(name.getText());
            assert a != null;
            a.title = name.getText();
            JPanel panel = (JPanel) child_panels.get(searched.getText());
            ImageIcon imageIcon = images.get(searched.getText());
            images.remove(searched.getText());
            images.put(name.getText(), imageIcon);
            panelCont.remove(panel);
            panelCont.add(panel, name.getText());
            all_components.remove(searched.getText());
            all_components.put(name.getText(), componentMap);
            child_panels.remove(searched.getText());
            child_panels.put(name.getText(), panel);
            name.setText("");
            not_found.setText("");
            searched.setText("");
            set_components_invisible();
            add_remove.setVisible(false);
        }
    }

    public JComboBox getUser_contributions() {
        return user_contributions;
    }

    public void change_plot() {
        if (!searched.getText().isEmpty()) {
            Production a = ParseInput.get_production_byname(searched.getText());
            Map<String, Component> componentMap = all_components.get(searched.getText());
            JLabel biography = (JLabel) componentMap.get("plot");

            String plot_str = "<html>";
            int remain = 0;
            int count = 0;
            for (int j = 0; j < plot.getText().length(); j++) {
                if (count > 50 && plot.getText().charAt(j) == ' ') {
                    plot_str += plot.getText().substring(remain, j) + "<br>";
                    remain = j;
                    count = 0;
                }
                count++;
            }
            plot_str += plot.getText().substring(remain);
            plot_str += "</html>";
            biography.setText(plot_str);
            assert a != null;
            a.setPlot(plot.getText());
            biography.setText("");
            not_found.setText("");
        }
    }

    public void init_comp(Component component, Font calibri, Color grey, int x, int y, int width, int height) {
        component.setBounds(x, y, width, height);
        component.setFont(calibri);
        if (!(component instanceof JTextField)) {
            component.setBackground(Color.black);
        }
        component.setForeground(grey);
    }

    public void set_genres(Font calibri) {
        if (!searched.getText().isEmpty()) {
            int count = 0;
            for (JCheckBox checkBox : genres) {
                if (checkBox.isSelected()) {
                    count++;
                }
            }
            if (count > 4) {
                not_found.setText("The maximum number of genres for a production is 4.");
                init_comp(not_found, calibri, Color.RED, 400, 500, 300, 30);
            } else {
                Production a = ParseInput.get_production_byname(searched.getText());
                Map<String, Component> componentMap = all_components.get(searched.getText());
                JLabel genres_modify_label = (JLabel) componentMap.get("genres");
                genres_modify_label.setText("");
                String str = null;
                assert a != null;
                a.getGenres().clear();
                for (JCheckBox checkBox : genres) {
                    if (checkBox.isSelected()) {
                        str += checkBox.getText() + ", ";
                        a.getGenres().add(Genre.valueOf(checkBox.getText()));
                    }
                }
                str = str.replace("null", "");
                str = str.substring(0, str.length() - 2);
                genres_modify_label.setText(str);
                not_found.setText("");
                for (JCheckBox checkBox : genres) {
                    if (checkBox.isSelected()) {
                        checkBox.setSelected(false);
                    }
                }
            }
        }
    }

    public void add_directors() {
        if (!searched.getText().isEmpty()) {
            Production a = ParseInput.get_production_byname(searched.getText());
            Map<String, Component> componentMap = all_components.get(searched.getText());
            JLabel directors_label_modify = (JLabel) componentMap.get("directors");
            String str = directors_label_modify.getText();
            str = str.substring(0, str.length() - 7);
            str += directors.getText();
            str += "<br></html>";
            directors_label_modify.setText(str);
            assert a != null;
            a.getDirectors().add(directors.getText());
            directors.setText("");
            not_found.setText("");
        }
    }

    public void add_actors() {
        if (!searched.getText().isEmpty()) {
            Production a = ParseInput.get_production_byname(searched.getText());
            Map<String, Component> componentMap = all_components.get(searched.getText());
            JLabel actors_label_modify = (JLabel) componentMap.get("actors");
            String str = actors_label_modify.getText();
            str = str.substring(0, str.length() - 7);
            str += actors.getText();
            str += "<br></html>";
            actors_label_modify.setText(str);
            assert a != null;
            a.getActors().add(actors.getText());
            actors.setText("");
            not_found.setText("");
        }
    }

    public void remove_actors(Font calibri) {
        if (!searched.getText().isEmpty()) {
            Production a = ParseInput.get_production_byname(searched.getText());
            Map<String, Component> componentMap = all_components.get(searched.getText());
            JLabel actors_label_modify = (JLabel) componentMap.get("actors");
            String str = actors_label_modify.getText();
            int found = 0;
            int count = 0;
            assert a != null;
            for (String p : a.getActors()) {
                if (p.equals(actors.getText())) {
                    if (count < a.getActors().size() - 1) {
                        str = str.replace(actors.getText() + "<br>", "");
                    } else if (a.getActors().size() - 1 == count) {
                        str = str.replace(actors.getText(), "");
                    } else {
                        actors_label_modify.setText("");
                    }
                    found = 1;
                    a.getActors().remove(p);
                    break;
                }
                count++;
            }
            if (found == 0) {
                not_found.setText("No results!");
                init_comp(not_found, calibri, Color.RED, 230, 600, 200, 30);
            } else {
                actors_label_modify.setText(str);
                actors.setText("");
                not_found.setText("");
            }
        }
    }

    public void remove_directors(Font calibri) {
        if (!searched.getText().isEmpty()) {
            Production a = ParseInput.get_production_byname(searched.getText());
            Map<String, Component> componentMap = all_components.get(searched.getText());
            JLabel directors_label_modify = (JLabel) componentMap.get("directors");
            String str = directors_label_modify.getText();
            int found = 0;
            int count = 0;
            assert a != null;
            for (String p : a.getDirectors()) {
                if (p.equals(directors.getText())) {
                    if (count < a.getDirectors().size() - 1) {
                        str = str.replace(directors.getText() + "<br>", "");
                    } else if (a.getDirectors().size() - 1 == count) {
                        str = str.replace(directors.getText(), "");
                    } else {
                        directors_label_modify.setText("");
                    }
                    found = 1;
                    a.getDirectors().remove(p);
                    break;
                }
                count++;
            }
            if (found == 0) {
                not_found.setText("No results!");
                init_comp(not_found, calibri, Color.RED, 230, 510, 200, 30);
            } else {
                directors_label_modify.setText(str);
                directors.setText("");
                not_found.setText("");
            }
        }
    }

    public void set_components_invisible() {
        name.setVisible(false);
        name_label.setVisible(false);
        add_name.setVisible(false);

        directors.setVisible(false);
        genres_but.setVisible(false);
        directors_label.setVisible(false);
        add_director.setVisible(false);
        remove_actor.setVisible(false);
        remove_director.setVisible(false);
        remove_performance.setVisible(false);
        remove_season.setVisible(false);
        remove_episode.setVisible(false);

        actors_label.setVisible(false);
        actors.setVisible(false);
        add_actor.setVisible(false);

        seasons.setVisible(false);
        seasons_label.setVisible(false);
        add_season.setVisible(false);

        ep_duration.setVisible(false);
        ep_duration_label.setVisible(false);
        add_episode_duration.setVisible(false);

        plot.setVisible(false);
        plot_label.setVisible(false);
        add_plot.setVisible(false);

        episode.setVisible(false);
        episode_label.setVisible(false);
        add_episode.setVisible(false);

        performance_label.setVisible(false);
        performance.setVisible(false);
        performance_but.setVisible(false);
        choose_type.setVisible(false);
        change_ep_name.setVisible(false);

        for (JCheckBox c : genres) {
            c.setVisible(false);
        }
    }
}
