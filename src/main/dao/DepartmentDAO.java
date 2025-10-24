package main.dao;

import main.DB.DBConnection;
import main.model.Department;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDAO {
    
    public boolean addDepartment(Department department) {
        String sql = "INSERT INTO departments (dept_name, manager_id) VALUES (?, ?)";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, department.getDeptName());
            ps.setInt(2, department.getManagerId());
            
            int result = ps.executeUpdate();
            return result > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean updateDepartment(Department department) {
        String sql = "UPDATE departments SET dept_name=?, manager_id=? WHERE dept_id=?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, department.getDeptName());
            ps.setInt(2, department.getManagerId());
            ps.setInt(3, department.getDeptId());
            
            int result = ps.executeUpdate();
            return result > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean deleteDepartment(int deptId) {
        String sql = "DELETE FROM departments WHERE dept_id=?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, deptId);
            int result = ps.executeUpdate();
            return result > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public Department getDepartmentById(int deptId) {
        String sql = "SELECT * FROM departments WHERE dept_id=?";
        Department department = null;
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, deptId);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                department = new Department();
                department.setDeptId(rs.getInt("dept_id"));
                department.setDeptName(rs.getString("dept_name"));
                department.setManagerId(rs.getInt("manager_id"));
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return department;
    }
    
    public List<Department> getAllDepartments() {
        String sql = "SELECT d.*, COUNT(e.emp_id) as employee_count FROM departments d LEFT JOIN employees e ON d.dept_id = e.dept_id GROUP BY d.dept_id ORDER BY d.dept_id";
        List<Department> departments = new ArrayList<>();
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                Department department = new Department();
                department.setDeptId(rs.getInt("dept_id"));
                department.setDeptName(rs.getString("dept_name"));
                department.setManagerId(rs.getInt("manager_id"));
                departments.add(department);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return departments;
    }
    
    public int getEmployeeCountByDepartment(int deptId) {
        String sql = "SELECT COUNT(*) as count FROM employees WHERE dept_id=?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, deptId);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return rs.getInt("count");
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return 0;
    }
}
