package org.example.swing;

import org.example.users.Admin;
import org.example.IMDB;
import org.example.users.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class RemoveUserSwing extends JPanel {
    JComboBox users;
    JButton remove_user;
    JButton back_button;
    public RemoveUserSwing(CardLayout cardLayout, JPanel panelCont, User user) {
        List<User> users_list = IMDB.getInstance().getUsers();
        Color mustard = new Color(219, 165, 5);
        String[] users_name = new String[users_list.size()];
        users_name[0] = "-";
        int i = 1;
        for (User u : users_list) {
            if (!u.getUsername().equals(user.getUsername())) {
                users_name[i] = u.getUsername();
                i++;
            }
        }
        ImageIcon back_img = new ImageIcon("src/main/java/org/example/images/back.jpg");
        back_img.setImage(back_img.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH));
        back_button = new JButton();
        back_button.setIcon(back_img);
        back_button.setBounds(30, 30, 40, 40);
        back_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(panelCont, "3");
            }
        });

        users = new JComboBox<>(users_name);
        users.setBounds(400, 200, 300, 30);
        remove_user = new JButton("Remove");
        remove_user.setBounds(450, 300, 200, 20);
        remove_user.setBackground(Color.black);
        remove_user.setBackground(mustard);
        users.setForeground(mustard);
        users.setBackground(Color.black);
        users.setFont(new Font("Calibri", Font.PLAIN, 15));
        users.setSelectedItem("-");
        remove_user.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!users.getSelectedItem().equals("-")) {
                    ((Admin)user).remove_user((String) users.getSelectedItem());
                }
                String[] users_name_str = new String[users_list.size()];
                users_name_str[0] = "-";
                int i = 1;
                for (User u : users_list) {
                    if (!u.getUsername().equals(user.getUsername())) {
                        users_name_str[i] = u.getUsername();
                        i++;
                    }
                }
                DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(users_name_str);
                users.setModel(model);
                users.setSelectedItem("-");
            }
        });
        this.add(users);
        add(back_button);
        this.add(remove_user);
        setVisible(true);
        setLayout(null);
        setBackground(Color.black);
    }
}
