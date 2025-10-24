package main.ui;

import main.model.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EmployeeDashboard extends JFrame {
    private User employeeUser;

    public EmployeeDashboard(User user) {
        this.employeeUser = user;

        setTitle("Employee Dashboard - Company HR System");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        JLabel lblWelcome = new JLabel("Welcome, " + user.getUsername());
        lblWelcome.setBounds(20, 20, 300, 25);
        add(lblWelcome);

        JButton btnViewAttendance = new JButton("View Attendance");
        btnViewAttendance.setBounds(150, 80, 200, 30);
        add(btnViewAttendance);

        JButton btnApplyLeave = new JButton("Apply Leave");
        btnApplyLeave.setBounds(150, 130, 200, 30);
        add(btnApplyLeave);

        JButton btnViewPayroll = new JButton("View Payslip");
        btnViewPayroll.setBounds(150, 180, 200, 30);
        add(btnViewPayroll);

        JButton btnLogout = new JButton("Logout");
        btnLogout.setBounds(150, 240, 200, 30);
        add(btnLogout);

        btnLogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new LoginForm().setVisible(true);
            }
        });

        setLocationRelativeTo(null);
    }
}
