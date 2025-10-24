package main.model;

import java.time.LocalDate;

public class LeaveRequest {
    private int leaveId;
    private int empId;
    private LocalDate startDate;
    private LocalDate endDate;
    private String reason;
    private String status; // Pending, Approved, Rejected
    private LocalDate appliedOn;

    // Constructors
    public LeaveRequest() {}

    public LeaveRequest(int empId, LocalDate startDate, LocalDate endDate, String reason) {
        this.empId = empId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.reason = reason;
        this.status = "Pending";
        this.appliedOn = LocalDate.now();
    }

    // Getters and Setters
    public int getLeaveId() { return leaveId; }
    public void setLeaveId(int leaveId) { this.leaveId = leaveId; }

    public int getEmpId() { return empId; }
    public void setEmpId(int empId) { this.empId = empId; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDate getAppliedOn() { return appliedOn; }
    public void setAppliedOn(LocalDate appliedOn) { this.appliedOn = appliedOn; }

    @Override
    public String toString() {
        return String.format("Leave ID: %d, Employee ID: %d, Period: %s to %s, Status: %s", 
                           leaveId, empId, startDate, endDate, status);
    }
}
