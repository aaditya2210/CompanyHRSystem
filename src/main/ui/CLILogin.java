package main.ui;

import java.util.Scanner;
import main.dao.UserDAO;
import main.model.User;

public class CLILogin {
    private Scanner scanner = new Scanner(System.in);
    private UserDAO userDAO = new UserDAO();

    public User performLogin() {
        System.out.println("\n=== LOGIN ===");
        System.out.print("Username: ");
        String username = scanner.nextLine().trim();
        
        System.out.print("Password: ");
        String password = scanner.nextLine().trim();

        if (username.isEmpty() || password.isEmpty()) {
            System.out.println("Username and password cannot be empty!");
            return null;
        }

        return userDAO.login(username, password);
    }
}
