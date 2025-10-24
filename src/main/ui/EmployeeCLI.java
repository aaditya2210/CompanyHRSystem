package main.ui;

import main.model.User;
import main.model.Employee;
import main.model.Attendance;
import main.model.LeaveRequest;
import main.model.Payroll;
import main.dao.EmployeeDAO;
import main.dao.AttendanceDAO;
import main.dao.LeaveRequestDAO;
import main.dao.PayrollDAO;
import main.util.SessionManager;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class EmployeeCLI {
    private User currentUser;
    private Scanner scanner;
    private EmployeeDAO employeeDAO;
    private AttendanceDAO attendanceDAO;
    private LeaveRequestDAO leaveRequestDAO;
    private PayrollDAO payrollDAO;

    public EmployeeCLI(User user) {
        this.currentUser = user;
        this.scanner = new Scanner(System.in);
        this.employeeDAO = new EmployeeDAO();
        this.attendanceDAO = new AttendanceDAO();
        this.leaveRequestDAO = new LeaveRequestDAO();
        this.payrollDAO = new PayrollDAO();
    }

    public void showMenu() {
        while (true) {
            System.out.println("\n=== EMPLOYEE DASHBOARD ===");
            System.out.println("1. View My Profile");
            System.out.println("2. Mark Attendance");
            System.out.println("3. View My Attendance");
            System.out.println("4. Apply for Leave");
            System.out.println("5. View My Leave Requests");
            System.out.println("6. View My Payroll");
            System.out.println("7. Logout");
            System.out.print("Choose an option (1-7): ");

            int choice = getIntInput();
            switch (choice) {
                case 1:
                    viewMyProfile();
                    break;
                case 2:
                    markMyAttendance();
                    break;
                case 3:
                    viewMyAttendance();
                    break;
                case 4:
                    applyForLeave();
                    break;
                case 5:
                    viewMyLeaveRequests();
                    break;
                case 6:
                    viewMyPayroll();
                    break;
                case 7:
                    SessionManager.logout();
                    return;
                default:
                    System.out.println("Invalid option! Please try again.");
            }
        }
    }

    private void viewMyProfile() {
        System.out.println("\n=== MY PROFILE ===");
        
        Employee employee = employeeDAO.getEmployeeByUserId(currentUser.getUserId());
        if (employee == null) {
            System.out.println("Employee profile not found!");
            return;
        }

        System.out.println("Personal Information:");
        System.out.println("Employee ID: " + employee.getEmpId());
        System.out.println("Name: " + employee.getFullName());
        System.out.println("Email: " + employee.getEmail());
        System.out.println("Phone: " + employee.getPhone());
        System.out.println("Address: " + employee.getAddress());
        System.out.println("Gender: " + employee.getGender());
        System.out.println("Date of Birth: " + employee.getDob());
        System.out.println();
        System.out.println("Work Information:");
        System.out.println("Designation: " + employee.getDesignation());
        System.out.println("Department ID: " + employee.getDeptId());
        System.out.println("Hire Date: " + employee.getHireDate());
        System.out.println("Salary: $" + String.format("%.2f", employee.getSalary()));
    }

    private void markMyAttendance() {
        System.out.println("\n=== MARK ATTENDANCE ===");
        
        Employee employee = employeeDAO.getEmployeeByUserId(currentUser.getUserId());
        if (employee == null) {
            System.out.println("Employee profile not found!");
            return;
        }

        LocalDate today = LocalDate.now();
        
        if (attendanceDAO.isAttendanceMarked(employee.getEmpId(), today)) {
            System.out.println("Attendance already marked for today (" + today + ")");
            return;
        }

        System.out.println("Today's Date: " + today);
        System.out.print("Status (Present/Absent/Leave): ");
        String status = scanner.nextLine().trim();

        Attendance attendance = new Attendance(employee.getEmpId(), today, status);
        
        if (attendanceDAO.markAttendance(attendance)) {
            System.out.println("✓ Attendance marked successfully!");
        } else {
            System.out.println("✗ Failed to mark attendance!");
        }
    }

    private void viewMyAttendance() {
        System.out.println("\n=== MY ATTENDANCE ===");
        
        Employee employee = employeeDAO.getEmployeeByUserId(currentUser.getUserId());
        if (employee == null) {
            System.out.println("Employee profile not found!");
            return;
        }

        System.out.println("1. View Recent Attendance");
        System.out.println("2. View Monthly Attendance");
        System.out.print("Choose an option (1-2): ");

        int choice = getIntInput();
        switch (choice) {
            case 1:
                viewRecentAttendance(employee.getEmpId());
                break;
            case 2:
                viewMonthlyAttendance(employee.getEmpId());
                break;
            default:
                System.out.println("Invalid option!");
        }
    }

    private void viewRecentAttendance(int empId) {
        List<Attendance> attendanceList = attendanceDAO.getAttendanceByEmployee(empId);
        
        if (attendanceList.isEmpty()) {
            System.out.println("No attendance records found.");
            return;
        }

        System.out.println("Recent Attendance Records:");
        System.out.printf("%-12s %-15s%n", "Date", "Status");
        System.out.println("=".repeat(30));
        
        // Show last 10 records
        int count = Math.min(10, attendanceList.size());
        for (int i = 0; i < count; i++) {
            Attendance att = attendanceList.get(i);
            System.out.printf("%-12s %-15s%n", att.getAttDate(), att.getStatus());
        }
    }

    private void viewMonthlyAttendance(int empId) {
        System.out.print("Year: ");
        int year = getIntInput();
        
        System.out.print("Month (1-12): ");
        int month = getIntInput();
        
        List<Attendance> attendanceList = attendanceDAO.getMonthlyAttendance(empId, year, month);
        int presentDays = attendanceDAO.getPresentDays(empId, year, month);
        int absentDays = attendanceDAO.getAbsentDays(empId, year, month);
        
        System.out.println("\nMonthly Attendance Summary (" + year + "-" + String.format("%02d", month) + "):");
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

    private void applyForLeave() {
        System.out.println("\n=== APPLY FOR LEAVE ===");
        
        Employee employee = employeeDAO.getEmployeeByUserId(currentUser.getUserId());
        if (employee == null) {
            System.out.println("Employee profile not found!");
            return;
        }

        System.out.print("Start Date (YYYY-MM-DD): ");
        LocalDate startDate = LocalDate.parse(scanner.nextLine().trim());
        
        System.out.print("End Date (YYYY-MM-DD): ");
        LocalDate endDate = LocalDate.parse(scanner.nextLine().trim());
        
        if (endDate.isBefore(startDate)) {
            System.out.println("End date cannot be before start date!");
            return;
        }
        
        System.out.print("Reason: ");
        String reason = scanner.nextLine().trim();

        LeaveRequest leaveRequest = new LeaveRequest(employee.getEmpId(), startDate, endDate, reason);
        
        if (leaveRequestDAO.addLeaveRequest(leaveRequest)) {
            System.out.println("✓ Leave request submitted successfully!");
            System.out.println("Your request will be reviewed by HR.");
        } else {
            System.out.println("✗ Failed to submit leave request!");
        }
    }

    private void viewMyLeaveRequests() {
        System.out.println("\n=== MY LEAVE REQUESTS ===");
        
        Employee employee = employeeDAO.getEmployeeByUserId(currentUser.getUserId());
        if (employee == null) {
            System.out.println("Employee profile not found!");
            return;
        }

        List<LeaveRequest> leaveRequests = leaveRequestDAO.getLeaveRequestsByEmployee(employee.getEmpId());
        
        if (leaveRequests.isEmpty()) {
            System.out.println("No leave requests found.");
            return;
        }

        System.out.printf("%-8s %-12s %-12s %-10s %-20s%n", "Leave ID", "Start Date", "End Date", "Status", "Reason");
        System.out.println("=".repeat(70));
        
        for (LeaveRequest leave : leaveRequests) {
            System.out.printf("%-8d %-12s %-12s %-10s %-20s%n", 
                leave.getLeaveId(), leave.getStartDate(), leave.getEndDate(), leave.getStatus(), leave.getReason());
        }
    }

    private void viewMyPayroll() {
        System.out.println("\n=== MY PAYROLL ===");
        
        Employee employee = employeeDAO.getEmployeeByUserId(currentUser.getUserId());
        if (employee == null) {
            System.out.println("Employee profile not found!");
            return;
        }

        System.out.println("1. View Latest Payslip");
        System.out.println("2. View All Payroll Records");
        System.out.print("Choose an option (1-2): ");

        int choice = getIntInput();
        switch (choice) {
            case 1:
                viewLatestPayslip(employee.getEmpId());
                break;
            case 2:
                viewAllPayrollRecords(employee.getEmpId());
                break;
            default:
                System.out.println("Invalid option!");
        }
    }

    private void viewLatestPayslip(int empId) {
        Payroll latestPayroll = payrollDAO.getLatestPayrollByEmployee(empId);
        
        if (latestPayroll == null) {
            System.out.println("No payroll records found.");
            return;
        }

        System.out.println("\n=== LATEST PAYSLIP ===");
        System.out.println("Payroll ID: " + latestPayroll.getPayrollId());
        System.out.println("Pay Date: " + latestPayroll.getPayDate());
        System.out.println("Basic Salary: $" + String.format("%.2f", latestPayroll.getBasicSalary()));
        System.out.println("Bonus: $" + String.format("%.2f", latestPayroll.getBonus()));
        System.out.println("Deductions: $" + String.format("%.2f", latestPayroll.getDeductions()));
        System.out.println("Net Salary: $" + String.format("%.2f", latestPayroll.getNetSalary()));
        System.out.println("=".repeat(40));
    }

    private void viewAllPayrollRecords(int empId) {
        List<Payroll> payrollList = payrollDAO.getPayrollByEmployee(empId);
        
        if (payrollList.isEmpty()) {
            System.out.println("No payroll records found.");
            return;
        }

        System.out.println("All Payroll Records:");
        System.out.printf("%-10s %-12s %-10s %-10s %-12s %-12s%n", "Payroll ID", "Basic Salary", "Bonus", "Deductions", "Net Salary", "Pay Date");
        System.out.println("=".repeat(80));
        
        for (Payroll payroll : payrollList) {
            System.out.printf("%-10d %-12.2f %-10.2f %-10.2f %-12.2f %-12s%n", 
                payroll.getPayrollId(), payroll.getBasicSalary(), payroll.getBonus(), 
                payroll.getDeductions(), payroll.getNetSalary(), payroll.getPayDate());
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
}
