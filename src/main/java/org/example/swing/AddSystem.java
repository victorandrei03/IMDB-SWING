package org.example.swing;

import org.example.*;
import org.example.actors.Actor;
import org.example.actors.Performance;
import org.example.painters.ProdIcon;
import org.example.productions.*;
import org.example.strategy.AddProductionActor;
import org.example.users.AccountType;
import org.example.users.Contributor;
import org.example.users.Staff;
import org.example.users.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddSystem extends JPanel {
    JComboBox chosen_obj;
    JComboBox users_contributios;
    JTextField name;
    JLabel name_label;
    JTextField directors;
    JLabel directors_label;
    Map<String, ProdIcon> prod_icons;
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
    JButton add_name;
    JButton add_plot;
    JLabel ep_duration_label;
    JTextField ep_duration;
    JButton create;
    CardLayout cardLayout;
    JPanel panelCont;
    JLabel not_found;
    JLabel performance_label;
    JTextField performance;
    JButton performance_but;
    Map<String, Map<String, Component>> all_components;
    Map<String, Component> child_panels;
    Map<String, ImageIcon> images;
    JComboBox add_remove;
    JButton genres_but;
    JButton add_ep_duration;
    User user;
    JButton back_button;
    JComboBox choose_type;
    JTextField release_year;
    JButton add_release_year;
    JButton upload_image;
    JLabel release_year_label;
    ImageIcon imdb_logo;
    JLabel movie_time_label;
    JTextField movie_time;
    JButton add_movie_time;

    public AddSystem() {
    }

    public AddSystem(Map<String, Map<String, Component>> all_components, CardLayout cardLayout, JPanel panelCont,
                     Map<String, Component> child_panels, Map<String, ImageIcon> images, User user, ImageIcon imdb_logo,
                     JComboBox users_contributios, Map<String, ProdIcon> prod_icons, JLabel experience_label) {
        this.all_components = all_components;
        this.cardLayout = cardLayout;
        this.panelCont = panelCont;
        this.child_panels = child_panels;
        this.prod_icons = prod_icons;
        this.user = user;
        this.images = images;
        this.imdb_logo = imdb_logo;
        this.users_contributios = users_contributios;
        movie_time = new JTextField();
        movie_time_label = new JLabel("Duration:");
        add_movie_time = new JButton("Enter");
        release_year = new JTextField();
        add_release_year = new JButton("Enter");
        release_year_label = new JLabel("Release year:");
        back_button = new JButton();
        add_remove = new JComboBox<>();
        performance = new JTextField();
        performance_but = new JButton("Add performance");
        performance_label = new JLabel("Performances:");
        name = new JTextField();
        name_label = new JLabel("Name:");
        directors_label = new JLabel("Directors:");
        add_name = new JButton("Enter");
        add_plot = new JButton("Enter");
        create = new JButton("Create");
        upload_image = new JButton("Upload image");
        directors = new JTextField();
        actors = new JTextField();
        actors_label = new JLabel("Actors:");
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
        genres_but = new JButton("Add genres");
        add_ep_duration = new JButton("Set duration");


        this.add(not_found);
        Font calibri = new Font("Calibri", Font.PLAIN, 15);
        Color mustard = new Color(219, 165, 5);

        ImageIcon imageIcon = new ImageIcon("src/main/java/org/example/images/back.jpg");
        imageIcon.setImage(imageIcon.getImage().getScaledInstance(40, 30, Image.SCALE_DEFAULT));
        back_button.setBounds(40, 40, 40, 30);

        String[] types = {"Movie", "Series"};
        choose_type = new JComboBox<>(types);
        init_comp(choose_type, calibri, mustard, 230, 450, 200, 30);

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

        List<Actor> actorList = IMDB.getInstance().getActors();
        List<Production> productions = IMDB.getInstance().getProductions();

        String[] objects = {"-", "Movie", "Series", "Actor"};
        chosen_obj = new JComboBox<>(objects);
        init_comp(chosen_obj, calibri, mustard, 400, 75, 300, 30);
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
        chosen_obj.setSelectedItem("-");


        chosen_obj.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (chosen_obj.getSelectedItem().equals("Actor")) {
                    set_components_invisible();
                    plot_label.setText("Biography:");

                    performance.setVisible(true);
                    performance_label.setVisible(true);
                    performance_but.setVisible(true);
                    choose_type.setVisible(true);

                } else if (chosen_obj.getSelectedItem().equals("Movie")) {
                    plot_label.setText("Plot:");
                    set_components_invisible();

                    directors.setVisible(true);
                    directors_label.setVisible(true);
                    add_director.setVisible(true);

                    movie_time.setVisible(true);
                    movie_time_label.setVisible(true);
                    add_movie_time.setVisible(true);

                    actors.setVisible(true);
                    actors_label.setVisible(true);
                    add_actor.setVisible(true);

                    release_year.setVisible(true);
                    release_year_label.setVisible(true);
                    add_release_year.setVisible(true);

                    genres_but.setVisible(true);
                    for (JCheckBox checkBox : genres) {
                        checkBox.setVisible(true);
                    }

                } else if (chosen_obj.getSelectedItem().equals("Series")) {
                    plot_label.setText("Plot:");
                    set_components_invisible();

                    directors.setVisible(true);
                    directors_label.setVisible(true);
                    add_director.setVisible(true);

                    actors.setVisible(true);
                    actors_label.setVisible(true);
                    add_actor.setVisible(true);

                    genres_but.setVisible(true);
                    for (JCheckBox checkBox : genres) {
                        checkBox.setVisible(true);
                    }

                    seasons.setVisible(true);
                    seasons_label.setVisible(true);
                    add_season.setVisible(true);

                    episode.setVisible(true);
                    episode_label.setVisible(true);
                    add_episode.setVisible(true);

                    ep_duration.setVisible(true);
                    ep_duration_label.setVisible(true);

                    release_year.setVisible(true);
                    release_year_label.setVisible(true);
                    add_release_year.setVisible(true);

                    int count = 0;
                    for (JCheckBox checkBox : genres) {
                        if (checkBox.isSelected()) {
                            count++;
                        }
                    }
                    if (count > 4) {
                        not_found.setText("The maximum number of genres for a production is 4.");
                        init_comp(not_found, calibri, Color.RED, 400, 500, 500, 30);
                    }
                }

            }
        });

        List<Performance> performances_list = new ArrayList<>();
        final String[] nameProdAct = new String[1];
        final String[] biography = new String[1];
        final ImageIcon[] icon = new ImageIcon[1];
        upload_image.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int res = fileChooser.showOpenDialog(null);

                if (res == JFileChooser.APPROVE_OPTION) {
                    icon[0] = new ImageIcon(fileChooser.getSelectedFile().getAbsolutePath());
                    icon[0].setImage(icon[0].getImage().getScaledInstance(300, 400 ,Image.SCALE_DEFAULT));
                    if (icon[0]== null) {
                        not_found.setText("Upload image");
                    }
                }
            }
        });
        performance_but.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!performance.getText().isEmpty() && e.getSource() == performance_but) {
                    String perf_title = performance.getText();
                    String type = (String) choose_type.getSelectedItem();
                    Performance p = new Performance(perf_title, type);
                    performances_list.add(p);
                    performance.setText("");
                }
            }
        });
        add_name.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!name.getText().isEmpty() && e.getSource() == add_name) {
                    nameProdAct[0] = name.getText();
                    name.setText("");
                }
            }
        });
        add_plot.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!plot.getText().isEmpty() && e.getSource() == add_plot) {
                    biography[0] = plot.getText();
                    plot.setText("");
                }
            }
        });

        List<String> directors_list = new ArrayList<>();
        List<String> actors_list = new ArrayList<>();
        final Long[] release_year_cr = {null};
        List<Genre> genreList = new ArrayList<>();

        add_director.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!directors.getText().isEmpty() && e.getSource() == add_director) {
                    String dir_name = directors.getText();
                    directors_list.add(dir_name);
                    directors.setText("");
                }
            }
        });

        add_actor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!actors.getText().isEmpty() && e.getSource() == add_actor) {
                    String act_name = actors.getText();
                    actors_list.add(act_name);
                    actors.setText("");
                }
            }
        });
        add_release_year.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!release_year.getText().isEmpty() && e.getSource() == add_release_year) {
                    String rel_year = release_year.getText();
                    try {
                        release_year_cr[0] = Long.parseLong(rel_year);
                        not_found.setText("");
                        release_year.setText("");
                    }
                    catch (NumberFormatException exception) {
                        not_found.setText("Release year should be a number");
                        init_comp(not_found, calibri, Color.RED, 600, 400, 500, 20);
                    }
                }
            }
        });
        genres_but.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == genres_but) {
                    int count = 0;
                    for (JCheckBox checkBox : genres) {
                        if (checkBox.isSelected()) {
                            count++;
                        }
                    }
                    if (count > 4) {
                        not_found.setText("A production should have maximum 4 genres");
                    }
                    else {
                        for (JCheckBox checkBox : genres) {
                            if (checkBox.isSelected() && checkBox.getText() != null) {
                                genreList.add(Genre.valueOf(checkBox.getText()));
                            }
                        }
                        not_found.setText("");
                        for (JCheckBox checkBox : genres) {
                            if (checkBox.isSelected()) {
                                checkBox.setSelected(false);
                            }
                        }
                    }
                }
            }
        });
        final String[] movie_dur_str = new String[1];
        add_movie_time.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!movie_time.getText().isEmpty() && e.getSource() == add_movie_time) {
                    String movie_time_str = movie_time.getText();
                    if (movie_time_str.endsWith("minutes")) {
                        try {
                            int x = Integer.parseInt(movie_time_str.split(" ")[0]);
                            movie_dur_str[0] = movie_time.getText();
                            movie_time.setText("");
                            not_found.setText("");
                        } catch (NumberFormatException exception) {
                            not_found.setVisible(true);
                            not_found.setText("Duration should be in the format -number minutes-");
                            init_comp(not_found, calibri, Color.RED, 600, 500, 500, 20);
                        }
                    }
                    else {
                        not_found.setVisible(true);
                        not_found.setText("Duration should be in the format -number minutes-");
                        init_comp(not_found, calibri, Color.RED, 600, 500, 500, 20);
                    }
                }
            }
        });
        Map<String, List<Episode>> seasons_map = new HashMap<>();
        final int[] num_season = {0};
        add_season.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!seasons.getText().isEmpty() && e.getSource() == add_season) {
                    String season_name = seasons.getText();
                    seasons_map.put(season_name, new ArrayList<>());
                    num_season[0]++;
                    seasons.setText("");
                }
            }
        });
        add_episode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!episode.getText().isEmpty() && !ep_duration.getText().isEmpty() && !seasons.getText().isEmpty() &&
                        e.getSource() == add_episode) {
                    if (seasons_map.containsKey(seasons.getText())) {
                        String ep_name = episode.getText();
                        String duration_s = ep_duration.getText();
                        if (duration_s.endsWith("minutes")) {
                            try {
                                int x = Integer.parseInt(duration_s.split(" ")[0]);
                                Episode ep = new Episode(ep_name, duration_s);
                                seasons_map.get(seasons.getText()).add(ep);
                                ep_duration.setText("");
                                episode.setText("");
                                seasons.setText("");
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
                    }
                    else {
                        not_found.setText("Operation failed! Season not found!");
                        init_comp(not_found, calibri, Color.RED, 600, 440, 500, 20);
                    }

                }
                else {
                    not_found.setText("Write the season's name and complete both of the fields!");
                    init_comp(not_found, calibri, Color.RED, 600, 440, 500, 20);
                }
            }
        });
        add_ep_duration.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!ep_duration.getText().isEmpty() && e.getSource() == add_ep_duration) {
                    String movie_time_str = movie_time.getText();
                    if (movie_time_str.endsWith("minutes")) {
                        try {
                            int x = Integer.parseInt(movie_time_str.split(" ")[0]);
                            movie_dur_str[0] = movie_time.getText();
                            movie_time.setText("");
                            not_found.setText("");
                        } catch (NumberFormatException exception) {
                            not_found.setText("Duration should be in the format -number minutes-");
                            init_comp(not_found, calibri, Color.RED, 600, 440, 500, 20);
                        }
                    }
                }
            }
        });

        create.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (chosen_obj.getSelectedItem().equals("Actor")) {
                    if ((nameProdAct[0] == null || biography[0] == null || icon[0] == null ||
                            performances_list.isEmpty()) && e.getSource() == create) {

                        not_found.setText("Complete fields!");
                        init_comp(not_found, calibri, Color.RED, 600, 400, 160, 20);
                    }
                    else if (e.getSource() == create) {
                        Actor actor = new Actor(nameProdAct[0], biography[0], performances_list);
                        actorList.add(actor);
                        not_found.setText("");
                        images.put(actor.name, icon[0]);
                        ((Staff)user).getContributions().add(actor);

                        if (user.getUserType() == AccountType.Contributor) {
                            ((Contributor<?>)user).setStrategy(new AddProductionActor());
                            user.setExperience(user.getExperience() + ((Contributor<?>)user).executeStrategy());
                            experience_label.setText("You have accumulated " + user.getExperience() + " experience points!");
                        }

                        MenuImdb.createActpanel(calibri, imdb_logo, images, cardLayout, panelCont, actor, new Font("Calibri", Font.BOLD, 25),
                                new Color(211, 211, 211), child_panels, all_components);
                        String[] contributions_str = new String[((Staff)user).getContributions().size()];
                        int i = 0;
                        for (Object o : ((Staff)user).getContributions()) {
                            if (o instanceof Actor) {
                                contributions_str[i] = ((Actor)o).name;
                            }
                            else {
                                contributions_str[i] = ((Production)o).title;
                            }
                            i++;
                        }
                        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>( contributions_str );
                        users_contributios.setModel( model );
                        cardLayout.show(panelCont, "3");
                    }
                }
                else if (chosen_obj.getSelectedItem().equals("Movie")) {
                    if ((nameProdAct[0] == null || biography[0] == null || icon[0] == null ||
                            directors_list.isEmpty() || actors_list.isEmpty() ||
                            genreList.isEmpty() || release_year_cr[0] == null || movie_dur_str[0] == null) &&
                            e.getSource() == create) {

                        not_found.setText("Complete fields!");
                        init_comp(not_found, calibri, Color.RED, 600, 400, 160, 20);
                    }
                    else if (e.getSource() == create) {
                        double rate = 0;
                        Movie movie = new Movie(release_year_cr[0], movie_dur_str[0], nameProdAct[0], genreList,
                                directors_list, actors_list, new ArrayList<>(), biography[0], rate, "Movie");
                        productions.add(movie);

                        if (user.getUserType() == AccountType.Contributor) {
                            ((Contributor<?>)user).setStrategy(new AddProductionActor());
                            user.setExperience(user.getExperience() + ((Contributor<?>)user).executeStrategy());
                            experience_label.setText("You have accumulated " + user.getExperience() + " experience points!");
                        }

                        not_found.setText("");
                        images.put(movie.title, icon[0]);
                        ((Staff)user).getContributions().add(movie);
                        MenuImdb.createProdpanel(calibri, new Font("Calibri", Font.BOLD, 25), imdb_logo,
                                images, cardLayout, panelCont, new Color(211, 211, 211), user,
                                productions.size() - 1, child_panels, all_components, prod_icons, experience_label);
                        String[] contributions_str = new String[((Staff)user).getContributions().size()];
                        int i = 0;
                        for (Object o : ((Staff)user).getContributions()) {
                            if (o instanceof Actor) {
                                contributions_str[i] = ((Actor)o).name;
                            }
                            else {
                                contributions_str[i] = ((Production)o).title;
                            }
                            i++;
                        }
                        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>( contributions_str );
                        users_contributios.setModel( model );
                        cardLayout.show(panelCont, "3");
                    }
                }
                else if (chosen_obj.getSelectedItem().equals("Series")) {
                    if ((nameProdAct[0] == null || biography[0] == null || icon[0] == null ||
                            directors_list.isEmpty() || actors_list.isEmpty() ||
                            genreList.isEmpty() || release_year_cr[0] == null || num_season[0] == 0 ||
                            seasons_map.isEmpty()) && e.getSource() == create) {

                        not_found.setText("Complete fields!");
                        init_comp(not_found, calibri, Color.RED, 600, 400, 160, 20);
                    }
                    else if (e.getSource() == create) {
                        double rate = 0;
                        Series series = new Series(num_season[0], release_year_cr[0], seasons_map, nameProdAct[0], genreList,
                                directors_list, actors_list, new ArrayList<>(), biography[0], rate, "Series");
                        productions.add(series);

                        if (user.getUserType() == AccountType.Contributor) {
                            ((Contributor<?>)user).setStrategy(new AddProductionActor());
                            user.setExperience(user.getExperience() + ((Contributor<?>)user).executeStrategy());
                            experience_label.setText("You have accumulated " + user.getExperience() + " experience points!");
                        }

                        not_found.setText("");
                        images.put(series.title, icon[0]);
                        ((Staff)user).getContributions().add(series);
                        MenuImdb.createProdpanel(calibri, new Font("Calibri", Font.BOLD, 25), imdb_logo,
                                images, cardLayout, panelCont, new Color(211, 211, 211), user,
                                productions.size() - 1, child_panels, all_components, prod_icons, experience_label);
                        String[] contributions_str = new String[((Staff)user).getContributions().size()];
                        int i = 0;
                        for (Object o : ((Staff)user).getContributions()) {
                            if (o instanceof Actor) {
                                contributions_str[i] = ((Actor)o).name;
                            }
                            else {
                                contributions_str[i] = ((Production)o).title;
                            }
                            i++;
                        }
                        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>( contributions_str );
                        users_contributios.setModel( model );
                        cardLayout.show(panelCont, "3");
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

        init_comp(directors_label, calibri, mustard, 100, 370, 100, 20);
        init_comp(directors, calibri, mustard, 230, 370, 200, 20);
        init_comp(add_director, calibri, mustard, 230, 400, 160, 20);

        init_comp(actors_label, calibri, mustard, 100, 450, 100, 20);
        init_comp(actors, calibri, mustard, 230, 450, 200, 20);
        init_comp(add_actor, calibri, mustard, 230, 490, 160, 20);

        init_comp(seasons_label, calibri, mustard, 630, 220, 100, 20);
        init_comp(seasons, calibri, mustard, 780, 220, 200, 20);
        init_comp(add_season, calibri, mustard, 1000, 210, 160, 20);

        init_comp(episode_label, calibri, mustard, 630, 270, 150, 20);
        init_comp(episode, calibri, mustard, 780, 270, 200, 20);
        init_comp(add_episode, calibri, mustard, 1000, 270, 160, 20);

        init_comp(ep_duration_label, calibri, mustard, 630, 320, 150, 20);
        init_comp(ep_duration, calibri, mustard, 780, 320, 200, 20);
        init_comp(add_ep_duration, calibri, mustard, 1000, 345, 160, 20);
        init_comp(create, calibri, mustard, 100, 700, 150, 30);

        init_comp(movie_time_label, calibri, mustard, 630, 360, 150, 20);
        init_comp(movie_time, calibri, mustard, 780, 360, 200, 20);
        init_comp(add_movie_time, calibri, mustard, 1000, 360, 160, 20);

        init_comp(upload_image, calibri, mustard, 100, 650, 150, 30);

        init_comp(release_year_label, calibri, mustard, 100, 540, 100, 20);
        init_comp(release_year, calibri, mustard, 230, 540, 200, 20);
        init_comp(add_release_year, calibri, mustard, 230, 590, 100, 20);

        set_components_invisible();

        this.add(chosen_obj);
        this.add(add_remove);
        this.add(genres_but);

        this.add(movie_time);
        this.add(movie_time_label);
        this.add(add_movie_time);

        this.add(name_label);
        this.add(name);
        this.add(add_name);
        this.add(upload_image);

        this.add(performance);
        this.add(performance_label);
        this.add(performance_but);
        this.add(create);

        this.add(release_year);
        this.add(release_year_label);
        this.add(add_release_year);

        this.add(add_ep_duration);


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

        this.add(ep_duration_label);
        this.add(ep_duration);
        this.add(choose_type);
        setLayout(null);
        setVisible(true);
        this.setBackground(Color.black);
    }

    public void init_comp(Component component, Font calibri, Color grey, int x, int y, int width, int height) {
        component.setBounds(x, y, width, height);
        component.setFont(calibri);
        if (!(component instanceof JTextField)) {
            component.setBackground(Color.black);
        }
        component.setForeground(grey);
    }


    public void set_components_invisible() {

        directors.setVisible(false);
        genres_but.setVisible(false);
        directors_label.setVisible(false);
        add_director.setVisible(false);
        choose_type.setVisible(false);

        movie_time.setVisible(false);
        movie_time_label.setVisible(false);
        add_movie_time.setVisible(false);

        actors_label.setVisible(false);
        actors.setVisible(false);
        add_actor.setVisible(false);

        release_year.setVisible(false);
        release_year_label.setVisible(false);
        add_release_year.setVisible(false);

        seasons.setVisible(false);
        seasons_label.setVisible(false);
        add_season.setVisible(false);

        ep_duration.setVisible(false);
        ep_duration_label.setVisible(false);

        episode.setVisible(false);
        episode_label.setVisible(false);
        add_episode.setVisible(false);

        performance_label.setVisible(false);
        performance.setVisible(false);
        performance_but.setVisible(false);
        add_ep_duration.setVisible(false);

        for (JCheckBox c : genres) {
            c.setVisible(false);
        }
    }
}
