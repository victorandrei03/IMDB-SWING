package org.example.swing;

import org.example.*;
import org.example.requests.Request;
import org.example.requests.RequestType;
import org.example.requests.RequestsHolder;
import org.example.strategy.ResolveRequest;
import org.example.users.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class RequestSwing extends JPanel {
    JButton solve_req;
    JButton remove_req;
    User user;
    JComboBox choose_location;
    JComboBox select_user;
    JButton back_button;
    JLabel username;
    JLabel request_type;
    JLabel date;
    JLabel description;
    JLabel mess;
    JLabel act_movie;

    public RequestSwing(User user, CardLayout cardLayout, JPanel panelCont, JLabel experience_label) {
        this.user = user;
        solve_req = new JButton("Solve request");
        select_user = new JComboBox<>();
        Font calibri = new Font("Calibri", Font.PLAIN, 18);
        Color mustard = new Color(219, 165, 5);
        username = new JLabel();
        request_type = new JLabel();
        date = new JLabel();
        description = new JLabel();
        act_movie = new JLabel();
        back_button = new JButton();
        remove_req = new JButton("Reject request");

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
        this.add(back_button);

        String[] objects;
        if (user.getUserType() == AccountType.Admin) {
            objects = new String[]{"-", "Your list", "The common list of admins"};
        } else {
            objects = new String[]{"-", "Your list"};
        }
        choose_location = new JComboBox<>(objects);
        init_comp(choose_location, calibri, mustard, 400, 75, 300, 30);

        init_comp(select_user, calibri, mustard, 400, 115, 300, 30);
        init_comp(solve_req, calibri, mustard, 100, 550, 300, 30);
        init_comp(remove_req, calibri, mustard, 100, 600, 300, 30);
        mess = new JLabel("Request solved!");
        init_comp(mess, calibri, Color.GREEN, 400, 700, 500, 25);
        mess.setVisible(false);
        set_invisible();

        choose_location.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (choose_location.getSelectedItem().equals("Your list")) {
                    String[] contributions_st = new String[((Staff) user).getRequests().size() + 1];
                    contributions_st[0] = "-";
                    int i = 1;
                    for (Object request : ((Staff) user).getRequests()) {
                        contributions_st[i] = ((Request) request).username;
                        i++;
                    }
                    DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(contributions_st);
                    select_user.setModel(model);
                    select_user.setSelectedItem("-");
                } else if (choose_location.getSelectedItem().equals("The common list of admins")) {
                    String[] contributions_st = new String[RequestsHolder.requests.size() + 1];
                    contributions_st[0] = "-";
                    int i = 1;
                    for (Request request : RequestsHolder.requests) {
                        contributions_st[i] = request.username;
                        i++;
                    }
                    DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(contributions_st);
                    select_user.setModel(model);
                    select_user.setSelectedItem("-");
                }
            }
        });

        select_user.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                set_invisible();
                username.setVisible(true);
                date.setVisible(true);
                description.setVisible(true);
                solve_req.setVisible(true);
                request_type.setVisible(true);
                String res = (String) select_user.getSelectedItem();
                if (!res.equals("-")) {
                    Request r;
                    if (choose_location.getSelectedItem().equals("Your list")) {
                        r = ParseInput.get_request_byname(res, user.getUsername());
                    }
                    else {
                        r = ParseInput.get_request_byname(res, "ADMIN");
                    }
                    mess.setVisible(false);
                    username.setText("Username: " + r.username);
                    request_type.setText("Request type: " + r.getType().name());
                    description.setText("Description: " + r.description);
                    date.setText("Date: " + r.getCreatedDate().toString());
                    if (r.getType() == RequestType.MOVIE_ISSUE) {
                        act_movie.setVisible(true);
                        act_movie.setText("Production title: " + r.movieTitle);
                    } else if (r.getType() == RequestType.ACTOR_ISSUE) {
                        act_movie.setVisible(true);
                        act_movie.setText("Actor name: " + r.actorName);
                    } else {
                        act_movie.setVisible(false);
                    }
                }
            }
        });

        solve_req.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == solve_req) {
                    String res = (String) select_user.getSelectedItem();
                    if (!res.equals("-")) {
                        List<Request> requests = IMDB.getInstance().getRequests();
                        User user_request = ParseInput.get_user_byname(res);
                        if (user.getUserType() == AccountType.Contributor) {
                            ((Contributor<?>)user).setStrategy(new ResolveRequest());
                            user.setExperience(user.getExperience() + ((Contributor<?>)user).executeStrategy());
                            experience_label.setText("You have accumulated " + user.getExperience() + " experience points!");
                        }
                        else if (user.getUserType() == AccountType.Regular) {
                            ((Regular<?>)user).setStrategy(new ResolveRequest());
                            user.setExperience(user.getExperience() + ((Regular<?>)user).executeStrategy());
                            experience_label.setText("You have accumulated " + user.getExperience() + " experience points!");
                        }

                        if (user_request.getUserType() == AccountType.Contributor) {
                            ((Contributor<?>)user_request).setStrategy(new ResolveRequest());
                            user_request.setExperience(user_request.getExperience() + ((Contributor<?>)user_request).executeStrategy());
                        }
                        else if (user_request.getUserType() == AccountType.Regular) {
                            ((Regular<?>)user_request).setStrategy(new ResolveRequest());
                            user_request.setExperience(user_request.getExperience() + ((Regular<?>)user_request).executeStrategy());
                        }

                        mess.setVisible(true);
                        mess.setText("Request solved!");
                        mess.setForeground(Color.GREEN);

                        if (choose_location.getSelectedItem().equals("Your list")) {
                            Request r = ParseInput.get_request_byname(res, user.getUsername());
                            ((Staff) user).getRequests().remove(r);
                            requests.remove(r);

                            String[] contributions_st = new String[((Staff) user).getRequests().size() + 1];
                            contributions_st[0] = "-";
                            int i = 1;
                            for (Object request : ((Staff) user).getRequests()) {
                                contributions_st[i] = ((Request) request).username;
                                i++;
                            }
                            DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(contributions_st);
                            select_user.setModel(model);
                            select_user.setSelectedItem("-");
                            r.clearObservers();
                            r.addObserver(user_request);
                            r.notifyObservers("User " + user.getUsername() +
                                    " has solved your request");
                        } else {
                            Request r = ParseInput.get_request_byname(res, "ADMIN");
                            RequestsHolder.remove_req(r);
                            requests.remove(r);

                            String[] contributions_st = new String[RequestsHolder.requests.size() + 1];
                            contributions_st[0] = "-";
                            int i = 1;
                            for (Request request : RequestsHolder.requests) {
                                contributions_st[i] = request.username;
                                i++;
                            }
                            DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(contributions_st);
                            select_user.setModel(model);
                            select_user.setSelectedItem("-");
                            r.clearObservers();
                            r.addObserver(user_request);
                            r.notifyObservers("User " + user.getUsername() +
                                    " has solved your request");
                        }

                        set_invisible();
                    }
                    else {
                        mess.setVisible(true);
                        mess.setText("You have not selected a request");
                        mess.setForeground(Color.RED);
                    }
                }
            }
        });

        remove_req.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == remove_req) {
                    String res = (String) select_user.getSelectedItem();
                    if (!res.equals("-")) {
                        List<Request> requests = IMDB.getInstance().getRequests();
                        mess.setVisible(true);
                        mess.setText("Request rejected!");
                        mess.setForeground(Color.GREEN);

                        if (choose_location.getSelectedItem().equals("Your list")) {
                            Request r = ParseInput.get_request_byname(res, user.getUsername());
                            requests.remove(r);
                            ((Staff) user).getRequests().remove(r);
                            String[] contributions_st = new String[((Staff) user).getRequests().size() + 1];
                            contributions_st[0] = "-";
                            int i = 1;
                            for (Object request : ((Staff) user).getRequests()) {
                                contributions_st[i] = ((Request) request).username;
                                i++;
                            }
                            DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(contributions_st);
                            select_user.setModel(model);
                            select_user.setSelectedItem("-");
                            r.clearObservers();
                            r.addObserver(ParseInput.get_user_byname(res));
                            r.notifyObservers("User " + user.getUsername() +
                                    " has rejected your request");
                        } else {
                            Request r = ParseInput.get_request_byname(res, "ADMIN");
                            requests.remove(r);
                            RequestsHolder.remove_req(r);
                            String[] contributions_st = new String[RequestsHolder.requests.size() + 1];
                            contributions_st[0] = "-";
                            int i = 1;
                            for (Request request : RequestsHolder.requests) {
                                contributions_st[i] = request.username;
                                i++;
                            }
                            DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(contributions_st);
                            select_user.setModel(model);
                            select_user.setSelectedItem("-");
                            r.clearObservers();
                            r.addObserver(ParseInput.get_user_byname(res));
                            r.notifyObservers("User " + user.getUsername() +
                                    " has rejected your request");
                        }
                        set_invisible();
                    }
                    else {
                        mess.setVisible(true);
                        mess.setText("You have not selected a request");
                        mess.setForeground(Color.RED);
                    }
                }
            }
        });

        init_comp(username, calibri, mustard, 100, 400, 300, 20);
        init_comp(request_type, calibri, mustard, 100, 430, 300, 20);
        init_comp(description, calibri, mustard, 100, 460, 700, 20);
        init_comp(date, calibri, mustard, 100, 490, 300, 20);
        init_comp(act_movie, calibri, mustard, 100, 520, 300, 20);

        this.add(username);
        this.add(request_type);
        this.add(description);
        this.add(date);
        this.add(act_movie);

        this.add(choose_location);
        this.add(select_user);
        this.add(solve_req);
        this.add(remove_req);
        this.add(choose_location);
        this.add(mess);

        this.setLayout(null);
        this.setVisible(true);
        this.setBackground(Color.BLACK);
    }

    public void init_comp(Component component, Font calibri, Color grey, int x, int y, int width, int height) {
        component.setBounds(x, y, width, height);
        component.setFont(calibri);
        if (!(component instanceof JTextField)) {
            component.setBackground(Color.black);
        }
        component.setForeground(grey);
    }

    public void set_invisible() {
        username.setVisible(false);
        request_type.setVisible(false);
        description.setVisible(false);
        date.setVisible(false);
        act_movie.setVisible(false);
    }
}
