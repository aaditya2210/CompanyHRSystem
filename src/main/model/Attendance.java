package main.model;

import java.time.LocalDate;

public class Attendance {
    private int attId;
    private int empId;
    private LocalDate attDate;
    private String status; // Present, Absent, Leave

    // Constructors
    public Attendance() {}

    public Attendance(int empId, LocalDate attDate, String status) {
        this.empId = empId;
        this.attDate = attDate;
        this.status = status;
    }

    // Getters and Setters
    public int getAttId() { return attId; }
    public void setAttId(int attId) { this.attId = attId; }

    public int getEmpId() { return empId; }
    public void setEmpId(int empId) { this.empId = empId; }

    public LocalDate getAttDate() { return attDate; }
    public void setAttDate(LocalDate attDate) { this.attDate = attDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return String.format("Employee ID: %d, Date: %s, Status: %s", empId, attDate, status);
    }
}
