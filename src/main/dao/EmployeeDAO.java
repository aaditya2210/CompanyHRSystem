package main.dao;

import main.DB.DBConnection;
import main.model.Employee;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO {
    
    public boolean addEmployee(Employee employee) {
        String sql = "INSERT INTO employees (user_id, first_name, last_name, gender, dob, email, phone, address, dept_id, designation, hire_date, salary) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, employee.getUserId());
            ps.setString(2, employee.getFirstName());
            ps.setString(3, employee.getLastName());
            ps.setString(4, employee.getGender());
            ps.setDate(5, Date.valueOf(employee.getDob()));
            ps.setString(6, employee.getEmail());
            ps.setString(7, employee.getPhone());
            ps.setString(8, employee.getAddress());
            ps.setInt(9, employee.getDeptId());
            ps.setString(10, employee.getDesignation());
            ps.setDate(11, Date.valueOf(employee.getHireDate()));
            ps.setDouble(12, employee.getSalary());
            
            int result = ps.executeUpdate();
            return result > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean updateEmployee(Employee employee) {
        String sql = "UPDATE employees SET first_name=?, last_name=?, gender=?, dob=?, email=?, phone=?, address=?, dept_id=?, designation=?, hire_date=?, salary=? WHERE emp_id=?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, employee.getFirstName());
            ps.setString(2, employee.getLastName());
            ps.setString(3, employee.getGender());
            ps.setDate(4, Date.valueOf(employee.getDob()));
            ps.setString(5, employee.getEmail());
            ps.setString(6, employee.getPhone());
            ps.setString(7, employee.getAddress());
            ps.setInt(8, employee.getDeptId());
            ps.setString(9, employee.getDesignation());
            ps.setDate(10, Date.valueOf(employee.getHireDate()));
            ps.setDouble(11, employee.getSalary());
            ps.setInt(12, employee.getEmpId());
            
            int result = ps.executeUpdate();
            return result > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean deleteEmployee(int empId) {
        String sql = "DELETE FROM employees WHERE emp_id=?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, empId);
            int result = ps.executeUpdate();
            return result > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public Employee getEmployeeById(int empId) {
        String sql = "SELECT * FROM employees WHERE emp_id=?";
        Employee employee = null;
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, empId);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                employee = new Employee();
                employee.setEmpId(rs.getInt("emp_id"));
                employee.setUserId(rs.getInt("user_id"));
                employee.setFirstName(rs.getString("first_name"));
                employee.setLastName(rs.getString("last_name"));
                employee.setGender(rs.getString("gender"));
                employee.setDob(rs.getDate("dob").toLocalDate());
                employee.setEmail(rs.getString("email"));
                employee.setPhone(rs.getString("phone"));
                employee.setAddress(rs.getString("address"));
                employee.setDeptId(rs.getInt("dept_id"));
                employee.setDesignation(rs.getString("designation"));
                employee.setHireDate(rs.getDate("hire_date").toLocalDate());
                employee.setSalary(rs.getDouble("salary"));
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return employee;
    }
    
    public List<Employee> getAllEmployees() {
        String sql = "SELECT * FROM employees ORDER BY emp_id";
        List<Employee> employees = new ArrayList<>();
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                Employee employee = new Employee();
                employee.setEmpId(rs.getInt("emp_id"));
                employee.setUserId(rs.getInt("user_id"));
                employee.setFirstName(rs.getString("first_name"));
                employee.setLastName(rs.getString("last_name"));
                employee.setGender(rs.getString("gender"));
                employee.setDob(rs.getDate("dob").toLocalDate());
                employee.setEmail(rs.getString("email"));
                employee.setPhone(rs.getString("phone"));
                employee.setAddress(rs.getString("address"));
                employee.setDeptId(rs.getInt("dept_id"));
                employee.setDesignation(rs.getString("designation"));
                employee.setHireDate(rs.getDate("hire_date").toLocalDate());
                employee.setSalary(rs.getDouble("salary"));
                employees.add(employee);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return employees;
    }
    
    public List<Employee> getEmployeesByDepartment(int deptId) {
        String sql = "SELECT * FROM employees WHERE dept_id=? ORDER BY emp_id";
        List<Employee> employees = new ArrayList<>();
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, deptId);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                Employee employee = new Employee();
                employee.setEmpId(rs.getInt("emp_id"));
                employee.setUserId(rs.getInt("user_id"));
                employee.setFirstName(rs.getString("first_name"));
                employee.setLastName(rs.getString("last_name"));
                employee.setGender(rs.getString("gender"));
                employee.setDob(rs.getDate("dob").toLocalDate());
                employee.setEmail(rs.getString("email"));
                employee.setPhone(rs.getString("phone"));
                employee.setAddress(rs.getString("address"));
                employee.setDeptId(rs.getInt("dept_id"));
                employee.setDesignation(rs.getString("designation"));
                employee.setHireDate(rs.getDate("hire_date").toLocalDate());
                employee.setSalary(rs.getDouble("salary"));
                employees.add(employee);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return employees;
    }
    
    public List<Employee> searchEmployeesByName(String name) {
        String sql = "SELECT * FROM employees WHERE first_name LIKE ? OR last_name LIKE ? ORDER BY emp_id";
        List<Employee> employees = new ArrayList<>();
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            String searchPattern = "%" + name + "%";
            ps.setString(1, searchPattern);
            ps.setString(2, searchPattern);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                Employee employee = new Employee();
                employee.setEmpId(rs.getInt("emp_id"));
                employee.setUserId(rs.getInt("user_id"));
                employee.setFirstName(rs.getString("first_name"));
                employee.setLastName(rs.getString("last_name"));
                employee.setGender(rs.getString("gender"));
                employee.setDob(rs.getDate("dob").toLocalDate());
                employee.setEmail(rs.getString("email"));
                employee.setPhone(rs.getString("phone"));
                employee.setAddress(rs.getString("address"));
                employee.setDeptId(rs.getInt("dept_id"));
                employee.setDesignation(rs.getString("designation"));
                employee.setHireDate(rs.getDate("hire_date").toLocalDate());
                employee.setSalary(rs.getDouble("salary"));
                employees.add(employee);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return employees;
    }
    
    public Employee getEmployeeByUserId(int userId) {
        String sql = "SELECT * FROM employees WHERE user_id=?";
        Employee employee = null;
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                employee = new Employee();
                employee.setEmpId(rs.getInt("emp_id"));
                employee.setUserId(rs.getInt("user_id"));
                employee.setFirstName(rs.getString("first_name"));
                employee.setLastName(rs.getString("last_name"));
                employee.setGender(rs.getString("gender"));
                employee.setDob(rs.getDate("dob").toLocalDate());
                employee.setEmail(rs.getString("email"));
                employee.setPhone(rs.getString("phone"));
                employee.setAddress(rs.getString("address"));
                employee.setDeptId(rs.getInt("dept_id"));
                employee.setDesignation(rs.getString("designation"));
                employee.setHireDate(rs.getDate("hire_date").toLocalDate());
                employee.setSalary(rs.getDouble("salary"));
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return employee;
    }
}