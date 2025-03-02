package org.example.swing;

import org.example.*;
import org.example.observer.Observer;
import org.example.painters.ProdIcon;
import org.example.productions.Production;
import org.example.strategy.AddReview;
import org.example.users.AccountType;
import org.example.users.Regular;
import org.example.users.Staff;
import org.example.users.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

public class RatingSwing extends JPanel {
    CardLayout cardLayout;
    JPanel panelCont;
    JLabel comment_label;
    JLabel rating_label;
    JTextField comment_field;
    JTextField rating_field;
    JLabel title_image;
    JButton rating_but;
    JLabel complete_fields;
    JButton back_button;
    Map<String, ImageIcon> images;
    Map<String, ProdIcon> prod_icons;
    JLabel averageRating;
    User user;
    JPanel ratings;
    Production production;

    public RatingSwing(CardLayout cardLayout, JPanel panelCont, User user, Map<String, ImageIcon> images, Map<String, ProdIcon> prod_icons,
                       JPanel ratings, Production production, JLabel averageRating, JLabel experience_label) {
        this.cardLayout = cardLayout;
        this.panelCont = panelCont;
        this.images = images;
        this.ratings = ratings;
        this.prod_icons = prod_icons;
        this.production = production;
        this.averageRating = averageRating;
        this.user = user;
        rating_but = new JButton("Add rating");

        comment_field = new JTextField();
        comment_label = new JLabel("Comment:");
        rating_field = new JTextField();
        rating_label = new JLabel("Rating:");
        title_image = new JLabel();
        back_button = new JButton();
        complete_fields = new JLabel();
        Font calibri = new Font("Calibri", Font.PLAIN, 20);
        Font calibri_small = new Font("Calibri", Font.PLAIN, 15);
        Color grey = new Color(211, 211, 211);

        comment_field.setBounds(400, 300, 300, 30);
        rating_field.setBounds(400, 400, 300, 30);

        comment_label.setBounds(200, 300, 100, 30);
        rating_label.setBounds(200, 400, 70, 30);

        comment_field.setFont(calibri);
        comment_label.setFont(calibri);
        rating_label.setFont(calibri);
        rating_field.setFont(calibri);

        complete_fields.setBounds(250, 650, 600, 30);
        complete_fields.setFont(calibri);
        complete_fields.setForeground(Color.RED);

        ImageIcon image = new ImageIcon("src/main/java/org/example/images/back.jpg");
        image.setImage(image.getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT));
        back_button.setIcon(image);
        back_button.setBounds(40, 40, 40, 40);
        back_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == back_button) {
                    cardLayout.show(panelCont, "3");
                }
            }
        });

        rating_but.setBounds(200, 500, 500, 40);
        rating_but.setBackground(new Color(219, 165, 5));
        rating_but.setFont(calibri);
        rating_but.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == rating_but) {
                    if (rating_field.getText().isEmpty() || comment_field.getText().isEmpty()) {
                        complete_fields.setText("Complete all fields");
                    } else {
                        try {
                            List<User> users = IMDB.getInstance().getUsers();
                            int rating = Integer.parseInt(rating_field.getText());
                            if (rating <= 10 && rating > 0) {
                                Rating r = new Rating(user.getUsername(), rating, comment_field.getText());
                                Rating r2 = new Rating(user.getUsername(), rating, comment_field.getText());
                                for (User u : users) {
                                    if (u.getUserType() != AccountType.Regular) {
                                        for (Object prod : ((Staff) u).getContributions()) {
                                            if (prod instanceof Production) {
                                                if (prod.equals(production)) {
                                                    r2.addObserver(u);
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                }
                                r2.notifyObservers("The production \"" + production.title + "\"" +
                                        " that you added has received a review from the user " + "\"" +
                                        user.getUsername() + "\" -> " + rating);
                                for (User u : users) {
                                    if (u.getFavorites() != null) {
                                        if (!u.getFavorites().isEmpty()) {
                                            for (Object prod : u.getFavorites()) {
                                                if (prod instanceof Production) {
                                                    if (prod.equals(production)) {
                                                        r.addObserver(u);
                                                        break;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                r.notifyObservers("The production \"" + production.title + "\"" +
                                        " from your list of favorites has received a review from the" +
                                        " user " + "\"" + user.getUsername() + "\" -> " + rating);
                                for (Observer o : r2.getObservers()) {
                                    r.addObserver(o);
                                }
                                ((Regular<?>)user).setStrategy(new AddReview());
                                user.setExperience(user.getExperience() + ((Regular<?>)user).executeStrategy());
                                experience_label.setText("You have accumulated " + user.getExperience() + " experience points!");

                                ratings.add(MenuImdb.user_ratings(r, calibri_small, grey));
                                production.ratings.add(r);
                                cardLayout.show(panelCont, "3");
                                double average = 0;
                                for (Rating rating1 : production.getRatings()) {
                                    average += rating1.getRating();
                                }
                                production.setAverageRating(average / production.ratings.size());
                                averageRating.setText("Rating: " + production.getAverageRating());
                                MenuImdb.update_lists_menu(images, prod_icons);
                            } else {
                                complete_fields.setText("Rating should be a whole number between 1 and 10.");
                            }
                        } catch (NumberFormatException exception) {
                            complete_fields.setText("Rating should be a whole number between 1 and 10.");
                        }
                    }
                }
            }
        });

        this.add(comment_field);
        this.add(comment_label);
        this.add(rating_but);
        this.add(complete_fields);
        this.add(rating_field);
        this.add(rating_label);
        this.add(back_button);
        setLayout(null);
        setVisible(true);
    }

}
