package org.example.swing;

import org.example.*;
import org.example.actors.Actor;
import org.example.actors.Performance;
import org.example.buttons.*;
import org.example.painters.CellPainter;
import org.example.painters.ProdIcon;
import org.example.productions.*;
import org.example.strategy.AddReview;
import org.example.users.AccountType;
import org.example.users.Regular;
import org.example.users.Staff;
import org.example.users.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

public class MenuImdb {
    User user;
    JPanel app_menu;
    JPanel panelCont;
    CardLayout cardLayout;
    JTextField searchbar;
    JButton logo;
    JButton search_button;
    JPanel search_panel;
    JPanel scroll_panel;
    JLabel experience_label;
    Map<String, Component> child_panels;
    Map<String, ProdIcon> prod_icons;
    Map<String, ProdIcon> fav_icons;
    Map<String, ImageIcon> images;

    JFrame frame;

    public MenuImdb(JPanel app_menu, CardLayout cardLayout, JPanel panelCont, User user, JFrame frame, Map<String, ImageIcon> images) {
        this.app_menu = app_menu;
        this.images = images;
        this.cardLayout = cardLayout;
        this.panelCont = panelCont;
        searchbar = new JTextField();
        logo = new JButton();
        search_button = new JButton("Search");
        search_panel = new JPanel();
        scroll_panel = new JPanel();
        this.user = user;
        child_panels = new HashMap<>();
        prod_icons = new HashMap<>();
        fav_icons = new HashMap<>();
        this.frame = frame;
    }

    public void menu_imdb() {
        ImageIcon imdb_logo = new ImageIcon("src/main/java/org/example/images/imdb_icon.jpg");
        imdb_logo.setImage(imdb_logo.getImage().getScaledInstance(100, 40, Image.SCALE_DEFAULT));
        Font calibri = new Font("Calibri", Font.PLAIN, 15);
        Color grey = new Color(211, 211, 211);
        Font cal_bold = new Font("Calibri", Font.BOLD, 25);
        experience_label = new JLabel();

        search_panel.setLayout(new FlowLayout());

        ImageIcon notifications_im = new ImageIcon("src/main/java/org/example/images/notifications.jpg");
        notifications_im.setImage(notifications_im.getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT));

        JPopupMenu notifications = new JPopupMenu();
        for (Object notif : user.getNotifications()) {
            JMenuItem notification = new JMenuItem((String) notif);
            notifications.add(notification);
        }

