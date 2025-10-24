package main.ui;

import main.dao.UserDAO;
import main.model.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginForm extends JFrame {
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;

    public LoginForm() {
        setTitle("Company HR System - Login");
        setSize(350, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        JLabel lblUser = new JLabel("Username:");
        lblUser.setBounds(20, 20, 80, 25);
        add(lblUser);

        txtUsername = new JTextField();
        txtUsername.setBounds(120, 20, 180, 25);
        add(txtUsername);

        JLabel lblPass = new JLabel("Password:");
        lblPass.setBounds(20, 60, 80, 25);
        add(lblPass);

        txtPassword = new JPasswordField();
        txtPassword.setBounds(120, 60, 180, 25);
        add(txtPassword);

        btnLogin = new JButton("Login");
        btnLogin.setBounds(120, 100, 100, 30);
        add(btnLogin);

        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = txtUsername.getText();
                String password = String.valueOf(txtPassword.getPassword());

                UserDAO dao = new UserDAO();
                User user = dao.login(username, password);

                if (user != null) {
                    JOptionPane.showMessageDialog(null, "Login Successful! Role: " + user.getRole());
                    // Open dashboard based on role
                    if (user.getRole().equals("Admin")) {
                        new AdminDashboard(user).setVisible(true);
                    } else {
                        new EmployeeDashboard(user).setVisible(true);
                    }
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid username or password!");
                }
            }
        });

        setLocationRelativeTo(null);
    }
}

