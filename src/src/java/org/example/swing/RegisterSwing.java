package org.example.swing;

import org.example.*;
import org.example.buttons.PasswordBox;
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

public class RegisterSwing extends JPanel implements ActionListener {
    JLabel introduction_image;
    JLabel email_label;
    JLabel password_label;
    JLabel name_label;
    JLabel age_label;
    JLabel gender_label;
    JLabel birthDate_label;
    JLabel username_label;
    JLabel complete_registration;
    JTextField email_field;
    JPasswordField password_field;
    JTextField name_field;
    JTextField age_field;
    JTextField birthdate_field;
    JTextField username_field;
    BorderLayout box_registration;
    JCheckBox password_show;
    JComboBox date;
    JComboBox month;
    JComboBox year;
    JComboBox gender_field;
    JButton back_button;
    JButton create_account;
    CardLayout cardLayout;
    JPanel panelCont;
    JLabel borders;
    JLabel country_label;
    JTextField country_field;

    public RegisterSwing(CardLayout cardLayout, JPanel panelCont) {
        introduction_image = new JLabel();
        email_field = new JTextField();
        birthdate_field = new JTextField();
        age_field = new JTextField();
        name_field = new JTextField();
        username_field = new JTextField();
        password_field = new JPasswordField();
        name_field = new JTextField();
        gender_label = new JLabel("Gender:");
        birthDate_label = new JLabel("Birth date:");
        age_label = new JLabel("Age:");
        username_label = new JLabel("Username:");
        email_label = new JLabel("Email:");
        name_label = new JLabel("Name:");
        password_label = new JLabel("Password:");
        complete_registration = new JLabel();
        password_show = new JCheckBox("Show password");
        box_registration = new BorderLayout();
        back_button = new JButton();
        create_account = new JButton("Create account");
        this.cardLayout = cardLayout;
        this.panelCont = panelCont;
        borders = new JLabel();
        country_label = new JLabel("Country:");
        country_field = new JTextField();

        ImageIcon registration_logo = new ImageIcon("src/main/java/org/example/images/imdb_icon.jpg");
        registration_logo.setImage(registration_logo.getImage().getScaledInstance(300, 150, Image.SCALE_DEFAULT));
        Font calibri = new Font("Calibri", Font.PLAIN, 15);

        ImageIcon back_image = new ImageIcon("src/main/java/org/example/images/back.jpg");
        back_image.setImage(back_image.getImage().getScaledInstance(40, 20, Image.SCALE_DEFAULT));
        back_button.setIcon(back_image);
        back_button.setBounds(20, 20, 40, 20);
        back_button.setBorderPainted(false);
        back_button.setFocusPainted(false);
        back_button.addActionListener(this);

        introduction_image.setIcon(registration_logo);
        introduction_image.setText("CREATE ACCOUNT:");
        introduction_image.setHorizontalTextPosition(JLabel.RIGHT);
        introduction_image.setVerticalTextPosition(JLabel.CENTER);
        introduction_image.setForeground(Color.BLACK);
        introduction_image.setFont(new Font("Calibri", Font.BOLD, 60));
        introduction_image.setIconTextGap(20);
        introduction_image.setBounds(100, 30, 800, 200);

        name_label.setBounds(200, 250, 100, 25);
        username_label.setBounds(200, 300, 100, 25);
        email_label.setBounds(200, 350, 100, 25);
        password_label.setBounds(200, 400, 100, 25);
        age_label.setBounds(200, 475, 100, 25);
        birthDate_label.setBounds(200, 525, 100, 25);
        gender_label.setBounds(200, 575, 100, 25);
        country_label.setBounds(200, 625, 100, 25);

        name_field.setBounds(300, 250, 200, 25);
        username_field.setBounds(300, 300, 200, 25);
        email_field.setBounds(300, 350, 200, 25);
        password_field.setBounds(300, 400, 200, 25);
        age_field.setBounds(300, 475, 200, 25);
        birthdate_field.setBounds(300, 525, 200, 25);
        country_field.setBounds(300, 625, 200, 25);

        create_account.setBackground(Color.YELLOW);
        create_account.setBounds(300, 670, 200, 25);
        create_account.addActionListener(this);

        PasswordBox passwordBox = new PasswordBox(password_field);
        password_show.addItemListener(passwordBox);
        password_show.setBounds(300, 425, 150, 25);
        password_show.setFont(calibri);

        String[] genders = {"Male", "Female"};
        gender_field = new JComboBox<>(genders);
        gender_field.setBounds(300, 575, 85, 25);
        gender_field.setFont(calibri);

        String[] years = new String[100];
        String[] dates = new String[31];
        String[] months = new String[12];
        int year_number = 1924;
        for (int i = 0; i < 100; i++) {
            if (i < 31) {
                dates[i] = i + 1 + "";
                if (i < 12) {
                    if (i < 9) {
                        months[i] = "0" + (i + 1);
                    } else {
                        months[i] = i + 1 + "";
                    }
                }
            }
            years[i] = year_number + i + "";
        }


        DefaultComboBoxModel<String> date_default = new DefaultComboBoxModel<>(dates);
        month = new JComboBox(months);

        date = new JComboBox<>(date_default);

        year = new JComboBox<>(years);

        year.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int year_num = Integer.parseInt((String) year.getSelectedItem());
                if (Integer.parseInt((String) month.getSelectedItem()) == 2) {
                    if ((year_num % 4 == 0 && year_num % 100 != 0) || (year_num % 400 == 0)) {
                        if (date_default.getIndexOf("29") == -1) {
                            date_default.addElement("29");
                        }
                    } else {
                        date_default.removeElement("29");
                    }
                }
            }
        });

        month.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == month) {
                    int month_index = Integer.parseInt((String) month.getSelectedItem());
                    if (month_index % 2 == 0) {
                        date_default.removeElement("31");
                    } else {
                        if (date_default.getIndexOf("30") == -1) {
                            date_default.addElement("30");
                        }
                        if (date_default.getIndexOf("31") == -1) {
                            date_default.addElement("31");
                        }
                    }
                    if (month_index == 2) {
                        date_default.removeElement("30");
                    }
                }
            }
        });

        month.setFont(calibri);
        month.setBounds(351, 525, 60, 20);

        date.setFont(calibri);
        date.setBounds(300, 525, 50, 20);

        year.setFont(calibri);
        year.setBounds(412, 525, 60, 20);

        borders.setBounds(130, 225, 490, 525);
        borders.setBorder(BorderFactory.createLineBorder(new Color(211, 211, 211)));
        borders.setVisible(true);

        complete_registration.setBounds(175, 700, 500, 40);

        add(year);
        add(month);
        add(date);

        add(introduction_image);
        add(name_label);
        add(username_label);
        add(email_label);
        add(password_label);
        add(age_label);
        add(birthDate_label);
        add(gender_label);
        add(name_field);
        add(username_field);
        add(email_field);
        add(password_field);
        add(age_field);
        add(gender_field);
        add(password_show);
        add(back_button);
        add(create_account);
        add(borders);
        add(complete_registration);
        add(country_field);
        add(country_label);

        name_label.setFont(calibri);
        username_label.setFont(calibri);
        email_label.setFont(calibri);
        password_label.setFont(calibri);
        age_label.setFont(calibri);
        birthDate_label.setFont(calibri);
        gender_label.setFont(calibri);
        country_label.setFont(calibri);

        setVisible(true);
        setLayout(null);
        complete_registration.setForeground(Color.RED);
        complete_registration.setFont(new Font("Arial", Font.PLAIN, 20));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == back_button) {
            cardLayout.show(panelCont, "1");
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
            if (name.isEmpty() || username.isEmpty()|| email.isEmpty() || password.isEmpty() || gender.isEmpty()
                    || date_str.isEmpty() || month_str.isEmpty() || year_str.isEmpty() || age_str.isEmpty()
                    || country.isEmpty()) {
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
                        AccountType accountType = AccountType.Regular;

                        Information info = new Information.InformationBuilder()
                                .credentials(c)
                                .name(name)
                                .country(country)
                                .age(age)
                                .gender(gender)
                                .birthDate(birthDate)
                                .build();
                        Regular new_user = new Regular(info, 0, new TreeSet<>(), username, new ArrayList<>());
                        users.add(new_user);
                        complete_registration.setText("");
                        country_field.setText("");
                        username_field.setText("");
                        age_field.setText("");
                        name_field.setText("");
                        email_field.setText("");
                        password_field.setText("");
                        cardLayout.show(panelCont, "1");
                    }
                    catch (NumberFormatException exception) {
                        complete_registration.setText("Age should be a number!");
                    }
                }
            }
        }
    }
}