        JButton notifications_button = new JButton();
        notifications_button.setIcon(notifications_im);
        notifications_button.setPreferredSize(new Dimension(40, 40));
        notifications_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                notifications.show(notifications_button, 0, notifications_button.getHeight());
            }
        });

        logo.setIcon(imdb_logo);
        logo.setPreferredSize(new Dimension(100, 40));
        logo.addActionListener(new LogoButton(logo, cardLayout, panelCont));

        set_searchbar(calibri, searchbar, search_button, images, cardLayout, panelCont, imdb_logo);

        search_panel.setBackground(new Color(20, 15, 15));
        search_panel.add(notifications_button);
        search_panel.add(logo);
        search_panel.add(searchbar);
        search_panel.add(search_button);

        List<Production> productions = IMDB.getInstance().getProductions();
        List<Actor> actors = IMDB.getInstance().getActors();
        Map<String, Map<String, Component>> all_components = new HashMap<>();
        for (int i = 0; i < productions.size() + 1; i++) {
            if (i < productions.size()) {
                createProdpanel(calibri, cal_bold, imdb_logo, images, cardLayout, panelCont, grey, user,
                        i, child_panels, all_components, prod_icons, experience_label);
            } else {
                JPanel searched_panel = createPanel(calibri, imdb_logo, images, cardLayout, panelCont);
                JLabel no_results = new JLabel("No results!");
                no_results.setBounds(200, 350, 200, 40);
                no_results.setFont(new Font("Arial", Font.PLAIN, 40));
                searched_panel.add(no_results);
                searched_panel.setBackground(Color.WHITE);
                panelCont.add(searched_panel, "Not found");
            }
        }

        for (Actor a : actors) {
            createActpanel(calibri, imdb_logo, images, cardLayout, panelCont, a, cal_bold, grey, child_panels, all_components);
        }

        JPanel left = new JPanel();
        JPanel right = new JPanel();
        JPanel center = new JPanel();
        JPanel down = new JPanel();
        left.setPreferredSize(new Dimension(200, 400));
        right.setPreferredSize(new Dimension(200, 400));
        center.setPreferredSize(new Dimension(800, 500));
        down.setPreferredSize(new Dimension(800, 600));
        left.setBackground(Color.BLACK);
        right.setBackground(Color.BLACK);
        down.setBackground(Color.BLACK);
        right.setBackground(Color.BLACK);
        center.setBackground(Color.BLACK);
        down.setLayout(new BorderLayout());

        JPanel center_up = new JPanel();
        center_up.setBackground(Color.BLACK);
        center_up.setPreferredSize(new Dimension(800, 200));
        center.setLayout(new BorderLayout());

        center_up.setLayout(new BorderLayout());

        String str = "Welcome back " + user.getUsername() + "! You have logged in as a " +
                user.getUserType() + "!";

        JLabel welcome_back = init_label(str, new Dimension(80, 40), cal_bold, grey);
        JLabel message_picks = init_label("Top 10 picks:", new Dimension(60, 40), cal_bold, grey);
        center_up.add(welcome_back, BorderLayout.NORTH);
        center_up.add(message_picks, BorderLayout.SOUTH);


        DefaultListModel topPicks = top_picks(images, prod_icons);
        JScrollPane jp = getScrollPane(topPicks);
        jp.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 10));
        jp.getHorizontalScrollBar().setBackground(Color.BLACK);


        DefaultListModel others_fav = others_favorites(images, fav_icons);
        JScrollPane jScrollPane = getScrollPane(others_fav);
        jScrollPane.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 10));
        jScrollPane.getHorizontalScrollBar().setBackground(Color.BLACK);

        JPanel down_up = new JPanel();
        JPanel down_left = new JPanel();
        JPanel down_right = new JPanel();
        JPanel down_down = new JPanel();

        String spaces = new String(new char[33]).replace('\0', ' ');
        down_up.setPreferredSize(new Dimension(800, 200));
        down_left.setPreferredSize(new Dimension(200, 300));
        down_right.setPreferredSize(new Dimension(200, 300));
        down_down.setPreferredSize(new Dimension(1200, 300));
        down_up.setBackground(Color.BLACK);
        down_left.setBackground(Color.BLACK);
        down_right.setBackground(Color.BLACK);
        down_down.setBackground(Color.BLACK);

        scroll_panel.setBackground(Color.BLACK);
        scroll_panel.setVisible(true);
        scroll_panel.setLayout(new BorderLayout());

        ImageIcon menu_img = new ImageIcon("src/main/java/org/example/images/menu.jpg");
        menu_img.setImage(menu_img.getImage().getScaledInstance(120, 40, Image.SCALE_SMOOTH));

        JPopupMenu menu = new JPopupMenu();
        JMenuItem sign_off = new JMenuItem("Sign off");
        sign_off.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == sign_off) {
                    frame.dispose();
                    LoginInterface loginInterface = new LoginInterface(images);
                }
            }
        });
        JMenuItem update_menu = new JMenuItem("Update contribution");

        JMenuItem add_system = new JMenuItem("Add in system");

        JMenuItem add_remove_request = new JMenuItem("Add/Remove request");

        CreateRequestSwing add_cr_request_panel = new CreateRequestSwing(user, cardLayout, panelCont);
        panelCont.add(add_cr_request_panel, "create_rem_req");
        add_remove_request.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(panelCont, "create_rem_req");
            }
        });

        JMenuItem remove_from_system = new JMenuItem("Remove contribution");

        JButton menu_button = new JButton();
        menu_button.setIcon(menu_img);
        menu_button.setPreferredSize(new Dimension(100, 40));
        menu_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                menu.show(menu_button, 0, menu_button.getHeight());
            }
        });

        JMenuItem favorites_men = new JMenuItem("Add/Remove favorites");
        AddFavoritesSwin add_fav = new AddFavoritesSwin(cardLayout, panelCont, user);
        panelCont.add(add_fav, "add_fav");
        favorites_men.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(panelCont, "add_fav");
            }
        });

        menu.add(sign_off);
        menu.add(add_remove_request);
        menu.add(favorites_men);
        search_panel.add(menu_button);

        if (user.getUserType() != AccountType.Regular) {
            String[] contributions_str = new String[((Staff) user).getContributions().size() + 1];
            int i = 1;
            contributions_str[0] = "-";
            for (Object o : ((Staff) user).getContributions()) {
                if (o instanceof Actor) {
                    contributions_str[i] = ((Actor) o).name;
                } else {
                    contributions_str[i] = ((Production) o).title;
                }
                i++;
            }
            JComboBox user_contributions_box = new JComboBox<>(contributions_str);
            RemoveSwing removeSwing = new RemoveSwing(all_components, prod_icons, child_panels, user,
                    cardLayout, panelCont, images, fav_icons, user_contributions_box, add_fav.yours_favorites);
            panelCont.add(removeSwing, "remove_system");
            remove_from_system.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    cardLayout.show(panelCont, "remove_system");
                }
            });

            UpdatePanel updatePanel = new UpdatePanel(all_components, cardLayout, panelCont, child_panels, images, user,
                    prod_icons, removeSwing.choose_rem, add_fav.yours_favorites, user_contributions_box);
            panelCont.add(updatePanel, "update");
            update_menu.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    cardLayout.show(panelCont, "update");
                }
            });
            JComboBox user_contributions = updatePanel.getUser_contributions();

            AddSystem addSystem = new AddSystem(all_components, cardLayout, panelCont, child_panels, images, user,
                    imdb_logo, user_contributions, prod_icons, experience_label);
            panelCont.add(addSystem, "add_system");
            add_system.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    cardLayout.show(panelCont, "add_system");
                }
            });

            JMenuItem solve_req = new JMenuItem("Solve requests");
            RequestSwing solve_panel = new RequestSwing(user, cardLayout, panelCont, experience_label);
            panelCont.add(solve_panel, "solve_req");
            solve_req.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    cardLayout.show(panelCont, "solve_req");
                }
            });

            menu.add(solve_req);
            menu.add(update_menu);
            menu.add(add_system);
            menu.add(remove_from_system);
        }
        if (user.getUserType() == AccountType.Admin) {
            JMenuItem add_user = new JMenuItem("Add User");
            AddUserSwing addUserSwing = new AddUserSwing(cardLayout, panelCont);
            panelCont.add(addUserSwing, "add_user");

            add_user.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    cardLayout.show(panelCont, "add_user");
                }
            });

            JMenuItem remove_user = new JMenuItem("Remove User");
            RemoveUserSwing removeUserSwing = new RemoveUserSwing(cardLayout, panelCont, user);
            panelCont.add(removeUserSwing, "remove_user");

            remove_user.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    cardLayout.show(panelCont, "remove_user");
                }
            });
            menu.add(add_user);
            menu.add(remove_user);
        }

        down.add(down_right, BorderLayout.WEST);
        down.add(down_up, BorderLayout.NORTH);
        down.add(down_down, BorderLayout.SOUTH);
        down.add(down_left, BorderLayout.EAST);

        JLabel mess_fav = init_label(spaces + "Fan favorites:", new Dimension(60, 87), cal_bold, grey);
        down.add(jScrollPane, BorderLayout.CENTER);
        down.add(mess_fav, BorderLayout.NORTH);

        if (user.getUserType() != AccountType.Admin) {
            experience_label.setText("You have accumulated " + user.getExperience() + " experience points!");
            experience_label.setForeground(grey);
            experience_label.setFont(cal_bold);
            center_up.add(experience_label, BorderLayout.CENTER);
        }

        center.add(center_up, BorderLayout.CENTER);
        center.add(jp, BorderLayout.SOUTH);

        scroll_panel.add(left, BorderLayout.WEST);
        scroll_panel.add(right, BorderLayout.EAST);
        scroll_panel.add(center, BorderLayout.CENTER);
        scroll_panel.add(down, BorderLayout.SOUTH);

        scroll_panel.revalidate();

        scroll_panel.add(search_panel, BorderLayout.NORTH);
        JScrollPane scrollPane = new JScrollPane(scroll_panel,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        app_menu.setLayout(new BorderLayout());
        app_menu.add(scrollPane);

    }

    private static void set_borders_panel(JPanel searched_panel, JPanel main_panel, JPanel left, JPanel center, JPanel right) {
        searched_panel.add(left, BorderLayout.WEST);
        searched_panel.add(right, BorderLayout.EAST);
        searched_panel.add(center, BorderLayout.CENTER);

        JScrollPane scrollPane = new JScrollPane(searched_panel,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        main_panel.setLayout(new BorderLayout());
        main_panel.add(scrollPane, BorderLayout.CENTER);
    }

    public static void set_searchbar(Font calibri, JTextField searchbar, JButton searchButton, Map<String, ImageIcon> images,
                                     CardLayout cardLayout, JPanel panelCont, ImageIcon imdb_logo) {
        searchbar.setPreferredSize(new Dimension(500, 30));
        searchbar.setFont(calibri);
        searchbar.addKeyListener(new SearchKey(searchbar, cardLayout, panelCont, images, imdb_logo));

        searchButton.setPreferredSize(new Dimension(125, 30));
        searchButton.setFont(calibri);
        searchButton.addActionListener(new SearchButton(searchButton, panelCont,
                cardLayout, searchbar, images, imdb_logo));
    }


    public JScrollPane getScrollPane(DefaultListModel topPicks) {
        CellPainter cellPainter = new CellPainter(cardLayout, panelCont);
        JList jList = new JList<>(topPicks);
        jList.setCellRenderer(cellPainter);
        jList.setFixedCellWidth(190);
        jList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jList.setVisibleRowCount(1);
        jList.setLayoutOrientation(JList.HORIZONTAL_WRAP);

        JScrollPane jp = new JScrollPane(jList, ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        return jp;
    }

    public static void createActpanel(Font calibri, ImageIcon imdb_logo, Map<String, ImageIcon> images,
                                      CardLayout cardLayout, JPanel panelCont, Actor a, Font cal_bold, Color grey, Map<String,
            Component> child_panels, Map<String, Map<String, Component>> all_components) {

        Map<String, Component> actor_components = new HashMap<>();
        String plot_str = "<html>";
        if (a.biography != null) {
            int remain = 0;
            int count = 0;
            for (int j = 0; j < a.biography.length(); j++) {
                if (count > 50 && a.biography.charAt(j) == ' ') {
                    plot_str += a.biography.substring(remain, j) + "<br>";
                    remain = j;
                    count = 0;
                }
                count++;
            }
            plot_str += a.biography.substring(remain);
            plot_str += "</html>";
        }

        JPanel searched_panel = createPanel(calibri, imdb_logo, images, cardLayout, panelCont);
        JPanel main_panel = new JPanel();
        JPanel image_title_plot = new JPanel();
        JPanel left = new JPanel();
        JPanel center = new JPanel();
        JPanel right = new JPanel();
        JLabel portrait = new JLabel();
        JPanel performances_label = new JPanel();
        JLabel performances = init_label("Performances:", new Dimension(50, 40), cal_bold, grey);
        JLabel title_text = init_label(a.name, new Dimension(900, 30),
                new Font("Calibri", Font.BOLD, 20), grey);
        JLabel plot = new JLabel(plot_str);
        plot.setForeground(grey);
        plot.setFont(calibri);
        actor_components.put("biography", plot);
        actor_components.put("actor_name", title_text);

        performances_label.setLayout(new BoxLayout(performances_label, BoxLayout.Y_AXIS));

        left.setPreferredSize(new Dimension(10, 500));
        right.setPreferredSize(new Dimension(10, 500));
        center.setPreferredSize(new Dimension(1180, 800 + a.performances.size() * 80));
        left.setBackground(Color.black);
        right.setBackground(Color.black);
        center.setBackground(Color.black);

        image_title_plot.setLayout(new BoxLayout(image_title_plot, BoxLayout.Y_AXIS));
        image_title_plot.setBackground(Color.black);

        portrait.setIcon(images.get(a.name));
        portrait.setPreferredSize(new Dimension(300, 400));

        String s = "<html>";
        for (Performance p : a.performances) {
            s += "Title: " + p.title + ";  Type:" + p.type + "<br>";
        }
        s += "</html>";
        JLabel all_performances = new JLabel();
        all_performances.setText(s);
        all_performances.setFont(new Font("Arial", Font.PLAIN, 20));
        all_performances.setForeground(grey);
        actor_components.put("performances", all_performances);

        performances_label.add(performances);
        performances_label.add(all_performances);
        performances_label.setBackground(Color.black);

        JLabel spacing = new JLabel("<html><br></html>");
        spacing.setPreferredSize(new Dimension(50, 80));

        image_title_plot.add(title_text);
        image_title_plot.add(portrait);
        image_title_plot.add(plot);
        image_title_plot.add(spacing);
        image_title_plot.add(performances_label);

        center.add(image_title_plot, BorderLayout.CENTER);
        set_borders_panel(searched_panel, main_panel, left, center, right);
        panelCont.add(main_panel, a.name);
        child_panels.put(a.name, main_panel);
        all_components.put(a.name, actor_components);
    }

    public static void createProdpanel(Font calibri, Font cal_bold, ImageIcon imdb_logo, Map<String, ImageIcon> images,
                                       CardLayout cardLayout, JPanel panelCont, Color grey, User user, int i,
                                       Map<String, Component> child_panels, Map<String, Map<String, Component>> all_components,
                                       Map<String, ProdIcon> prod_icons, JLabel experience_label) {

        List<Production> productions = IMDB.getInstance().getProductions();
        JPanel searched_panel = createPanel(calibri, imdb_logo, images, cardLayout, panelCont);
        JPanel main_panel = new JPanel();
        Map<String, Component> components_each_prod = new HashMap<>();
        Production p = productions.get(i);
        Long releaseYear;
        if (p instanceof Series) {
            releaseYear = ((Series) p).getReleaseYear();
        } else {
            releaseYear = ((Movie) p).getReleaseYear();
        }

        String plot_str = "<html>";
        int remain = 0;
        int count = 0;
        for (int j = 0; j < p.getPlot().length(); j++) {
            if (count > 50 && p.getPlot().charAt(j) == ' ') {
                plot_str += p.getPlot().substring(remain, j) + "<br>";
                remain = j;
                count = 0;
            }
            count++;
        }
        plot_str += p.getPlot().substring(remain);
        plot_str += "</html>";
        JPanel image_title_plot = new JPanel();
        JPanel left = new JPanel();
        JPanel center = new JPanel();
        JPanel right = new JPanel();
        JLabel portrait = new JLabel();
        JLabel title_text = init_label(p.title, new Dimension(900, 30),
                new Font("Calibri", Font.BOLD, 20), grey);

        JLabel plot = init_label(plot_str, new Dimension(100, 120), calibri, grey);
        JLabel genres = init_label("Genres", new Dimension(40, 50), cal_bold, grey);
        JLabel averagerating = init_label("Rating: " + p.getAverageRating(),
                new Dimension(50, 60), cal_bold, grey);

        JLabel genres_name = init_label("", new Dimension(70, 40), calibri, grey);
        JLabel userrating = init_label("User ratings:", new Dimension(60, 30), cal_bold, grey);

        left.setPreferredSize(new Dimension(10, 500));
        right.setPreferredSize(new Dimension(10, 500));

        int size_season_series = 0;
        if (p instanceof Series) {
            for (Map.Entry<String, List<Episode>> entry : ((Series) p).getSeasons().entrySet()) {
                List<Episode> episodes = entry.getValue();
                size_season_series += episodes.size();
            }
        }

        left.setBackground(Color.black);
        right.setBackground(Color.black);
        center.setBackground(Color.black);

        image_title_plot.setLayout(new BoxLayout(image_title_plot, BoxLayout.Y_AXIS));
        if (p instanceof Movie) {
            image_title_plot.setPreferredSize(new Dimension(500, 800 + p.ratings.size() * 140));
        } else {
            image_title_plot.setPreferredSize(new Dimension(500, 800 + p.ratings.size() * 140
                    + size_season_series * 30));
        }
        image_title_plot.setBackground(Color.black);

        JPanel directors_panel = new JPanel();
        JLabel directors = new JLabel("Directors");
        JLabel name_directors = new JLabel();

        JPanel actors_panel = new JPanel();
        JLabel actors_label = new JLabel("Top Cast");
        JLabel name_actors = new JLabel();

        set_actors_directors(directors, directors_panel, name_directors, p.getDirectors(), grey, calibri, cal_bold);
        components_each_prod.put("directors", name_directors);
        if (p instanceof Movie) {
            directors_panel.setPreferredSize(new Dimension(300, 500 + p.ratings.size() * 140));
        } else {
            directors_panel.setPreferredSize(new Dimension(300, 500 + p.ratings.size() * 140
                    + size_season_series * 30));
        }
        set_actors_directors(actors_label, actors_panel, name_actors, p.getActors(), grey, calibri, cal_bold);
        components_each_prod.put("actors", name_actors);
        if (p instanceof Movie) {
            actors_panel.setPreferredSize(new Dimension(300, 500 + p.ratings.size() * 140));
        } else {
            actors_panel.setPreferredSize(new Dimension(300, 500 + p.ratings.size() * 140
                    + size_season_series * 30));
        }

        portrait.setIcon(images.get(p.title));

        components_each_prod.put("title", title_text);
        image_title_plot.add(title_text);

        if (releaseYear != null) {
            JLabel release_year;
            if (p instanceof Movie) {
                release_year = new JLabel(releaseYear + ",  " + ((Movie) p).getDuration());
            } else {
                release_year = new JLabel(releaseYear.toString());
            }
            release_year.setFont(calibri);
            release_year.setForeground(grey);

            components_each_prod.put("releaseYear", release_year);
            image_title_plot.add(release_year);
        }

        String genres_str = "";
        int j = 0;
        for (Genre g : p.getGenres()) {
            j++;
            genres_str += g.toString();
            if (j < p.getGenres().size()) {
                genres_str += ", ";
            }
        }
        genres_name.setText(genres_str);

        image_title_plot.add(portrait);

        image_title_plot.add(plot);
        components_each_prod.put("plot", plot);

        image_title_plot.add(genres);

        image_title_plot.add(genres_name);
        components_each_prod.put("genres", genres_name);

        image_title_plot.add(averagerating);

        image_title_plot.add(userrating);

        JPanel ratings = new JPanel();
        ratings.setLayout(new BoxLayout(ratings, BoxLayout.Y_AXIS));
        ratings.setBackground(Color.BLACK);
        int already_rated = 0;

        for (int k = 0; k < p.ratings.size(); k++) {
            if (p.ratings.get(k).getUsername().equals(user.getUsername())) {
                already_rated = 1;
            }
            JPanel rating_panel = user_ratings(p.ratings.get(k), calibri, grey);
            ratings.add(rating_panel);
        }

        image_title_plot.add(ratings);
        JPanel holder = new JPanel();
        holder.setBackground(Color.black);
        holder.setLayout(new BoxLayout(holder, BoxLayout.Y_AXIS));
        if (p instanceof Series) {
            for (Map.Entry<String, List<Episode>> entry : ((Series) p).getSeasons().entrySet()) {
                String seasonName = entry.getKey();
                List<Episode> episodes = entry.getValue();
                JPanel season_panel = season_panel(seasonName, episodes, cal_bold, calibri, grey, components_each_prod);
                components_each_prod.put("panel" + seasonName, season_panel);
                holder.add(season_panel);
            }
        }
        components_each_prod.put("holder", holder);
        image_title_plot.add(holder);

        JButton rating = new JButton("Add rating to this production");
        rating.setFont(calibri);
        rating.setBackground(new Color(219, 165, 5));
        rating.setBorderPainted(false);
        rating.setForeground(Color.BLACK);
        rating.setPreferredSize(new Dimension(60, 30));
        if (already_rated != 0) {
            rating.setVisible(false);
        }

        JButton remove_rating = new JButton("Remove rating");
        remove_rating.setFont(calibri);
        remove_rating.setBackground(new Color(219, 165, 5));
        remove_rating.setBorderPainted(false);
        remove_rating.setForeground(Color.BLACK);
        remove_rating.setPreferredSize(new Dimension(60, 30));
        if (already_rated == 0) {
            remove_rating.setVisible(false);
        }

        if (user.getUserType() == AccountType.Regular) {
            rating.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (e.getSource() == rating) {
                        RatingSwing ratingSwing = new RatingSwing(cardLayout, panelCont, user, images, prod_icons,
                                ratings, p, averagerating, experience_label);
                        panelCont.add(ratingSwing, "rating");
                        remove_rating.setVisible(true);
                        rating.setVisible(false);
                        cardLayout.show(panelCont, "rating");
                    }
                }
            });
            remove_rating.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (e.getSource() == remove_rating) {
                        double mark = 0;
                        int index = 0;
                        int i = 0;
                        if (!p.ratings.isEmpty()) {
                            for (Rating r : p.ratings) {
                                if (r.getUsername().equals(user.getUsername())) {
                                    index = i;
                                }
                                i++;
                            }
                        }
                        Rating rat = p.ratings.get(index);
                        rat.notifyObservers("The rating from the production " +
                                p.title + " was deleted");
                        ((Regular<?>)user).setStrategy(new AddReview());
                        user.setExperience(user.getExperience() - ((Regular<?>)user).executeStrategy());
                        experience_label.setText("You have accumulated " + user.getExperience() + " experience points!");
                        p.ratings.remove(index);
                        if (!p.ratings.isEmpty()) {
                            for (Rating r : p.ratings) {
                                mark += r.getRating();
                            }
                            p.setAverageRating((mark / p.ratings.size()));
                            averagerating.setText("Rating: " + p.getAverageRating());
                        } else {
                            averagerating.setText("Rating: 0");
                            double x = 0;
                            p.setAverageRating(x);
                        }
                        MenuImdb.update_lists_menu(images, prod_icons);
                        ratings.remove(index);
                        remove_rating.setVisible(false);
                        rating.setVisible(true);
                    }
                }
            });

            image_title_plot.add(rating);
            image_title_plot.add(remove_rating);
        }

        center.add(image_title_plot, BorderLayout.WEST);
        center.add(directors_panel, BorderLayout.CENTER);
        center.add(actors_panel, BorderLayout.EAST);

        set_borders_panel(searched_panel, main_panel, left, center, right);
        all_components.put(p.title, components_each_prod);
        panelCont.add(main_panel, p.title);
        child_panels.put(p.title, main_panel);
    }

    public static JPanel createPanel(Font calibri, ImageIcon imdb_logo, Map<String, ImageIcon> images, CardLayout cardLayout,
                                     JPanel panelCont) {
        JPanel searched_panel = new JPanel();
        searched_panel.setLayout(new BorderLayout());
        searched_panel.setVisible(true);


        JPanel logo_panel = new JPanel();
        logo_panel.setLayout(new FlowLayout());
        logo_panel.setBackground(new Color(20, 15, 15));
        searched_panel.setBackground(Color.BLACK);

        JButton logo_each_panel = new JButton();
        JTextField searchbar_each_panel = new JTextField();
        JButton search_button_each_panel = new JButton("Search");

        set_searchbar(calibri, searchbar_each_panel, search_button_each_panel, images, cardLayout, panelCont, imdb_logo);

        logo_each_panel.setIcon(imdb_logo);
        logo_each_panel.setPreferredSize(new Dimension(100, 40));
        logo_each_panel.addActionListener(new LogoButton(logo_each_panel, cardLayout, panelCont));

        logo_panel.add(logo_each_panel);
        logo_panel.add(searchbar_each_panel);
        logo_panel.add(search_button_each_panel);
        searched_panel.add(logo_panel, BorderLayout.NORTH);

        return searched_panel;
    }

    public static void set_actors_directors(JLabel directors, JPanel directors_panel, JLabel name_directors, List<String> str_list,
                                            Color grey, Font calibri, Font cal_bold) {
        directors.setFont(cal_bold);
        directors.setForeground(grey);

        directors_panel.setLayout(new BoxLayout(directors_panel, BoxLayout.Y_AXIS));
        directors_panel.setBackground(Color.black);

        String name_directors_str = "<html>";
        for (String s : str_list) {
            name_directors_str += s;
            name_directors_str += "<br>";
        }
        name_directors_str += "</html>";
        name_directors.setText(name_directors_str);
        name_directors.setFont(calibri);
        name_directors.setForeground(grey);

        directors_panel.add(directors);
        directors_panel.add(name_directors);
    }

    public DefaultListModel others_favorites(Map<String, ImageIcon> images, Map<String, ProdIcon> fav_icons) {
        DefaultListModel others_favorites = new DefaultListModel();
        List<User> users = IMDB.getInstance().getUsers();
        int count = 0;
        for (User u : users) {
            if (u.getFavorites() != null) {
                if (!u.getFavorites().isEmpty()) {
                    if (count >= 9) {
                        break;
                    }
                    if (u != user) {
                        for (Object o : u.getFavorites()) {
                            if (count >= 9) {
                                break;
                            }
                            if (o instanceof Production) {
                                ImageIcon menu_im = new ImageIcon();
                                menu_im.setImage(images.get(((Production) o).title).getImage().getScaledInstance(180, 200, Image.SCALE_SMOOTH));
                                ProdIcon prodIcon = new ProdIcon(((Production) o).title, menu_im);
                                fav_icons.put(((Production) o).title, prodIcon);
                                others_favorites.addElement(prodIcon);
                                count++;
                            }
                        }
                    }
                }
            }
        }

        return others_favorites;
    }

    public DefaultListModel top_picks(Map<String, ImageIcon> images, Map<String, ProdIcon> prod_icons) {
        DefaultListModel topPicks = new DefaultListModel();
        List<Production> productions = IMDB.getInstance().getProductions();
        Collections.sort(productions, new Comparator<Production>(){
            public int compare(Production o1, Production o2){
                return Double.compare(o2.getAverageRating(), o1.getAverageRating());
            }
        });
        for (int i = 0; i < 10; i++) {
            Production best_rated = productions.get(i);

            ImageIcon menu_im = new ImageIcon();
            menu_im.setImage(images.get(best_rated.title).getImage().getScaledInstance(180, 200, Image.SCALE_SMOOTH));
            ProdIcon prodIcon = new ProdIcon(best_rated.title, menu_im);
            prod_icons.put(best_rated.title, prodIcon);
            topPicks.addElement(prodIcon);
        }

        return topPicks;
    }

    public static void update_lists_menu(Map<String, ImageIcon> images, Map<String, ProdIcon> prod_icons) {
        List<Production> productions = IMDB.getInstance().getProductions();
        Collections.sort(productions, new Comparator<Production>(){
            public int compare(Production o1, Production o2){
                return Double.compare(o2.getAverageRating(), o1.getAverageRating());
            }
        });
        Production production_max = productions.get(0);
        for (Production production : productions) {
            if (!prod_icons.containsKey(production.title)) {
                production_max = production;
                break;
            }
        }
        assert production_max != null;
        int was_removed = 0;
        for (Map.Entry<String, ProdIcon> entry : prod_icons.entrySet()) {
            if (entry.getValue().getProduction().equals("removed")) {
                was_removed = 1;
                ImageIcon new_img = new ImageIcon();
                new_img.setImage(images.get(production_max.title).getImage().getScaledInstance(180, 200, Image.SCALE_SMOOTH));
                ProdIcon prodIcon = entry.getValue();
                prodIcon.setImageIcon(new_img);
                prodIcon.setProduction(production_max.title);
                prod_icons.remove(entry.getKey());
                prod_icons.put(production_max.title, prodIcon);
                break;
            }
        }
        if (was_removed == 0) {
            for (Map.Entry<String, ProdIcon> entry : prod_icons.entrySet()) {
                Production p = ParseInput.get_production_byname(entry.getKey());
                if (p.getAverageRating() < production_max.getAverageRating()) {
                    ImageIcon new_img = new ImageIcon();
                    new_img.setImage(images.get(production_max.title).getImage().getScaledInstance(180, 200, Image.SCALE_SMOOTH));
                    ProdIcon prodIcon = prod_icons.get(p.title);
                    prodIcon.setImageIcon(new_img);
                    prodIcon.setProduction(production_max.title);
                    prod_icons.remove(p.title);
                    prod_icons.put(production_max.title, prodIcon);
                    break;
                }
            }
        }
    }

    public static JPanel user_ratings(Rating r, Font calibri, Color grey) {
        JPanel rating = new JPanel();
        rating.setLayout(new BoxLayout(rating, BoxLayout.Y_AXIS));
        rating.setPreferredSize(new Dimension(800, 160));
        rating.setBackground(Color.black);

        JLabel username = new JLabel("Username: " + r.getUsername());
        JLabel rate = new JLabel("Rating: " + r.getRating());
        JLabel comment = new JLabel("Comment: " + r.getComment());

        username.setPreferredSize(new Dimension(100, 20));
        rate.setPreferredSize(new Dimension(60, 20));
        comment.setPreferredSize(new Dimension(500, 20));

        username.setFont(calibri);
        rate.setFont(calibri);
        comment.setFont(calibri);

        username.setForeground(grey);
        rate.setForeground(grey);
        comment.setForeground(grey);

        rating.add(username);
        rating.add(rate);
        rating.add(comment);

        rating.setVisible(true);
        return rating;
    }

    public static JPanel season_panel(String s, List<Episode> episodes, Font cal_bold, Font calibri, Color grey,
                                      Map<String, Component> componentMap) {
        JPanel season = new JPanel();
        season.setLayout(new BoxLayout(season, BoxLayout.Y_AXIS));
        season.setBackground(Color.black);
        JLabel season_name = new JLabel(s + ":");
        season_name.setFont(cal_bold);
        season_name.setForeground(grey);
        componentMap.put(s, season_name);

        season.add(season_name);

        for (Episode e : episodes) {
            JLabel episode_name = new JLabel("         " + e.getEpisodeName() + ";  duration: " + e.getDuration());
            episode_name.setForeground(grey);
            episode_name.setFont(calibri);
            episode_name.setLayout(new FlowLayout());
            season.add(episode_name);
            componentMap.put(e.getEpisodeName(), episode_name);
        }

        return season;
    }

    public static JLabel init_label(String text, Dimension d, Font font, Color grey) {
        JLabel new_label = new JLabel(text);
        new_label.setPreferredSize(d);
        new_label.setForeground(grey);
        new_label.setFont(font);
        return new_label;
    }
}
