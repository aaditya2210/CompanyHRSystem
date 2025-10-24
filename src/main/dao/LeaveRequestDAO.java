package main.dao;

import main.DB.DBConnection;
import main.model.LeaveRequest;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LeaveRequestDAO {
    
    public boolean addLeaveRequest(LeaveRequest leaveRequest) {
        String sql = "INSERT INTO leaves (emp_id, start_date, end_date, reason, status, applied_on) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, leaveRequest.getEmpId());
            ps.setDate(2, Date.valueOf(leaveRequest.getStartDate()));
            ps.setDate(3, Date.valueOf(leaveRequest.getEndDate()));
            ps.setString(4, leaveRequest.getReason());
            ps.setString(5, leaveRequest.getStatus());
            ps.setDate(6, Date.valueOf(leaveRequest.getAppliedOn()));
            
            int result = ps.executeUpdate();
            return result > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean updateLeaveStatus(int leaveId, String status) {
        String sql = "UPDATE leaves SET status=? WHERE leave_id=?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, status);
            ps.setInt(2, leaveId);
            
            int result = ps.executeUpdate();
            return result > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public List<LeaveRequest> getLeaveRequestsByEmployee(int empId) {
        String sql = "SELECT * FROM leaves WHERE emp_id=? ORDER BY applied_on DESC";
        List<LeaveRequest> leaveRequests = new ArrayList<>();
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, empId);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                LeaveRequest leaveRequest = new LeaveRequest();
                leaveRequest.setLeaveId(rs.getInt("leave_id"));
                leaveRequest.setEmpId(rs.getInt("emp_id"));
                leaveRequest.setStartDate(rs.getDate("start_date").toLocalDate());
                leaveRequest.setEndDate(rs.getDate("end_date").toLocalDate());
                leaveRequest.setReason(rs.getString("reason"));
                leaveRequest.setStatus(rs.getString("status"));
                leaveRequest.setAppliedOn(rs.getDate("applied_on").toLocalDate());
                leaveRequests.add(leaveRequest);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return leaveRequests;
    }
    
    public List<LeaveRequest> getPendingLeaveRequests() {
        String sql = "SELECT l.*, e.first_name, e.last_name FROM leaves l JOIN employees e ON l.emp_id = e.emp_id WHERE l.status='Pending' ORDER BY l.applied_on";
        List<LeaveRequest> leaveRequests = new ArrayList<>();
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                LeaveRequest leaveRequest = new LeaveRequest();
                leaveRequest.setLeaveId(rs.getInt("leave_id"));
                leaveRequest.setEmpId(rs.getInt("emp_id"));
                leaveRequest.setStartDate(rs.getDate("start_date").toLocalDate());
                leaveRequest.setEndDate(rs.getDate("end_date").toLocalDate());
                leaveRequest.setReason(rs.getString("reason"));
                leaveRequest.setStatus(rs.getString("status"));
                leaveRequest.setAppliedOn(rs.getDate("applied_on").toLocalDate());
                leaveRequests.add(leaveRequest);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return leaveRequests;
    }
    
    public List<LeaveRequest> getAllLeaveRequests() {
        String sql = "SELECT l.*, e.first_name, e.last_name FROM leaves l JOIN employees e ON l.emp_id = e.emp_id ORDER BY l.applied_on DESC";
        List<LeaveRequest> leaveRequests = new ArrayList<>();
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                LeaveRequest leaveRequest = new LeaveRequest();
                leaveRequest.setLeaveId(rs.getInt("leave_id"));
                leaveRequest.setEmpId(rs.getInt("emp_id"));
                leaveRequest.setStartDate(rs.getDate("start_date").toLocalDate());
                leaveRequest.setEndDate(rs.getDate("end_date").toLocalDate());
                leaveRequest.setReason(rs.getString("reason"));
                leaveRequest.setStatus(rs.getString("status"));
                leaveRequest.setAppliedOn(rs.getDate("applied_on").toLocalDate());
                leaveRequests.add(leaveRequest);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return leaveRequests;
    }
    
    public LeaveRequest getLeaveRequestById(int leaveId) {
        String sql = "SELECT * FROM leaves WHERE leave_id=?";
        LeaveRequest leaveRequest = null;
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, leaveId);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                leaveRequest = new LeaveRequest();
                leaveRequest.setLeaveId(rs.getInt("leave_id"));
                leaveRequest.setEmpId(rs.getInt("emp_id"));
                leaveRequest.setStartDate(rs.getDate("start_date").toLocalDate());
                leaveRequest.setEndDate(rs.getDate("end_date").toLocalDate());
                leaveRequest.setReason(rs.getString("reason"));
                leaveRequest.setStatus(rs.getString("status"));
                leaveRequest.setAppliedOn(rs.getDate("applied_on").toLocalDate());
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return leaveRequest;
    }
}
