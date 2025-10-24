import java.util.Scanner;
import main.ui.CLILogin;
import main.model.User;
import main.util.DatabaseInitializer;
import main.util.SessionManager;

public class HRManagementCLI {
    private static Scanner scanner = new Scanner(System.in);
    private static User currentUser = null;

    public static void main(String[] args) {
        System.out.println("==========================================");
        System.out.println("    COMPANY HR MANAGEMENT SYSTEM");
        System.out.println("==========================================");
        System.out.println();

        // Initialize database
        DatabaseInitializer.initializeDatabase();

        // Main application loop
        while (true) {
            if (currentUser == null || !SessionManager.isLoggedIn()) {
                // Show login menu
                showLoginMenu();
            } else {
                // Show role-based dashboard
                showDashboard();
            }
        }
    }

    private static void showLoginMenu() {
        System.out.println("\n=== LOGIN MENU ===");
        System.out.println("1. Login");
        System.out.println("2. Exit");
        System.out.print("Choose an option (1-2): ");

        int choice = getIntInput();
        switch (choice) {
            case 1:
                CLILogin login = new CLILogin();
                currentUser = login.performLogin();
                if (currentUser != null) {
                    System.out.println("\n✓ Login successful! Welcome, " + currentUser.getUsername() + "!");
                    SessionManager.login();
                } else {
                    System.out.println("\n✗ Login failed! Invalid credentials.");
                }
                break;
            case 2:
                System.out.println("\nThank you for using HR Management System. Goodbye!");
                System.exit(0);
                break;
            default:
                System.out.println("\nInvalid option! Please try again.");
        }
    }

    private static void showDashboard() {
        System.out.println("\n==========================================");
        System.out.println("    WELCOME, " + currentUser.getUsername().toUpperCase());
        System.out.println("    Role: " + currentUser.getRole());
        System.out.println("==========================================");

        if (currentUser.getRole().equals("Admin")) {
            showAdminDashboard();
        } else {
            showEmployeeDashboard();
        }
    }

    private static void showAdminDashboard() {
        main.ui.AdminCLI adminCLI = new main.ui.AdminCLI(currentUser);
        adminCLI.showMenu();
    }

    private static void showEmployeeDashboard() {
        main.ui.EmployeeCLI employeeCLI = new main.ui.EmployeeCLI(currentUser);
        employeeCLI.showMenu();
    }

    public static int getIntInput() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("Please enter a valid number: ");
            }
        }
    }

    public static String getStringInput() {
        return scanner.nextLine().trim();
    }

    public static void logout() {
        currentUser = null;
        SessionManager.logout();
    }

    public static User getCurrentUser() {
        return currentUser;
    }
}