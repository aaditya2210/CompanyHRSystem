package main.dao;

import main.DB.DBConnection;
import main.model.Attendance;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AttendanceDAO {
    
    public boolean markAttendance(Attendance attendance) {
        String sql = "INSERT INTO attendance (emp_id, att_date, status) VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE status=?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, attendance.getEmpId());
            ps.setDate(2, Date.valueOf(attendance.getAttDate()));
            ps.setString(3, attendance.getStatus());
            ps.setString(4, attendance.getStatus());
            
            int result = ps.executeUpdate();
            return result > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public List<Attendance> getAttendanceByEmployee(int empId) {
        String sql = "SELECT * FROM attendance WHERE emp_id=? ORDER BY att_date DESC";
        List<Attendance> attendanceList = new ArrayList<>();
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, empId);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                Attendance attendance = new Attendance();
                attendance.setAttId(rs.getInt("att_id"));
                attendance.setEmpId(rs.getInt("emp_id"));
                attendance.setAttDate(rs.getDate("att_date").toLocalDate());
                attendance.setStatus(rs.getString("status"));
                attendanceList.add(attendance);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return attendanceList;
    }
    
    public List<Attendance> getAttendanceByDate(LocalDate date) {
        String sql = "SELECT * FROM attendance WHERE att_date=? ORDER BY emp_id";
        List<Attendance> attendanceList = new ArrayList<>();
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setDate(1, Date.valueOf(date));
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                Attendance attendance = new Attendance();
                attendance.setAttId(rs.getInt("att_id"));
                attendance.setEmpId(rs.getInt("emp_id"));
                attendance.setAttDate(rs.getDate("att_date").toLocalDate());
                attendance.setStatus(rs.getString("status"));
                attendanceList.add(attendance);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return attendanceList;
    }
    
    public List<Attendance> getMonthlyAttendance(int empId, int year, int month) {
        String sql = "SELECT * FROM attendance WHERE emp_id=? AND YEAR(att_date)=? AND MONTH(att_date)=? ORDER BY att_date";
        List<Attendance> attendanceList = new ArrayList<>();
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, empId);
            ps.setInt(2, year);
            ps.setInt(3, month);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                Attendance attendance = new Attendance();
                attendance.setAttId(rs.getInt("att_id"));
                attendance.setEmpId(rs.getInt("emp_id"));
                attendance.setAttDate(rs.getDate("att_date").toLocalDate());
                attendance.setStatus(rs.getString("status"));
                attendanceList.add(attendance);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return attendanceList;
    }
    
    public int getPresentDays(int empId, int year, int month) {
        String sql = "SELECT COUNT(*) as count FROM attendance WHERE emp_id=? AND YEAR(att_date)=? AND MONTH(att_date)=? AND status='Present'";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, empId);
            ps.setInt(2, year);
            ps.setInt(3, month);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return rs.getInt("count");
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return 0;
    }
    
    public int getAbsentDays(int empId, int year, int month) {
        String sql = "SELECT COUNT(*) as count FROM attendance WHERE emp_id=? AND YEAR(att_date)=? AND MONTH(att_date)=? AND status='Absent'";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, empId);
            ps.setInt(2, year);
            ps.setInt(3, month);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return rs.getInt("count");
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return 0;
    }
    
    public boolean isAttendanceMarked(int empId, LocalDate date) {
        String sql = "SELECT COUNT(*) as count FROM attendance WHERE emp_id=? AND att_date=?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, empId);
            ps.setDate(2, Date.valueOf(date));
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return rs.getInt("count") > 0;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return false;
    }
}
