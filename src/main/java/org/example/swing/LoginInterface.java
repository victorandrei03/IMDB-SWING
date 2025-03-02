package org.example.swing;

import org.example.IMDB;
import org.example.users.User;
import org.example.buttons.PasswordBox;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

public class LoginInterface extends JFrame implements ActionListener {
    JLabel photo_login_message;
    JLabel userlabel;
    JLabel passwordlabel;
    JLabel no_account;
    JLabel login_message;
    JButton login_button;
    JButton register_button;
    JTextField username_field;
    JPasswordField password_field;
    JPanel panelCont;
    JPanel first_panel;
    JPanel registration_panel;
    CardLayout cardLayout;
    JCheckBox password_show;
    BorderLayout box_login;
    JLabel borders;
    JPanel app_menu;
    Map<String, ImageIcon> images;

    public LoginInterface(Map<String, ImageIcon> images) {
        photo_login_message = new JLabel();
        this.images = images;
        userlabel = new JLabel("Email:");
        passwordlabel = new JLabel("Password:");
        no_account = new JLabel();
        login_button = new JButton();
        register_button = new JButton();
        username_field = new JTextField();
        password_field = new JPasswordField();
        login_message = new JLabel();
        panelCont = new JPanel();
        first_panel = new JPanel();
        registration_panel = new JPanel();
        cardLayout = new CardLayout();
        password_show = new JCheckBox("Show password");
        box_login = new BorderLayout();
        borders = new JLabel("");
        app_menu = new JPanel();

        ImageIcon imdb_logo = new ImageIcon("src/main/java/org/example/images/imdb_icon.jpg");
        imdb_logo.setImage(imdb_logo.getImage().getScaledInstance(400, 200, Image.SCALE_DEFAULT));
        Font calibri = new Font("Calibri", Font.PLAIN, 15);

        photo_login_message.setIcon(imdb_logo);
        photo_login_message.setText("Sign in");
        photo_login_message.setHorizontalTextPosition(JLabel.CENTER);
        photo_login_message.setVerticalTextPosition(JLabel.BOTTOM);
        photo_login_message.setForeground(Color.black);
        photo_login_message.setFont(new Font("Calibri", Font.BOLD, 25));
        photo_login_message.setIconTextGap(40);
        photo_login_message.setBounds(400, 30, 400, 330);

        userlabel.setBounds(400, 350, 100, 25);
        userlabel.setFont(calibri);
        userlabel.setForeground(Color.black);

        passwordlabel.setBounds(400, 400, 100, 25);
        passwordlabel.setFont(calibri);
        passwordlabel.setForeground(Color.black);


        username_field.setBounds(500, 350, 300, 25);
        password_field.setBounds(500, 400, 300, 25);

        register_button.setBounds(400, 600, 400, 40);
        register_button.setText("Register");
        register_button.setFont(calibri);
        register_button.setForeground(Color.black);
        register_button.addActionListener(this);


        PasswordBox passwordBox = new PasswordBox(password_field);
        password_show.addItemListener(passwordBox);
        password_show.setBounds(495, 430, 150, 25);
        password_show.setFont(calibri);

        login_button.setBounds(400, 500, 400, 40);
        login_button.setText("Sign in");
        login_button.setFont(calibri);
        login_button.setForeground(Color.black);
        login_button.setBackground(Color.YELLOW);
        login_button.addActionListener(this);

        borders.setBounds(350, 285, 500, 390);
        borders.setBorder(BorderFactory.createLineBorder(new Color(211, 211, 211)));
        borders.setVisible(true);

        no_account.setText("No account? Please register.");
        no_account.setFont(calibri);
        no_account.setBounds(500, 575, 200, 25);

        login_message.setBounds(480, 450, 225, 50);

        panelCont.setLayout(cardLayout);

        first_panel.add(login_button);
        first_panel.add(register_button);
        first_panel.add(photo_login_message);
        first_panel.add(userlabel);
        first_panel.add(passwordlabel);
        first_panel.add(username_field);
        first_panel.add(password_field);
        first_panel.add(no_account);
        first_panel.add(login_message);
        first_panel.add(password_show);
        first_panel.add(borders);
        first_panel.setVisible(true);
        first_panel.setLayout(null);


        panelCont.add(first_panel, "1");
        cardLayout.show(panelCont, "1");

        add(panelCont);

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screensize = toolkit.getScreenSize();

        setSize(1200, 800);
        setResizable(false);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("IMDB");
        setLocationRelativeTo(null);
        setVisible(true);
        setLayout(null);
        int x = (screensize.width - getWidth()) / 2;
        int y = (screensize.height - getHeight()) / 2;

        setIconImage(imdb_logo.getImage());

        setLocation(x, y);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == login_button) {
            String email = username_field.getText();
            String password = String.valueOf(password_field.getText());
            List<User> users = IMDB.getInstance().getUsers();
            int found = 0;
            for (User u : users) {
                if (u.getInformation().getCredentials().getEmail().equals(email)) {
                    if (u.getInformation().getCredentials().getPassword().equals(password)) {
                        found = 1;
                        panelCont.add(app_menu, "3");
                        MenuImdb menuImdb = new MenuImdb(app_menu, cardLayout, panelCont, u, this, images);
                        menuImdb.menu_imdb();
                        cardLayout.show(panelCont, "3");
                        break;
                    }
                }
            }
            if (found == 0) {
                login_message.setText("Invalid email or password! Try again.");
                login_message.setForeground(Color.RED);
                login_message.setFont(new Font("Calibri", Font.PLAIN, 15));
            }
        } else if (e.getSource() == register_button) {
            panelCont.add(new RegisterSwing(cardLayout, panelCont), "2");
            cardLayout.show(panelCont, "2");
        }
    }
}
