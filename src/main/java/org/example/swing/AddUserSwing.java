package org.example.swing;

import org.example.*;
import org.example.users.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeSet;

public class AddUserSwing extends RegisterSwing {
    JComboBox account_type;
    public AddUserSwing(CardLayout cardLayout, JPanel panelCont) {
        super(cardLayout, panelCont);
        String[] acc_types = {"-" ,"Regular", "Contributor", "Admin"};
        account_type = new JComboBox<>(acc_types);
        country_label.setBounds(200, 625, 100, 25);
        account_type.setBounds(200, 670, 100, 25);
        account_type.setSelectedItem("-");
        super.complete_registration.setBounds(330, 670, 500, 25);
        super.create_account.setBounds(200, 720, 200, 25);
        add(account_type);

        super.create_account.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == back_button) {
                    cardLayout.show(panelCont, "3");
                }
                if (e.getSource() == create_account) {
                    String name = name_field.getText();
                    String username = username_field.getText();
                    String email = email_field.getText();
                    String password = String.valueOf(password_field.getText());
                    String gender = (String) gender_field.getSelectedItem();
                    String date_str = (String) date.getSelectedItem();
                    String month_str = (String) month.getSelectedItem();
                    String year_str = (String) date.getSelectedItem();
                    String age_str = age_field.getText();
                    String country = country_field.getText();
                    String acc_type = (String) account_type.getSelectedItem();
                    if (name.isEmpty() || username.isEmpty()|| email.isEmpty() || password.isEmpty() || gender.isEmpty()
                            || date_str.isEmpty() || month_str.isEmpty() || year_str.isEmpty() || age_str.isEmpty()
                            || country.isEmpty() || acc_type.equals("-")) {
                        complete_registration.setText("Complete all fields!");
                    }
                    else {
                        List<User> users = IMDB.getInstance().getUsers();
                        int already_taken = 0;
                        for (User user : users) {
                            if (email.equals(user.getInformation().getCredentials().getEmail())) {
                                already_taken = 1;
                                complete_registration.setText("An account has been created with this email.");
                                break;
                            }
                            if (username.equals(user.getUsername())) {
                                complete_registration.setText("An account has been created with this username.");
                                already_taken = 1;
                                break;
                            }
                        }
                        if (already_taken == 0) {
                            int age = 0;
                            try {
                                age = Integer.parseInt(age_str);

                                String birth = year_str + "-" + month_str + "-" + date_str;
                                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                Date birthDate = null;
                                try {
                                    birthDate = dateFormat.parse(birth);
                                } catch (ParseException ex) {
                                    throw new RuntimeException(ex);
                                }
                                Credentials c = new Credentials(email, password);
                                AccountType accountType = AccountType.valueOf(acc_type);

                                Information info = new Information.InformationBuilder()
                                        .credentials(c)
                                        .name(name)
                                        .country(country)
                                        .age(age)
                                        .gender(gender)
                                        .birthDate(birthDate)
                                        .build();
                                UserFactory userFactory = new UserFactory();
                                User new_user = userFactory.createUser(accountType, info, 0, new TreeSet<>(),
                                        new TreeSet<>(), username, new ArrayList<>());
                                users.add(new_user);
                                complete_registration.setText("");
                                country_field.setText("");
                                username_field.setText("");
                                age_field.setText("");
                                name_field.setText("");
                                email_field.setText("");
                                password_field.setText("");
                                cardLayout.show(panelCont, "3");
                                AddUserSwing.super.complete_registration.setText("");
                            }
                            catch (NumberFormatException exception) {
                                complete_registration.setText("Age should be a number!");
                            }
                        }
                    }
                }
            }
        });
    }
}
