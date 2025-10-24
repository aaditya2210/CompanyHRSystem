package main.util;

public class SessionManager {
    private static boolean isLoggedIn = true;
    
    public static void logout() {
        isLoggedIn = false;
        System.out.println("\n✓ Logged out successfully!");
    }
    
    public static boolean isLoggedIn() {
        return isLoggedIn;
    }
    
    public static void login() {
        isLoggedIn = true;
    }
}
