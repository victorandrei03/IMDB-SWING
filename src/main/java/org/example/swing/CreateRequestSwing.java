package org.example.swing;

import org.example.*;
import org.example.actors.Actor;
import org.example.productions.Production;
import org.example.requests.Request;
import org.example.requests.RequestType;
import org.example.requests.RequestsHolder;
import org.example.users.AccountType;
import org.example.users.Staff;
import org.example.users.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;

public class CreateRequestSwing extends JPanel {
    JButton create_request;
    JButton remove_req;
    User user;
    JComboBox request_type;
    JComboBox choose_operation;
    JComboBox users_to_requests;
    JButton back_button;
    JLabel description_label;
    JTextField description;
    JLabel mess;
    JLabel mess_rem;
    JLabel act_movie_label;
    JTextField act_movie;

    JLabel to_cr;
    JLabel request_type_cr;
    JLabel date_cr;
    JLabel description_cr;
    JLabel act_movie_cr;
    public CreateRequestSwing(User user, CardLayout cardLayout, JPanel panelCont) {
        this.user = user;
        create_request = new JButton("Create request");
        Font calibri = new Font("Calibri", Font.PLAIN, 18);
        Color mustard = new Color(219, 165, 5);
        description = new JTextField();
        act_movie_label = new JLabel();
        remove_req = new JButton("Remove request");
        description = new JTextField();
        act_movie = new JTextField();
        description_label = new JLabel("Description: ");

        to_cr = new JLabel();
        request_type_cr = new JLabel();
        date_cr = new JLabel();
        description_cr = new JLabel();
        act_movie_cr = new JLabel();
        back_button = new JButton();

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


        String[] objects = {"-", "DELETE_ACCOUNT", "ACTOR_ISSUE", "MOVIE_ISSUE", "OTHERS"};
        request_type = new JComboBox<>(objects);

        String[] obj_op = {"-", "Create request", "Remove request"};
        choose_operation = new JComboBox<>(obj_op);
        init_comp(choose_operation, calibri, mustard, 400, 75, 300, 30);

        users_to_requests = new JComboBox<>();
        init_comp(request_type, calibri, mustard, 400, 115, 300, 30);
        init_comp(users_to_requests, calibri, mustard, 400, 115, 300, 30);

        init_comp(create_request, calibri, mustard, 400, 550, 300, 30);
        init_comp(remove_req, calibri, mustard, 100, 600, 300, 30);
        mess = new JLabel("Request created!");
        mess_rem = new JLabel("Request removed!");
        init_comp(mess, calibri, Color.GREEN, 200, 590, 900, 25);
        init_comp(mess_rem, calibri, Color.GREEN, 100, 650, 500, 25);
        mess.setVisible(false);
        mess_rem.setVisible(false);
        set_invisible();

        choose_operation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (choose_operation.getSelectedItem().equals("Create request")) {
                    set_invisible();
                    mess.setVisible(false);
                    mess_rem.setVisible(false);
                    request_type.setVisible(true);
                    users_to_requests.setVisible(false);
                    remove_req.setVisible(false);
                    create_request.setVisible(true);
                }
                else if (choose_operation.getSelectedItem().equals("Remove request")) {
                    set_invisible();
                    request_type.setVisible(false);
                    mess.setVisible(false);
                    mess_rem.setVisible(false);
                    users_to_requests.setVisible(true);
                    create_request.setVisible(false);

                    remove_req.setVisible(true);
                    List<Request> obj_count = user.get_created_request_by_user();
                    String[] contributions_st = new String[0];
                    int found = 0;
                    for (Request request : RequestsHolder.requests) {
                        if (request.username.equals(user.getUsername())) {
                            contributions_st = new String[obj_count.size() + 2];
                            contributions_st[1] = "ADMIN";
                            if (!obj_count.isEmpty()) {
                                for (int i = 2; i <= obj_count.size(); i++) {
                                    contributions_st[i] = obj_count.get(i - 1).to;
                                }
                            }
                            found = 1;
                            break;
                        }
                    }
                    if (found == 0) {
                        contributions_st = new String[obj_count.size() + 1];
                        contributions_st[0] = "-";
                        if (!obj_count.isEmpty()) {
                            for (int i = 1; i <= obj_count.size(); i++) {
                                contributions_st[i] = obj_count.get(i - 1).to;
                            }
                        }
                    }

                    DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(contributions_st);
                    users_to_requests.setModel(model);
                }
            }
        });

        request_type.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!request_type.getSelectedItem().equals("-")) {
                    description.setVisible(true);
                    description_label.setVisible(true);
                    if (request_type.getSelectedItem().equals("ACTOR_ISSUE")) {

                        act_movie_label.setVisible(true);
                        act_movie.setVisible(true);
                        act_movie_label.setText("Actor name: ");
                    }
                    else if (request_type.getSelectedItem().equals("MOVIE_ISSUE")) {

                        act_movie_label.setVisible(true);
                        act_movie.setVisible(true);
                        act_movie_label.setText("Production title: ");
                    }
                    else {
                        act_movie_label.setVisible(false);
                        act_movie.setVisible(false);
                    }
                }
                else {
                    set_invisible();
                }
            }
        });

        users_to_requests.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!users_to_requests.getSelectedItem().equals("-")) {
                    to_cr.setVisible(true);
                    date_cr.setVisible(true);
                    request_type_cr.setVisible(true);
                    description_cr.setVisible(true);
                    List<Request> req_user = user.get_created_request_by_user();
                    Request request = null;
                    for (Request r : req_user) {
                        if (users_to_requests.getSelectedItem().equals(r.to)) {
                            request = r;
                            break;
                        }
                    }
                    if (request == null) {
                        for (Request r : RequestsHolder.requests) {
                            if (r.username.equals(user.getUsername())) {
                                request = r;
                            }
                        }
                    }
                    to_cr.setText("To: " + users_to_requests.getSelectedItem());
                    request_type_cr.setText("Request type: " + request.getType().name());
                    description_cr.setText("Description: " + request.description);
                    date_cr.setText("Date: " + request.getCreatedDate().toString());
                    if (request.getType() == RequestType.MOVIE_ISSUE) {
                        act_movie_cr.setVisible(true);
                        act_movie_cr.setText("Production title: " + request.movieTitle);
                    } else if (request.getType() == RequestType.ACTOR_ISSUE) {
                        act_movie_cr.setVisible(true);
                        act_movie_cr.setText("Actor name: " + request.actorName);
                    } else {
                        act_movie_cr.setVisible(false);
                    }
                }
                else {
                    set_invisible();
                }
            }
        });

        remove_req.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Request> requests = IMDB.getInstance().getRequests();
                if (e.getSource() == remove_req && !users_to_requests.getSelectedItem().equals("-")) {
                    List<Request> req_user = user.get_created_request_by_user();
                    Request req = null;
                    for (Request r : req_user) {
                        if (users_to_requests.getSelectedItem().equals(r.to)) {
                            req = r;
                            User u = ParseInput.get_user_byname(r.to);
                            ((Staff)u).getRequests().remove(req);
                            requests.remove(req);
                            to_cr.setVisible(false);
                            request_type_cr.setVisible(false);
                            description_cr.setVisible(false);
                            date_cr.setVisible(false);
                            act_movie_cr.setVisible(false);
                            mess_rem.setVisible(true);
                            break;
                        }
                    }
                    if (req == null) {
                        for (Request r : RequestsHolder.requests) {
                            if (r.username.equals(user.getUsername())) {
                                req = r;
                                RequestsHolder.requests.remove(req);
                                requests.remove(req);
                                mess_rem.setVisible(true);
                                to_cr.setVisible(false);
                                request_type_cr.setVisible(false);
                                description.setVisible(false);
                                date_cr.setVisible(false);
                                act_movie_cr.setVisible(false);
                            }
                        }
                    }
                    req.notifyObservers("The request from user " + user.getUsername()
                            + " has been deleted from the system.");
                    req.clearObservers();
                    List<Request> obj_count = user.get_created_request_by_user();
                    String[] contributions_st = new String[0];
                    int found = 0;
                    for (Request request : RequestsHolder.requests) {
                        if (request.username.equals(user.getUsername())) {
                            contributions_st = new String[obj_count.size() + 2];
                            contributions_st[1] = "ADMIN";
                            if (!obj_count.isEmpty()) {
                                for (int i = 2; i <= obj_count.size(); i++) {
                                    contributions_st[i] = obj_count.get(i - 1).to;
                                }
                            }
                            found = 1;
                            break;
                        }
                    }
                    if (found == 0) {
                        contributions_st = new String[obj_count.size() + 1];
                        contributions_st[0] = "-";
                        if (!obj_count.isEmpty()) {
                            for (int i = 1; i <= obj_count.size(); i++) {
                                contributions_st[i] = obj_count.get(i - 1).to;
                            }
                        }
                    }
                    DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(contributions_st);
                    users_to_requests.setModel(model);

                }
            }
        });


        create_request.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                List<Request> requests = IMDB.getInstance().getRequests();
                List<User> users = IMDB.getInstance().getUsers();
                if (e.getSource() == create_request) {
                    String res = (String) request_type.getSelectedItem();
                    if (!res.equals("-")) {
                        Date d = new Date();
                        String username = user.getUsername();
                        if (res.equals("MOVIE_ISSUE") || res.equals("ACTOR_ISSUE")) {
                            User u = null;
                            Production production = null;
                            if (!act_movie.getText().isEmpty()) {
                                production = ParseInput.get_production_byname(act_movie.getText());
                            }
                            Actor actor = null;
                            if (!act_movie.getText().isEmpty()) {
                                actor = ParseInput.get_actor_byname(act_movie.getText());
                            }
                            for (User user1 : users) {
                                if (user1.getUserType() != AccountType.Regular) {
                                    if (((Staff<?>) user1).getContributions() != null) {
                                        if (actor != null) {
                                            if (((Staff<?>) user1).getContributions().contains(actor)) {
                                                u = user1;
                                                break;
                                            }
                                        }
                                        if (production != null) {
                                            if (((Staff<?>) user1).getContributions().contains(production)) {
                                                u = user1;
                                                break;
                                            }
                                        }
                                    }
                                }
                            }
                            if (u != null && (production != null || actor != null)) {
                                Request req = ParseInput.get_request_byname(username, u.getUsername());
                                if (req == null) {
                                    if (res.equals("MOVIE_ISSUE")) {
                                        RequestType r = RequestType.valueOf("MOVIE_ISSUE");
                                        String title = act_movie.getText();
                                        if (production != null && !description.getText().isEmpty()) {
                                            Request request = new Request(r, d, null, title, description.getText(),
                                                    user.getUsername(), u.getUsername());
                                            request.addObserver(u);
                                            request.notifyObservers("A request has been created and" +
                                                    " added to your request list.");
                                            ((Staff) u).getRequests().add(request);
                                            requests.add(request);
                                            act_movie_cr.setText("");
                                            description.setText("");
                                            act_movie.setText("");
                                            mess.setVisible(true);
                                            mess.setText("Request created!");
                                            mess.setForeground(Color.GREEN);
                                            set_invisible();
                                        } else {
                                            mess.setVisible(true);
                                            mess.setText("The title of production wasn't found or you didn't introduce a description!");
                                            mess.setForeground(Color.RED);
                                        }
                                    } else {
                                        RequestType r = RequestType.valueOf("ACTOR_ISSUE");
                                        String actor_name = act_movie.getText();
                                        if (actor != null && !description.getText().isEmpty()) {
                                            Request request = new Request(r, d, actor_name, null, description.getText(),
                                                    user.getUsername(), u.getUsername());
                                            ((Staff) u).getRequests().add(request);
                                            request.addObserver(u);
                                            request.notifyObservers("A request has been created and" +
                                                    " added to your request list.");
                                            requests.add(request);
                                            mess.setVisible(true);
                                            mess.setText("Request created!");
                                            act_movie_cr.setText("");
                                            description.setText("");
                                            act_movie.setText("");
                                            mess.setForeground(Color.GREEN);
                                            set_invisible();
                                        } else {
                                            mess.setVisible(true);
                                            mess.setText("The name of the actor wasn't found or you didn't introduce a description!");
                                            mess.setForeground(Color.RED);
                                        }
                                    }
                                }
                                else {
                                    mess.setVisible(true);
                                    mess.setText("You have already created a request for this user! If you want to change it, remove it and add a new one!");
                                    mess.setForeground(Color.RED);
                                }
                            }
                            else {
                                mess.setVisible(true);
                                mess.setText("The production or actor you are looking for is not in the system!");
                                mess.setForeground(Color.RED);
                            }
                        }
                        else {
                            int found = 0;
                            for (Request r : RequestsHolder.requests) {
                                if (r.username.equals(username)) {
                                    found = 1;
                                    break;
                                }
                            }
                            if (found == 0) {
                                RequestType r;
                                if (res.equals("DELETE_ACCOUNT")) {
                                    r = RequestType.DELETE_ACCOUNT;
                                } else {
                                    r = RequestType.OTHERS;
                                }
                                if (!description.getText().isEmpty()) {
                                    Request request = new Request(r, d, null, null, description.getText(), username, "ADMIN");
                                    RequestsHolder.add_request(request);
                                    requests.add(request);
                                    for (User u : users) {
                                        if (u.getUserType() == AccountType.Admin) {
                                            request.addObserver(u);
                                        }
                                    }
                                    request.notifyObservers("A request has been created and added to the" +
                                            " admins' common request list.");
                                    mess.setVisible(true);
                                    mess.setText("Request created!");
                                    description.setText("");
                                    mess.setForeground(Color.GREEN);
                                    set_invisible();
                                } else {
                                    mess.setVisible(true);
                                    mess.setText("Enter a description!");
                                    mess.setForeground(Color.RED);
                                }
                            }
                            else {
                                mess.setVisible(true);
                                mess.setText("You have already created a request for the team of admins! If you want to change it, remove it and add a new one!");
                                mess.setForeground(Color.RED);
                            }
                        }
                    }
                }
            }
        });



        init_comp(description_label, calibri, mustard, 200, 440, 700, 20);
        init_comp(description, calibri, mustard, 320, 440, 700, 20);

        init_comp(act_movie_label, calibri, mustard, 200, 480, 300, 20);
        init_comp(act_movie, calibri, mustard, 320, 480, 300, 20);


        init_comp(to_cr, calibri, mustard, 100, 350, 300, 20);
        init_comp(request_type_cr, calibri, mustard, 100, 380, 300, 20);
        init_comp(description_cr, new Font("Calibri", Font.PLAIN, 15), mustard, 100, 410, 1080, 20);
        init_comp(date_cr, calibri, mustard, 100, 440, 300, 20);
        init_comp(act_movie_cr, calibri, mustard, 100, 470, 300, 20);

        this.add(description);
        this.add(description_label);

        this.add(act_movie_label);
        this.add(act_movie);

        add(to_cr);
        add(request_type_cr);
        add(description_cr);
        add(date_cr);
        add(act_movie_cr);

        this.add(create_request);
        this.add(remove_req);

        this.add(users_to_requests);
        this.add(request_type);
        this.add(choose_operation);

        this.add(mess);
        this.add(mess_rem);

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
    public void set_invisible() {
        request_type.setVisible(false);
        users_to_requests.setVisible(false);
        description.setVisible(false);
        description_label.setVisible(false);
        act_movie_label.setVisible(false);
        act_movie.setVisible(false);
        remove_req.setVisible(false);
        create_request.setVisible(false);

        to_cr.setVisible(false);
        request_type_cr.setVisible(false);
        date_cr.setVisible(false);
        act_movie_cr.setVisible(false);
        description_cr.setVisible(false);
    }
}
