package main.ui;

import main.model.User;
import main.model.Employee;
import main.model.Department;
import main.model.Attendance;
import main.model.LeaveRequest;
import main.model.Payroll;
import main.dao.EmployeeDAO;
import main.dao.DepartmentDAO;
import main.dao.AttendanceDAO;
import main.dao.LeaveRequestDAO;
import main.dao.PayrollDAO;
import main.util.SessionManager;
import main.util.DatabaseHelper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class AdminCLI {
    private User currentUser;
    private Scanner scanner;
    private EmployeeDAO employeeDAO;
    private DepartmentDAO departmentDAO;
    private AttendanceDAO attendanceDAO;
    private LeaveRequestDAO leaveRequestDAO;
    private PayrollDAO payrollDAO;

    public AdminCLI(User user) {
        this.currentUser = user;
        this.scanner = new Scanner(System.in);
        this.employeeDAO = new EmployeeDAO();
        this.departmentDAO = new DepartmentDAO();
        this.attendanceDAO = new AttendanceDAO();
        this.leaveRequestDAO = new LeaveRequestDAO();
        this.payrollDAO = new PayrollDAO();
        
        // Log admin login
        System.out.println("Admin " + currentUser.getUsername() + " logged in successfully.");
    }

    public void showMenu() {
        while (true) {
            System.out.println("\n=== ADMIN DASHBOARD ===");
            System.out.println("1. Employee Management");
            System.out.println("2. Department Management");
            System.out.println("3. Attendance Management");
            System.out.println("4. Leave Management");
            System.out.println("5. Payroll Management");
            System.out.println("6. Reports & Analytics");
            System.out.println("7. Logout");
            System.out.print("Choose an option (1-7): ");

            int choice = getIntInput();
            switch (choice) {
                case 1:
                    employeeManagementMenu();
                    break;
                case 2:
                    departmentManagementMenu();
                    break;
                case 3:
                    attendanceManagementMenu();
                    break;
                case 4:
                    leaveManagementMenu();
                    break;
                case 5:
                    payrollManagementMenu();
                    break;
                case 6:
                    reportsMenu();
                    break;
                case 7:
                    SessionManager.logout();
                    return;
                default:
                    System.out.println("Invalid option! Please try again.");
            }
        }
    }

    private void employeeManagementMenu() {
        while (true) {
            System.out.println("\n=== EMPLOYEE MANAGEMENT ===");
            System.out.println("1. Add Employee");
            System.out.println("2. View All Employees");
            System.out.println("3. Search Employee");
            System.out.println("4. Update Employee");
            System.out.println("5. Delete Employee");
            System.out.println("6. Back to Main Menu");
            System.out.print("Choose an option (1-6): ");

            int choice = getIntInput();
            switch (choice) {
                case 1:
                    addEmployee();
                    break;
                case 2:
                    viewAllEmployees();
                    break;
                case 3:
                    searchEmployee();
                    break;
                case 4:
                    updateEmployee();
                    break;
                case 5:
                    deleteEmployee();
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Invalid option! Please try again.");
            }
        }
    }

    private void addEmployee() {
        System.out.println("\n=== ADD EMPLOYEE ===");
        
        System.out.print("User ID: ");
        int userId = getIntInput();
        
        System.out.print("First Name: ");
        String firstName = scanner.nextLine().trim();
        
        System.out.print("Last Name: ");
        String lastName = scanner.nextLine().trim();
        
        System.out.print("Gender (Male/Female/Other): ");
        String gender = scanner.nextLine().trim();
        
        System.out.print("Date of Birth (YYYY-MM-DD): ");
        LocalDate dob = LocalDate.parse(scanner.nextLine().trim());
        
        System.out.print("Email: ");
        String email = scanner.nextLine().trim();
        
        System.out.print("Phone: ");
        String phone = scanner.nextLine().trim();
        
        System.out.print("Address: ");
        String address = scanner.nextLine().trim();
        
        System.out.println("\nAvailable Department IDs:");
        DatabaseHelper.displayAvailableDepartmentIds();
        System.out.print("Department ID: ");
        int deptId = getIntInput();
        
        // Validate department ID
        if (!DatabaseHelper.isValidDepartmentId(deptId)) {
            System.out.println("✗ Invalid Department ID! Please choose from the available IDs above.");
            return;
        }
        
        System.out.print("Designation: ");
        String designation = scanner.nextLine().trim();
        
        System.out.print("Hire Date (YYYY-MM-DD): ");
        LocalDate hireDate = LocalDate.parse(scanner.nextLine().trim());
        
        System.out.print("Salary: ");
        double salary = getDoubleInput();

        Employee employee = new Employee(userId, firstName, lastName, gender, dob, email, phone, address, deptId, designation, hireDate, salary);
        
        if (employeeDAO.addEmployee(employee)) {
            System.out.println("✓ Employee added successfully!");
        } else {
            System.out.println("✗ Failed to add employee!");
        }
    }

    private void viewAllEmployees() {
        System.out.println("\n=== ALL EMPLOYEES ===");
        List<Employee> employees = employeeDAO.getAllEmployees();
        
        if (employees.isEmpty()) {
            System.out.println("No employees found.");
            return;
        }

        System.out.printf("%-5s %-15s %-15s %-25s %-15s %-10s%n", "ID", "First Name", "Last Name", "Email", "Designation", "Salary");
        System.out.println("=".repeat(90));
        
        for (Employee emp : employees) {
            System.out.printf("%-5d %-15s %-15s %-25s %-15s %-10.2f%n", 
                emp.getEmpId(), emp.getFirstName(), emp.getLastName(), emp.getEmail(), emp.getDesignation(), emp.getSalary());
        }
    }

    private void searchEmployee() {
        System.out.println("\n=== SEARCH EMPLOYEE ===");
        System.out.println("1. Search by ID");
        System.out.println("2. Search by Name");
        System.out.println("3. Search by Department");
        System.out.print("Choose search option (1-3): ");

        int choice = getIntInput();
        switch (choice) {
            case 1:
                System.out.print("Enter Employee ID: ");
                int empId = getIntInput();
                Employee emp = employeeDAO.getEmployeeById(empId);
                if (emp != null) {
                    displayEmployeeDetails(emp);
                } else {
                    System.out.println("Employee not found!");
                }
                break;
            case 2:
                System.out.print("Enter Name: ");
                String name = scanner.nextLine().trim();
                List<Employee> employees = employeeDAO.searchEmployeesByName(name);
                displayEmployeeList(employees);
                break;
            case 3:
                System.out.print("Enter Department ID: ");
                int deptId = getIntInput();
                List<Employee> deptEmployees = employeeDAO.getEmployeesByDepartment(deptId);
                displayEmployeeList(deptEmployees);
                break;
            default:
                System.out.println("Invalid option!");
        }
    }

    private void updateEmployee() {
        System.out.println("\n=== UPDATE EMPLOYEE ===");
        System.out.print("Enter Employee ID: ");
        int empId = getIntInput();
        
        Employee employee = employeeDAO.getEmployeeById(empId);
        if (employee == null) {
            System.out.println("Employee not found!");
            return;
        }

        System.out.println("Current Employee Details:");
        displayEmployeeDetails(employee);
        
        System.out.println("\nEnter new details (press Enter to keep current value):");
        
        System.out.print("First Name [" + employee.getFirstName() + "]: ");
        String firstName = scanner.nextLine().trim();
        if (!firstName.isEmpty()) employee.setFirstName(firstName);
        
        System.out.print("Last Name [" + employee.getLastName() + "]: ");
        String lastName = scanner.nextLine().trim();
        if (!lastName.isEmpty()) employee.setLastName(lastName);
        
        System.out.print("Email [" + employee.getEmail() + "]: ");
        String email = scanner.nextLine().trim();
        if (!email.isEmpty()) employee.setEmail(email);
        
        System.out.print("Phone [" + employee.getPhone() + "]: ");
        String phone = scanner.nextLine().trim();
        if (!phone.isEmpty()) employee.setPhone(phone);
        
        System.out.print("Designation [" + employee.getDesignation() + "]: ");
        String designation = scanner.nextLine().trim();
        if (!designation.isEmpty()) employee.setDesignation(designation);
        
        System.out.print("Salary [" + employee.getSalary() + "]: ");
        String salaryStr = scanner.nextLine().trim();
        if (!salaryStr.isEmpty()) employee.setSalary(Double.parseDouble(salaryStr));

        if (employeeDAO.updateEmployee(employee)) {
            System.out.println("✓ Employee updated successfully!");
        } else {
            System.out.println("✗ Failed to update employee!");
        }
    }

    private void deleteEmployee() {
        System.out.println("\n=== DELETE EMPLOYEE ===");
        System.out.print("Enter Employee ID: ");
        int empId = getIntInput();
        
        Employee employee = employeeDAO.getEmployeeById(empId);
        if (employee == null) {
            System.out.println("Employee not found!");
            return;
        }

        System.out.println("Employee to delete:");
        displayEmployeeDetails(employee);
        
        System.out.print("Are you sure you want to delete this employee? (y/n): ");
        String confirm = scanner.nextLine().trim().toLowerCase();
        
        if (confirm.equals("y") || confirm.equals("yes")) {
            if (employeeDAO.deleteEmployee(empId)) {
                System.out.println("✓ Employee deleted successfully!");
            } else {
                System.out.println("✗ Failed to delete employee!");
            }
        } else {
            System.out.println("Deletion cancelled.");
        }
    }

    private void departmentManagementMenu() {
        while (true) {
            System.out.println("\n=== DEPARTMENT MANAGEMENT ===");
            System.out.println("1. Add Department");
            System.out.println("2. View All Departments");
            System.out.println("3. Update Department");
            System.out.println("4. Delete Department");
            System.out.println("5. Back to Main Menu");
            System.out.print("Choose an option (1-5): ");

            int choice = getIntInput();
            switch (choice) {
                case 1:
                    addDepartment();
                    break;
                case 2:
                    viewAllDepartments();
                    break;
                case 3:
                    updateDepartment();
                    break;
                case 4:
                    deleteDepartment();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid option! Please try again.");
            }
        }
    }

    private void addDepartment() {
        System.out.println("\n=== ADD DEPARTMENT ===");
        
        System.out.print("Department Name: ");
        String deptName = scanner.nextLine().trim();
        
        System.out.println("\nAvailable User IDs for Manager:");
        DatabaseHelper.displayAvailableUserIds();
        System.out.print("Manager ID: ");
        int managerId = getIntInput();
        
        // Validate manager ID
        if (!DatabaseHelper.isValidUserId(managerId)) {
            System.out.println("✗ Invalid Manager ID! Please choose from the available IDs above.");
            return;
        }

        Department department = new Department(0, deptName, managerId);
        
        if (departmentDAO.addDepartment(department)) {
            System.out.println("✓ Department added successfully!");
        } else {
            System.out.println("✗ Failed to add department!");
        }
    }

    private void viewAllDepartments() {
        System.out.println("\n=== ALL DEPARTMENTS ===");
        List<Department> departments = departmentDAO.getAllDepartments();
        
        if (departments.isEmpty()) {
            System.out.println("No departments found.");
            return;
        }

        System.out.printf("%-5s %-20s %-10s %-10s%n", "ID", "Name", "Manager ID", "Employees");
        System.out.println("=".repeat(50));
        
        for (Department dept : departments) {
            int empCount = departmentDAO.getEmployeeCountByDepartment(dept.getDeptId());
            System.out.printf("%-5d %-20s %-10d %-10d%n", 
                dept.getDeptId(), dept.getDeptName(), dept.getManagerId(), empCount);
        }
    }

    private void updateDepartment() {
        System.out.println("\n=== UPDATE DEPARTMENT ===");
        System.out.print("Enter Department ID: ");
        int deptId = getIntInput();
        
        Department department = departmentDAO.getDepartmentById(deptId);
        if (department == null) {
            System.out.println("Department not found!");
            return;
        }

        System.out.println("Current Department Details:");
        System.out.println("ID: " + department.getDeptId());
        System.out.println("Name: " + department.getDeptName());
        System.out.println("Manager ID: " + department.getManagerId());
        
        System.out.println("\nEnter new details (press Enter to keep current value):");
        
        System.out.print("Department Name [" + department.getDeptName() + "]: ");
        String deptName = scanner.nextLine().trim();
        if (!deptName.isEmpty()) department.setDeptName(deptName);
        
        System.out.print("Manager ID [" + department.getManagerId() + "]: ");
        String managerIdStr = scanner.nextLine().trim();
        if (!managerIdStr.isEmpty()) department.setManagerId(Integer.parseInt(managerIdStr));

        if (departmentDAO.updateDepartment(department)) {
            System.out.println("✓ Department updated successfully!");
        } else {
            System.out.println("✗ Failed to update department!");
        }
    }

    private void deleteDepartment() {
        System.out.println("\n=== DELETE DEPARTMENT ===");
        System.out.print("Enter Department ID: ");
        int deptId = getIntInput();
        
        Department department = departmentDAO.getDepartmentById(deptId);
        if (department == null) {
            System.out.println("Department not found!");
            return;
        }

        System.out.println("Department to delete:");
        System.out.println("ID: " + department.getDeptId());
        System.out.println("Name: " + department.getDeptName());
        
        System.out.print("Are you sure you want to delete this department? (y/n): ");
        String confirm = scanner.nextLine().trim().toLowerCase();
        
        if (confirm.equals("y") || confirm.equals("yes")) {
            if (departmentDAO.deleteDepartment(deptId)) {
                System.out.println("✓ Department deleted successfully!");
            } else {
                System.out.println("✗ Failed to delete department!");
            }
        } else {
            System.out.println("Deletion cancelled.");
        }
    }

    private void attendanceManagementMenu() {
        while (true) {
            System.out.println("\n=== ATTENDANCE MANAGEMENT ===");
            System.out.println("1. Mark Attendance");
            System.out.println("2. View Daily Attendance");
            System.out.println("3. View Employee Attendance");
            System.out.println("4. Monthly Attendance Report");
            System.out.println("5. Back to Main Menu");
            System.out.print("Choose an option (1-5): ");

            int choice = getIntInput();
            switch (choice) {
                case 1:
                    markAttendance();
                    break;
                case 2:
                    viewDailyAttendance();
                    break;
                case 3:
                    viewEmployeeAttendance();
                    break;
                case 4:
                    monthlyAttendanceReport();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid option! Please try again.");
            }
        }
    }

    private void markAttendance() {
        System.out.println("\n=== MARK ATTENDANCE ===");
        
        System.out.print("Employee ID: ");
        int empId = getIntInput();
        
        System.out.print("Date (YYYY-MM-DD) [Today]: ");
        String dateStr = scanner.nextLine().trim();
        LocalDate date = dateStr.isEmpty() ? LocalDate.now() : LocalDate.parse(dateStr);
        
        System.out.print("Status (Present/Absent/Leave): ");
        String status = scanner.nextLine().trim();

        Attendance attendance = new Attendance(empId, date, status);
        
        if (attendanceDAO.markAttendance(attendance)) {
            System.out.println("✓ Attendance marked successfully!");
        } else {
            System.out.println("✗ Failed to mark attendance!");
        }
    }

    private void viewDailyAttendance() {
        System.out.println("\n=== DAILY ATTENDANCE ===");
        
        System.out.print("Date (YYYY-MM-DD) [Today]: ");
        String dateStr = scanner.nextLine().trim();
        LocalDate date = dateStr.isEmpty() ? LocalDate.now() : LocalDate.parse(dateStr);
        
        List<Attendance> attendanceList = attendanceDAO.getAttendanceByDate(date);
        
        if (attendanceList.isEmpty()) {
            System.out.println("No attendance records found for " + date);
            return;
        }

        System.out.println("Attendance for " + date + ":");
        System.out.printf("%-10s %-15s%n", "Employee ID", "Status");
        System.out.println("=".repeat(30));
        
        for (Attendance att : attendanceList) {
            System.out.printf("%-10d %-15s%n", att.getEmpId(), att.getStatus());
        }
    }

    private void viewEmployeeAttendance() {
        System.out.println("\n=== EMPLOYEE ATTENDANCE ===");
        
        System.out.print("Employee ID: ");
        int empId = getIntInput();
        
        List<Attendance> attendanceList = attendanceDAO.getAttendanceByEmployee(empId);
        
        if (attendanceList.isEmpty()) {
            System.out.println("No attendance records found for employee " + empId);
            return;
        }

        System.out.println("Attendance records for Employee " + empId + ":");
        System.out.printf("%-12s %-15s%n", "Date", "Status");
        System.out.println("=".repeat(30));
        
        for (Attendance att : attendanceList) {
            System.out.printf("%-12s %-15s%n", att.getAttDate(), att.getStatus());
        }
    }

    private void monthlyAttendanceReport() {
        System.out.println("\n=== MONTHLY ATTENDANCE REPORT ===");
        
        System.out.print("Employee ID: ");
        int empId = getIntInput();
        
        System.out.print("Year: ");
        int year = getIntInput();
        
        System.out.print("Month (1-12): ");
        int month = getIntInput();
        
        List<Attendance> attendanceList = attendanceDAO.getMonthlyAttendance(empId, year, month);
        int presentDays = attendanceDAO.getPresentDays(empId, year, month);
        int absentDays = attendanceDAO.getAbsentDays(empId, year, month);
        
        System.out.println("\nMonthly Attendance Report for Employee " + empId + " (" + year + "-" + String.format("%02d", month) + "):");
        System.out.println("Present Days: " + presentDays);
        System.out.println("Absent Days: " + absentDays);
        System.out.println("Total Working Days: " + attendanceList.size());
        
        if (!attendanceList.isEmpty()) {
            System.out.println("\nDaily Records:");
            System.out.printf("%-12s %-15s%n", "Date", "Status");
            System.out.println("=".repeat(30));
            
            for (Attendance att : attendanceList) {
                System.out.printf("%-12s %-15s%n", att.getAttDate(), att.getStatus());
            }
        }
    }

    private void leaveManagementMenu() {
        while (true) {
            System.out.println("\n=== LEAVE MANAGEMENT ===");
            System.out.println("1. View Pending Leave Requests");
            System.out.println("2. Approve/Reject Leave Request");
            System.out.println("3. View All Leave Requests");
            System.out.println("4. Back to Main Menu");
            System.out.print("Choose an option (1-4): ");

            int choice = getIntInput();
            switch (choice) {
                case 1:
                    viewPendingLeaveRequests();
                    break;
                case 2:
                    approveRejectLeaveRequest();
                    break;
                case 3:
                    viewAllLeaveRequests();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Invalid option! Please try again.");
            }
        }
    }

    private void viewPendingLeaveRequests() {
        System.out.println("\n=== PENDING LEAVE REQUESTS ===");
        List<LeaveRequest> pendingRequests = leaveRequestDAO.getPendingLeaveRequests();
        
        if (pendingRequests.isEmpty()) {
            System.out.println("No pending leave requests.");
            return;
        }

        System.out.printf("%-8s %-10s %-12s %-12s %-20s%n", "Leave ID", "Employee ID", "Start Date", "End Date", "Reason");
        System.out.println("=".repeat(70));
        
        for (LeaveRequest leave : pendingRequests) {
            System.out.printf("%-8d %-10d %-12s %-12s %-20s%n", 
                leave.getLeaveId(), leave.getEmpId(), leave.getStartDate(), leave.getEndDate(), leave.getReason());
        }
    }

    private void approveRejectLeaveRequest() {
        System.out.println("\n=== APPROVE/REJECT LEAVE REQUEST ===");
        
        System.out.print("Leave Request ID: ");
        int leaveId = getIntInput();
        
        LeaveRequest leaveRequest = leaveRequestDAO.getLeaveRequestById(leaveId);
        if (leaveRequest == null) {
            System.out.println("Leave request not found!");
            return;
        }

        System.out.println("Leave Request Details:");
        System.out.println("Employee ID: " + leaveRequest.getEmpId());
        System.out.println("Start Date: " + leaveRequest.getStartDate());
        System.out.println("End Date: " + leaveRequest.getEndDate());
        System.out.println("Reason: " + leaveRequest.getReason());
        System.out.println("Status: " + leaveRequest.getStatus());
        
        System.out.print("Action (Approve/Reject): ");
        String action = scanner.nextLine().trim().toLowerCase();
        
        String newStatus;
        if (action.equals("approve")) {
            newStatus = "Approved";
        } else if (action.equals("reject")) {
            newStatus = "Rejected";
        } else {
            System.out.println("Invalid action!");
            return;
        }

        if (leaveRequestDAO.updateLeaveStatus(leaveId, newStatus)) {
            System.out.println("✓ Leave request " + newStatus.toLowerCase() + " successfully!");
        } else {
            System.out.println("✗ Failed to update leave request!");
        }
    }

    private void viewAllLeaveRequests() {
        System.out.println("\n=== ALL LEAVE REQUESTS ===");
        List<LeaveRequest> allRequests = leaveRequestDAO.getAllLeaveRequests();
        
        if (allRequests.isEmpty()) {
            System.out.println("No leave requests found.");
            return;
        }

        System.out.printf("%-8s %-10s %-12s %-12s %-10s %-20s%n", "Leave ID", "Employee ID", "Start Date", "End Date", "Status", "Reason");
        System.out.println("=".repeat(80));
        
        for (LeaveRequest leave : allRequests) {
            System.out.printf("%-8d %-10d %-12s %-12s %-10s %-20s%n", 
                leave.getLeaveId(), leave.getEmpId(), leave.getStartDate(), leave.getEndDate(), leave.getStatus(), leave.getReason());
        }
    }

    private void payrollManagementMenu() {
        while (true) {
            System.out.println("\n=== PAYROLL MANAGEMENT ===");
            System.out.println("1. Generate Payroll");
            System.out.println("2. View Employee Payroll");
            System.out.println("3. View Monthly Payroll");
            System.out.println("4. View All Payroll Records");
            System.out.println("5. Back to Main Menu");
            System.out.print("Choose an option (1-5): ");

            int choice = getIntInput();
            switch (choice) {
                case 1:
                    generatePayroll();
                    break;
                case 2:
                    viewEmployeePayroll();
                    break;
                case 3:
                    viewMonthlyPayroll();
                    break;
                case 4:
                    viewAllPayrollRecords();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid option! Please try again.");
            }
        }
    }

    private void generatePayroll() {
        System.out.println("\n=== GENERATE PAYROLL ===");
        
        System.out.print("Employee ID: ");
        int empId = getIntInput();
        
        System.out.print("Year: ");
        int year = getIntInput();
        
        System.out.print("Month (1-12): ");
        int month = getIntInput();
        
        Employee employee = employeeDAO.getEmployeeById(empId);
        if (employee == null) {
            System.out.println("Employee not found!");
            return;
        }

        System.out.print("Basic Salary [" + employee.getSalary() + "]: ");
        String basicSalaryStr = scanner.nextLine().trim();
        double basicSalary = basicSalaryStr.isEmpty() ? employee.getSalary() : Double.parseDouble(basicSalaryStr);
        
        System.out.print("Bonus: ");
        double bonus = getDoubleInput();
        
        System.out.print("Deductions: ");
        double deductions = getDoubleInput();

        if (payrollDAO.generateMonthlyPayroll(empId, year, month, basicSalary, bonus, deductions)) {
            System.out.println("✓ Payroll generated successfully!");
        } else {
            System.out.println("✗ Failed to generate payroll!");
        }
    }

    private void viewEmployeePayroll() {
        System.out.println("\n=== EMPLOYEE PAYROLL ===");
        
        System.out.print("Employee ID: ");
        int empId = getIntInput();
        
        List<Payroll> payrollList = payrollDAO.getPayrollByEmployee(empId);
        
        if (payrollList.isEmpty()) {
            System.out.println("No payroll records found for employee " + empId);
            return;
        }

        System.out.println("Payroll records for Employee " + empId + ":");
        System.out.printf("%-10s %-12s %-10s %-10s %-12s %-12s%n", "Payroll ID", "Basic Salary", "Bonus", "Deductions", "Net Salary", "Pay Date");
        System.out.println("=".repeat(80));
        
        for (Payroll payroll : payrollList) {
            System.out.printf("%-10d %-12.2f %-10.2f %-10.2f %-12.2f %-12s%n", 
                payroll.getPayrollId(), payroll.getBasicSalary(), payroll.getBonus(), 
                payroll.getDeductions(), payroll.getNetSalary(), payroll.getPayDate());
        }
    }

    private void viewMonthlyPayroll() {
        System.out.println("\n=== MONTHLY PAYROLL ===");
        
        System.out.print("Year: ");
        int year = getIntInput();
        
        System.out.print("Month (1-12): ");
        int month = getIntInput();
        
        List<Payroll> payrollList = payrollDAO.getMonthlyPayroll(year, month);
        
        if (payrollList.isEmpty()) {
            System.out.println("No payroll records found for " + year + "-" + String.format("%02d", month));
            return;
        }

        System.out.println("Monthly Payroll for " + year + "-" + String.format("%02d", month) + ":");
        System.out.printf("%-10s %-12s %-10s %-10s %-12s%n", "Employee ID", "Basic Salary", "Bonus", "Deductions", "Net Salary");
        System.out.println("=".repeat(70));
        
        for (Payroll payroll : payrollList) {
            System.out.printf("%-10d %-12.2f %-10.2f %-10.2f %-12.2f%n", 
                payroll.getEmpId(), payroll.getBasicSalary(), payroll.getBonus(), 
                payroll.getDeductions(), payroll.getNetSalary());
        }
    }

    private void viewAllPayrollRecords() {
        System.out.println("\n=== ALL PAYROLL RECORDS ===");
        List<Payroll> allPayroll = payrollDAO.getAllPayroll();
        
        if (allPayroll.isEmpty()) {
            System.out.println("No payroll records found.");
            return;
        }

        System.out.printf("%-10s %-10s %-12s %-10s %-10s %-12s %-12s%n", "Payroll ID", "Employee ID", "Basic Salary", "Bonus", "Deductions", "Net Salary", "Pay Date");
        System.out.println("=".repeat(90));
        
        for (Payroll payroll : allPayroll) {
            System.out.printf("%-10d %-10d %-12.2f %-10.2f %-10.2f %-12.2f %-12s%n", 
                payroll.getPayrollId(), payroll.getEmpId(), payroll.getBasicSalary(), 
                payroll.getBonus(), payroll.getDeductions(), payroll.getNetSalary(), payroll.getPayDate());
        }
    }

    private void reportsMenu() {
        while (true) {
            System.out.println("\n=== REPORTS & ANALYTICS ===");
            System.out.println("1. Employee Statistics");
            System.out.println("2. Department Statistics");
            System.out.println("3. Top Paid Employees");
            System.out.println("4. Attendance Summary");
            System.out.println("5. Back to Main Menu");
            System.out.print("Choose an option (1-5): ");

            int choice = getIntInput();
            switch (choice) {
                case 1:
                    employeeStatistics();
                    break;
                case 2:
                    departmentStatistics();
                    break;
                case 3:
                    topPaidEmployees();
                    break;
                case 4:
                    attendanceSummary();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid option! Please try again.");
            }
        }
    }

    private void employeeStatistics() {
        System.out.println("\n=== EMPLOYEE STATISTICS ===");
        
        List<Employee> employees = employeeDAO.getAllEmployees();
        System.out.println("Total Employees: " + employees.size());
        
        if (!employees.isEmpty()) {
            double totalSalary = employees.stream().mapToDouble(Employee::getSalary).sum();
            double avgSalary = totalSalary / employees.size();
            double maxSalary = employees.stream().mapToDouble(Employee::getSalary).max().orElse(0);
            double minSalary = employees.stream().mapToDouble(Employee::getSalary).min().orElse(0);
            
            System.out.println("Total Salary Budget: $" + String.format("%.2f", totalSalary));
            System.out.println("Average Salary: $" + String.format("%.2f", avgSalary));
            System.out.println("Highest Salary: $" + String.format("%.2f", maxSalary));
            System.out.println("Lowest Salary: $" + String.format("%.2f", minSalary));
        }
    }

    private void departmentStatistics() {
        System.out.println("\n=== DEPARTMENT STATISTICS ===");
        
        List<Department> departments = departmentDAO.getAllDepartments();
        
        if (departments.isEmpty()) {
            System.out.println("No departments found.");
            return;
        }

        System.out.printf("%-5s %-20s %-10s%n", "ID", "Department", "Employees");
        System.out.println("=".repeat(40));
        
        for (Department dept : departments) {
            int empCount = departmentDAO.getEmployeeCountByDepartment(dept.getDeptId());
            System.out.printf("%-5d %-20s %-10d%n", dept.getDeptId(), dept.getDeptName(), empCount);
        }
    }

    private void topPaidEmployees() {
        System.out.println("\n=== TOP PAID EMPLOYEES ===");
        
        List<Employee> employees = employeeDAO.getAllEmployees();
        
        if (employees.isEmpty()) {
            System.out.println("No employees found.");
            return;
        }

        employees.sort((e1, e2) -> Double.compare(e2.getSalary(), e1.getSalary()));
        
        System.out.println("Top 3 Highest Paid Employees:");
        System.out.printf("%-5s %-20s %-15s %-10s%n", "ID", "Name", "Designation", "Salary");
        System.out.println("=".repeat(55));
        
        int count = Math.min(3, employees.size());
        for (int i = 0; i < count; i++) {
            Employee emp = employees.get(i);
            System.out.printf("%-5d %-20s %-15s %-10.2f%n", 
                emp.getEmpId(), emp.getFullName(), emp.getDesignation(), emp.getSalary());
        }
    }

    private void attendanceSummary() {
        System.out.println("\n=== ATTENDANCE SUMMARY ===");
        
        System.out.print("Year: ");
        int year = getIntInput();
        
        System.out.print("Month (1-12): ");
        int month = getIntInput();
        
        List<Employee> employees = employeeDAO.getAllEmployees();
        
        if (employees.isEmpty()) {
            System.out.println("No employees found.");
            return;
        }

        System.out.println("Monthly Attendance Summary for " + year + "-" + String.format("%02d", month) + ":");
        System.out.printf("%-10s %-20s %-10s %-10s%n", "Employee ID", "Name", "Present Days", "Absent Days");
        System.out.println("=".repeat(55));
        
        for (Employee emp : employees) {
            int presentDays = attendanceDAO.getPresentDays(emp.getEmpId(), year, month);
            int absentDays = attendanceDAO.getAbsentDays(emp.getEmpId(), year, month);
            System.out.printf("%-10d %-20s %-10d %-10d%n", 
                emp.getEmpId(), emp.getFullName(), presentDays, absentDays);
        }
    }

    private void displayEmployeeDetails(Employee employee) {
        System.out.println("\nEmployee Details:");
        System.out.println("ID: " + employee.getEmpId());
        System.out.println("Name: " + employee.getFullName());
        System.out.println("Email: " + employee.getEmail());
        System.out.println("Phone: " + employee.getPhone());
        System.out.println("Designation: " + employee.getDesignation());
        System.out.println("Department ID: " + employee.getDeptId());
        System.out.println("Salary: $" + String.format("%.2f", employee.getSalary()));
        System.out.println("Hire Date: " + employee.getHireDate());
    }

    private void displayEmployeeList(List<Employee> employees) {
        if (employees.isEmpty()) {
            System.out.println("No employees found.");
            return;
        }

        System.out.printf("%-5s %-15s %-15s %-25s %-15s %-10s%n", "ID", "First Name", "Last Name", "Email", "Designation", "Salary");
        System.out.println("=".repeat(90));
        
        for (Employee emp : employees) {
            System.out.printf("%-5d %-15s %-15s %-25s %-15s %-10.2f%n", 
                emp.getEmpId(), emp.getFirstName(), emp.getLastName(), emp.getEmail(), emp.getDesignation(), emp.getSalary());
        }
    }

    private int getIntInput() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("Please enter a valid number: ");
            }
        }
    }

    private double getDoubleInput() {
        while (true) {
            try {
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("Please enter a valid number: ");
            }
        }
    }
}
