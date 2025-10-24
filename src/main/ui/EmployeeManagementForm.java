package main.ui;


import main.dao.EmployeeDAO;
import main.model.Employee;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.util.List;

public class EmployeeManagementForm extends JFrame {
    private EmployeeDAO dao = new EmployeeDAO();
    private JTable table;
    private DefaultTableModel model;

    private JTextField txtFirstName, txtLastName, txtEmail, txtPhone, txtDesignation, txtSalary;

    public EmployeeManagementForm() {
        setTitle("Employee Management");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);

        JLabel lblTitle = new JLabel("Manage Employees");
        lblTitle.setBounds(300, 20, 200, 25);
        add(lblTitle);

        JLabel lblFirstName = new JLabel("First Name:");
        lblFirstName.setBounds(30, 70, 100, 25);
        add(lblFirstName);
        txtFirstName = new JTextField();
        txtFirstName.setBounds(130, 70, 150, 25);
        add(txtFirstName);

        JLabel lblLastName = new JLabel("Last Name:");
        lblLastName.setBounds(30, 110, 100, 25);
        add(lblLastName);
        txtLastName = new JTextField();
        txtLastName.setBounds(130, 110, 150, 25);
        add(txtLastName);

        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setBounds(30, 150, 100, 25);
        add(lblEmail);
        txtEmail = new JTextField();
        txtEmail.setBounds(130, 150, 150, 25);
        add(txtEmail);

        JLabel lblPhone = new JLabel("Phone:");
        lblPhone.setBounds(30, 190, 100, 25);
        add(lblPhone);
        txtPhone = new JTextField();
        txtPhone.setBounds(130, 190, 150, 25);
        add(txtPhone);

        JLabel lblDesignation = new JLabel("Designation:");
        lblDesignation.setBounds(30, 230, 100, 25);
        add(lblDesignation);
        txtDesignation = new JTextField();
        txtDesignation.setBounds(130, 230, 150, 25);
        add(txtDesignation);

        JLabel lblSalary = new JLabel("Salary:");
        lblSalary.setBounds(30, 270, 100, 25);
        add(lblSalary);
        txtSalary = new JTextField();
        txtSalary.setBounds(130, 270, 150, 25);
        add(txtSalary);

        JButton btnAdd = new JButton("Add Employee");
        btnAdd.setBounds(50, 320, 150, 30);
        add(btnAdd);

        JButton btnDelete = new JButton("Delete Selected");
        btnDelete.setBounds(220, 320, 150, 30);
        add(btnDelete);

        model = new DefaultTableModel(new String[]{"ID", "First Name", "Last Name", "Email", "Phone", "Designation", "Salary"}, 0);
        table = new JTable(model);
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBounds(320, 70, 440, 400);
        add(scroll);

        loadEmployees();

        btnAdd.addActionListener(e -> {
            Employee emp = new Employee();
            emp.setFirstName(txtFirstName.getText());
            emp.setLastName(txtLastName.getText());
            emp.setEmail(txtEmail.getText());
            emp.setPhone(txtPhone.getText());
            emp.setDesignation(txtDesignation.getText());
            emp.setSalary(Double.parseDouble(txtSalary.getText()));

            if (dao.addEmployee(emp)) {
                JOptionPane.showMessageDialog(null, "Employee Added!");
                loadEmployees();
            } else {
                JOptionPane.showMessageDialog(null, "Failed to add employee.");
            }
        });

        btnDelete.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                int empId = (int) model.getValueAt(selectedRow, 0);
                if (dao.deleteEmployee(empId)) {
                    JOptionPane.showMessageDialog(null, "Employee Deleted!");
                    loadEmployees();
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to delete.");
                }
            }
        });

        setLocationRelativeTo(null);
    }

    private void loadEmployees() {
        model.setRowCount(0);
        List<Employee> list = dao.getAllEmployees();
        for (Employee emp : list) {
            model.addRow(new Object[]{
                    emp.getEmpId(), emp.getFirstName(), emp.getLastName(),
                    emp.getEmail(), emp.getPhone(), emp.getDesignation(), emp.getSalary()
            });
        }
    }
}
