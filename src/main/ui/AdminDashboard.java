package main.ui;

import main.model.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminDashboard extends JFrame {
    private User adminUser;

    public AdminDashboard(User user) {
        this.adminUser = user;

        setTitle("Admin Dashboard - Company HR System");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        JLabel lblWelcome = new JLabel("Welcome, " + user.getUsername() + " (Admin)");
        lblWelcome.setBounds(20, 20, 300, 25);
        add(lblWelcome);

        JButton btnEmployees = new JButton("Manage Employees");
        btnEmployees.setBounds(150, 70, 200, 30);
        add(btnEmployees);

        JButton btnDepartments = new JButton("Manage Departments");
        btnDepartments.setBounds(150, 120, 200, 30);
        add(btnDepartments);

        JButton btnAttendance = new JButton("View Attendance");
        btnAttendance.setBounds(150, 170, 200, 30);
        add(btnAttendance);

        JButton btnPayroll = new JButton("Generate Payroll");
        btnPayroll.setBounds(150, 220, 200, 30);
        add(btnPayroll);

        JButton btnLogout = new JButton("Logout");
        btnLogout.setBounds(150, 280, 200, 30);
        add(btnLogout);

        btnLogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new LoginForm().setVisible(true);
            }
        });
        btnEmployees.addActionListener(e -> new EmployeeManagementForm().setVisible(true));

        setLocationRelativeTo(null);
    }
}
