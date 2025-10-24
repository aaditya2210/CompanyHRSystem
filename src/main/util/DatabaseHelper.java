package main.util;

import main.DB.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper {
    
    public static List<Integer> getAvailableUserIds() {
        List<Integer> userIds = new ArrayList<>();
        String sql = "SELECT user_id FROM users ORDER BY user_id";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                userIds.add(rs.getInt("user_id"));
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return userIds;
    }
    
    public static List<Integer> getAvailableEmployeeIds() {
        List<Integer> empIds = new ArrayList<>();
        String sql = "SELECT emp_id FROM employees ORDER BY emp_id";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                empIds.add(rs.getInt("emp_id"));
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return empIds;
    }
    
    public static List<Integer> getAvailableDepartmentIds() {
        List<Integer> deptIds = new ArrayList<>();
        String sql = "SELECT dept_id FROM departments ORDER BY dept_id";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                deptIds.add(rs.getInt("dept_id"));
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return deptIds;
    }
    
    public static boolean isValidUserId(int userId) {
        String sql = "SELECT COUNT(*) FROM users WHERE user_id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return false;
    }
    
    public static boolean isValidEmployeeId(int empId) {
        String sql = "SELECT COUNT(*) FROM employees WHERE emp_id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, empId);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return false;
    }
    
    public static boolean isValidDepartmentId(int deptId) {
        String sql = "SELECT COUNT(*) FROM departments WHERE dept_id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, deptId);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return false;
    }
    
    public static void displayAvailableUserIds() {
        List<Integer> userIds = getAvailableUserIds();
        System.out.println("Available User IDs:");
        for (Integer id : userIds) {
            System.out.println("- " + id);
        }
    }
    
    public static void displayAvailableEmployeeIds() {
        List<Integer> empIds = getAvailableEmployeeIds();
        System.out.println("Available Employee IDs:");
        for (Integer id : empIds) {
            System.out.println("- " + id);
        }
    }
    
    public static void displayAvailableDepartmentIds() {
        List<Integer> deptIds = getAvailableDepartmentIds();
        System.out.println("Available Department IDs:");
        for (Integer id : deptIds) {
            System.out.println("- " + id);
        }
    }
}
