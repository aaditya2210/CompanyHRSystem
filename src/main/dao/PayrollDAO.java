package main.dao;

import main.DB.DBConnection;
import main.model.Payroll;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PayrollDAO {
    
    public boolean addPayroll(Payroll payroll) {
        String sql = "INSERT INTO payroll (emp_id, basic_salary, bonus, deductions, net_salary, pay_date) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, payroll.getEmpId());
            ps.setDouble(2, payroll.getBasicSalary());
            ps.setDouble(3, payroll.getBonus());
            ps.setDouble(4, payroll.getDeductions());
            ps.setDouble(5, payroll.getNetSalary());
            ps.setDate(6, Date.valueOf(payroll.getPayDate()));
            
            int result = ps.executeUpdate();
            return result > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public List<Payroll> getPayrollByEmployee(int empId) {
        String sql = "SELECT p.*, e.first_name, e.last_name FROM payroll p JOIN employees e ON p.emp_id = e.emp_id WHERE p.emp_id=? ORDER BY p.pay_date DESC";
        List<Payroll> payrollList = new ArrayList<>();
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, empId);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                Payroll payroll = new Payroll();
                payroll.setPayrollId(rs.getInt("payroll_id"));
                payroll.setEmpId(rs.getInt("emp_id"));
                payroll.setBasicSalary(rs.getDouble("basic_salary"));
                payroll.setBonus(rs.getDouble("bonus"));
                payroll.setDeductions(rs.getDouble("deductions"));
                payroll.setNetSalary(rs.getDouble("net_salary"));
                payroll.setPayDate(rs.getDate("pay_date").toLocalDate());
                payrollList.add(payroll);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return payrollList;
    }
    
    public List<Payroll> getMonthlyPayroll(int year, int month) {
        String sql = "SELECT p.*, e.first_name, e.last_name FROM payroll p JOIN employees e ON p.emp_id = e.emp_id WHERE YEAR(p.pay_date)=? AND MONTH(p.pay_date)=? ORDER BY p.emp_id";
        List<Payroll> payrollList = new ArrayList<>();
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, year);
            ps.setInt(2, month);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                Payroll payroll = new Payroll();
                payroll.setPayrollId(rs.getInt("payroll_id"));
                payroll.setEmpId(rs.getInt("emp_id"));
                payroll.setBasicSalary(rs.getDouble("basic_salary"));
                payroll.setBonus(rs.getDouble("bonus"));
                payroll.setDeductions(rs.getDouble("deductions"));
                payroll.setNetSalary(rs.getDouble("net_salary"));
                payroll.setPayDate(rs.getDate("pay_date").toLocalDate());
                payrollList.add(payroll);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return payrollList;
    }
    
    public List<Payroll> getAllPayroll() {
        String sql = "SELECT p.*, e.first_name, e.last_name FROM payroll p JOIN employees e ON p.emp_id = e.emp_id ORDER BY p.pay_date DESC";
        List<Payroll> payrollList = new ArrayList<>();
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                Payroll payroll = new Payroll();
                payroll.setPayrollId(rs.getInt("payroll_id"));
                payroll.setEmpId(rs.getInt("emp_id"));
                payroll.setBasicSalary(rs.getDouble("basic_salary"));
                payroll.setBonus(rs.getDouble("bonus"));
                payroll.setDeductions(rs.getDouble("deductions"));
                payroll.setNetSalary(rs.getDouble("net_salary"));
                payroll.setPayDate(rs.getDate("pay_date").toLocalDate());
                payrollList.add(payroll);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return payrollList;
    }
    
    public Payroll getLatestPayrollByEmployee(int empId) {
        String sql = "SELECT * FROM payroll WHERE emp_id=? ORDER BY pay_date DESC LIMIT 1";
        Payroll payroll = null;
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, empId);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                payroll = new Payroll();
                payroll.setPayrollId(rs.getInt("payroll_id"));
                payroll.setEmpId(rs.getInt("emp_id"));
                payroll.setBasicSalary(rs.getDouble("basic_salary"));
                payroll.setBonus(rs.getDouble("bonus"));
                payroll.setDeductions(rs.getDouble("deductions"));
                payroll.setNetSalary(rs.getDouble("net_salary"));
                payroll.setPayDate(rs.getDate("pay_date").toLocalDate());
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return payroll;
    }
    
    public boolean generateMonthlyPayroll(int empId, int year, int month, double basicSalary, double bonus, double deductions) {
        Payroll payroll = new Payroll(empId, basicSalary, bonus, deductions);
        payroll.setPayDate(LocalDate.of(year, month, 1));
        return addPayroll(payroll);
    }
}
