package main.util;

import main.DB.DBConnection;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;

public class DatabaseInitializer {
    
    public static void initializeDatabase() {
        try (Connection conn = DBConnection.getConnection()) {
            if (conn == null) {
                System.out.println("Failed to connect to database!");
                return;
            }
            
            System.out.println("Initializing database...");
            
            // Create tables if they don't exist
            createTables(conn);
            
            // Insert sample data
            insertSampleData(conn);
            
            System.out.println(" Database initialized successfully!");
            
        } catch (SQLException e) {
            System.out.println("Error initializing database: " + e.getMessage());
        }
    }
    
    private static void createTables(Connection conn) throws SQLException {
        try (Statement stmt = conn.createStatement()) {
            
            // Create users table
            String createUsersTable = """
                CREATE TABLE IF NOT EXISTS users (
                    user_id INT PRIMARY KEY AUTO_INCREMENT,
                    username VARCHAR(50) UNIQUE NOT NULL,
                    password VARCHAR(100) NOT NULL,
                    role ENUM('Admin','Employee') NOT NULL,
                    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                )
            """;
            stmt.execute(createUsersTable);
            
            // Create departments table
            String createDepartmentsTable = """
                CREATE TABLE IF NOT EXISTS departments (
                    dept_id INT PRIMARY KEY AUTO_INCREMENT,
                    dept_name VARCHAR(100) NOT NULL,
                    manager_id INT,
                    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                    FOREIGN KEY (manager_id) REFERENCES users(user_id)
                )
            """;
            stmt.execute(createDepartmentsTable);
            
            // Create employees table
            String createEmployeesTable = """
                CREATE TABLE IF NOT EXISTS employees (
                    emp_id INT PRIMARY KEY AUTO_INCREMENT,
                    user_id INT NOT NULL,
                    first_name VARCHAR(50),
                    last_name VARCHAR(50),
                    gender ENUM('Male','Female','Other'),
                    dob DATE,
                    email VARCHAR(100),
                    phone VARCHAR(15),
                    address VARCHAR(200),
                    dept_id INT,
                    designation VARCHAR(100),
                    hire_date DATE,
                    salary DECIMAL(10,2),
                    FOREIGN KEY (user_id) REFERENCES users(user_id),
                    FOREIGN KEY (dept_id) REFERENCES departments(dept_id)
                )
            """;
            stmt.execute(createEmployeesTable);
            
            // Create attendance table
            String createAttendanceTable = """
                CREATE TABLE IF NOT EXISTS attendance (
                    att_id INT PRIMARY KEY AUTO_INCREMENT,
                    emp_id INT,
                    att_date DATE,
                    status ENUM('Present','Absent','Leave') DEFAULT 'Absent',
                    FOREIGN KEY (emp_id) REFERENCES employees(emp_id),
                    UNIQUE KEY unique_emp_date (emp_id, att_date)
                )
            """;
            stmt.execute(createAttendanceTable);
            
            // Create leaves table
            String createLeavesTable = """
                CREATE TABLE IF NOT EXISTS leaves (
                    leave_id INT PRIMARY KEY AUTO_INCREMENT,
                    emp_id INT,
                    start_date DATE,
                    end_date DATE,
                    reason VARCHAR(255),
                    status ENUM('Pending','Approved','Rejected') DEFAULT 'Pending',
                    applied_on TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                    FOREIGN KEY (emp_id) REFERENCES employees(emp_id)
                )
            """;
            stmt.execute(createLeavesTable);
            
            // Create payroll table
            String createPayrollTable = """
                CREATE TABLE IF NOT EXISTS payroll (
                    payroll_id INT PRIMARY KEY AUTO_INCREMENT,
                    emp_id INT,
                    basic_salary DECIMAL(10,2),
                    bonus DECIMAL(10,2),
                    deductions DECIMAL(10,2),
                    net_salary DECIMAL(10,2),
                    pay_date DATE,
                    FOREIGN KEY (emp_id) REFERENCES employees(emp_id)
                )
            """;
            stmt.execute(createPayrollTable);
            
            // Create logs table
            String createLogsTable = """
                CREATE TABLE IF NOT EXISTS logs (
                    log_id INT PRIMARY KEY AUTO_INCREMENT,
                    user_id INT,
                    action VARCHAR(255),
                    log_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                    FOREIGN KEY (user_id) REFERENCES users(user_id)
                )
            """;
            stmt.execute(createLogsTable);
            
            System.out.println(" Database tables created successfully!");
        }
    }
    
    private static void insertSampleData(Connection conn) throws SQLException {
        try (Statement stmt = conn.createStatement()) {
            
            // Check if sample data already exists
            String checkUsers = "SELECT COUNT(*) FROM users";
            var result = stmt.executeQuery(checkUsers);
            result.next();
            int userCount = result.getInt(1);
            
            if (userCount > 0) {
                System.out.println("Sample data already exists. Skipping data insertion.");
                return;
            }
            
            // Insert sample users
            String insertUsers = """
                INSERT INTO users (username, password, role) VALUES
                ('admin', 'admin123', 'Admin'),
                ('john.doe', 'password123', 'Employee'),
                ('jane.smith', 'password123', 'Employee'),
                ('mike.wilson', 'password123', 'Employee')
            """;
            stmt.execute(insertUsers);
            
            // Insert sample departments
            String insertDepartments = """
                INSERT INTO departments (dept_name, manager_id) VALUES
                ('Human Resources', 1),
                ('Information Technology', 1),
                ('Sales', 1),
                ('Marketing', 1)
            """;
            stmt.execute(insertDepartments);
            
            // Insert sample employees
            String insertEmployees = """
                INSERT INTO employees (user_id, first_name, last_name, gender, dob, email, phone, address, dept_id, designation, hire_date, salary) VALUES
                (2, 'John', 'Doe', 'Male', '1990-05-15', 'john.doe@company.com', '1234567890', '123 Main St, City', 1, 'HR Manager', '2020-01-15', 75000.00),
                (3, 'Jane', 'Smith', 'Female', '1988-08-22', 'jane.smith@company.com', '2345678901', '456 Oak Ave, City', 2, 'Software Developer', '2019-06-01', 85000.00),
                (4, 'Mike', 'Wilson', 'Male', '1992-03-10', 'mike.wilson@company.com', '3456789012', '789 Pine Rd, City', 3, 'Sales Representative', '2021-03-15', 60000.00)
            """;
            stmt.execute(insertEmployees);
            
            System.out.println("Sample data inserted successfully!");
        }
    }
}
