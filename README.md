# Company HR Management System

A comprehensive Command Line Interface (CLI) based Human Resources Management System built with Java, JDBC, and MySQL.

## 🎯 Project Overview

This system automates and manages core HR operations including employee management, department tracking, payroll processing, attendance monitoring, and leave management using Java and JDBC for database connectivity.

## 🚀 Features

### Admin Module
- **Authentication**: Secure login system with role-based access
- **Dashboard**: Overview with total employees, departments, and active staff count
- **Employee Management**: Complete CRUD operations for employee records
- **Department Management**: Create, update, and manage departments
- **Attendance Management**: Mark and track daily attendance
- **Leave Management**: Approve/reject leave requests
- **Payroll Management**: Generate monthly payslips and manage salary records
- **Reports & Analytics**: Comprehensive reporting system

### Employee Module
- **Profile Management**: View personal and work information
- **Attendance**: Mark daily attendance and view attendance history
- **Leave Requests**: Apply for leave and track request status
- **Payroll**: View payslips and salary information

## 🛠️ Technology Stack

- **Backend**: Java 8+
- **Database**: MySQL 8.0+
- **Database Connectivity**: JDBC
- **Interface**: Command Line Interface (CLI)
- **Build Tool**: Manual compilation or IDE

## 📋 Prerequisites

- Java Development Kit (JDK) 8 or higher
- MySQL Server 8.0 or higher
- MySQL Connector/J (included in lib folder)

## 🗄️ Database Schema

The system uses the following main tables:
- `users` - User authentication and roles
- `departments` - Department information
- `employees` - Employee details and work information
- `attendance` - Daily attendance records
- `leaves` - Leave requests and approvals
- `payroll` - Salary and payroll information
- `logs` - System activity logs

## 🚀 Installation & Setup

### 1. Database Setup
```sql
-- Create database
CREATE DATABASE company_hr_system;

-- Use the database
USE company_hr_system;
```

### 2. Database Configuration
Update the database connection settings in `src/main/DB/DBConnection.java`:
```java
private static final String URL = "jdbc:mysql://localhost:3306/company_hr_system?useSSL=false&serverTimezone=UTC";
private static final String USER = "root"; // your DB username
private static final String PASSWORD = ""; // your DB password
```

### 3. Compilation
```bash
# Compile all Java files
javac -cp "lib/mysql-connector-j-9.3.0.jar" -d . src/*.java src/main/**/*.java

# Run the application
java -cp ".:lib/mysql-connector-j-9.3.0.jar" HRManagementCLI
```

### 4. Windows Compilation
```cmd
# Compile all Java files
javac -cp "lib\mysql-connector-j-9.3.0.jar" -d . src\*.java src\main\**\*.java

# Run the application
java -cp ".;lib\mysql-connector-j-9.3.0.jar" HRManagementCLI
```

## 👥 Default Login Credentials

The system comes with pre-configured sample users:

### Admin Account
- **Username**: admin
- **Password**: admin123
- **Role**: Admin

### Employee Accounts
- **Username**: john.doe
- **Password**: password123
- **Role**: Employee

- **Username**: jane.smith
- **Password**: password123
- **Role**: Employee

- **Username**: mike.wilson
- **Password**: password123
- **Role**: Employee

## 📁 Project Structure

```
CompanyHRSystem/
├── src/
│   ├── HRManagementCLI.java          # Main application entry point
│   ├── Main.java                     # Original GUI entry point
│   └── main/
│       ├── DB/
│       │   └── DBConnection.java     # Database connection utility
│       ├── dao/                      # Data Access Objects
│       │   ├── AttendanceDAO.java
│       │   ├── DepartmentDAO.java
│       │   ├── EmployeeDAO.java
│       │   ├── LeaveRequestDAO.java
│       │   ├── PayrollDAO.java
│       │   └── UserDAO.java
│       ├── model/                    # Entity models
│       │   ├── Attendance.java
│       │   ├── Department.java
│       │   ├── Employee.java
│       │   ├── LeaveRequest.java
│       │   ├── Payroll.java
│       │   └── User.java
│       ├── ui/                       # User Interface classes
│       │   ├── AdminCLI.java
│       │   ├── CLILogin.java
│       │   ├── EmployeeCLI.java
│       │   ├── EmployeeDashboard.java
│       │   ├── EmployeeManagementForm.java
│       │   └── LoginForm.java
│       └── util/                     # Utility classes
│           ├── DatabaseInitializer.java
│           └── InputValidator.java
├── lib/
│   └── mysql-connector-j-9.3.0.jar   # MySQL JDBC driver
└── README.md
```

## 🔧 Key Features Implementation

### Database Connectivity (JDBC Features)
- ✅ Database connection using DriverManager
- ✅ PreparedStatement for secure queries
- ✅ ResultSet for data retrieval
- ✅ CallableStatement support
- ✅ Exception handling for SQL errors
- ✅ Proper connection closing and transaction management
- ✅ SQL Injection prevention

### Security Features
- ✅ Role-based authentication
- ✅ Secure password handling
- ✅ Input validation and sanitization
- ✅ SQL injection prevention

### Additional Features
- ✅ Comprehensive input validation
- ✅ Error handling and user feedback
- ✅ Data export capabilities (can be extended)
- ✅ Logging system for audit trails

## 📊 Reports & Analytics

The system provides various reports including:
- Employee statistics and demographics
- Department-wise employee distribution
- Top paid employees
- Monthly attendance summaries
- Payroll reports
- Leave request analytics

## 🎮 Usage Guide

### For Administrators
1. Login with admin credentials
2. Access the Admin Dashboard
3. Manage employees, departments, attendance, leaves, and payroll
4. Generate reports and analytics
5. Approve/reject leave requests

### For Employees
1. Login with employee credentials
2. Access the Employee Dashboard
3. View personal profile
4. Mark daily attendance
5. Apply for leave
6. View payslips and attendance history

## 🔍 Sample Queries

The system implements various database queries including:
- Complex joins for reporting
- Aggregation functions for statistics
- Date-based filtering
- Search functionality
- Sorting and pagination

## 🐛 Troubleshooting

### Common Issues
1. **Database Connection Error**: Verify MySQL server is running and credentials are correct
2. **Class Not Found**: Ensure MySQL connector JAR is in classpath
3. **SQL Errors**: Check database schema and table creation
4. **Compilation Errors**: Verify Java version and file paths

### Debug Mode
Enable debug logging by modifying the database connection class to show detailed error messages.

## 🔮 Future Enhancements

- GUI interface option
- Email notifications
- Advanced reporting with charts
- Mobile app integration
- API development
- Cloud deployment
- Advanced security features

## 📝 License

This project is created for educational purposes as part of MCA coursework.

## 👨‍💻 Developer

Created as part of MCA Semester 1 Innovative Project requirements.

---

**Note**: This is a CLI-based application. For GUI functionality, refer to the existing GUI components in the project.
