package main.DB;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    // private static final String URL = "jdbc:mysql://localhost:3306/company_hr_system?useSSL=false&serverTimezone=UTC";
    private static final String URL = "jdbc:mysql://localhost:3306/company_hr_system?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root"; // your DB username
    private static final String PASSWORD = ""; // your DB password

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Load MySQL driver
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("MySQL Driver not found!");
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Database connection failed!");
            return null;
        }
    }
}

