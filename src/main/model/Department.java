package main.model;

import java.time.LocalDate;

public class Department {
    private int deptId;
    private String deptName;
    private int managerId;
    private LocalDate createdAt;

    // Constructors
    public Department() {}

    public Department(int deptId, String deptName, int managerId) {
        this.deptId = deptId;
        this.deptName = deptName;
        this.managerId = managerId;
    }

    // Getters and Setters
    public int getDeptId() { return deptId; }
    public void setDeptId(int deptId) { this.deptId = deptId; }

    public String getDeptName() { return deptName; }
    public void setDeptName(String deptName) { this.deptName = deptName; }

    public int getManagerId() { return managerId; }
    public void setManagerId(int managerId) { this.managerId = managerId; }

    public LocalDate getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDate createdAt) { this.createdAt = createdAt; }

    @Override
    public String toString() {
        return String.format("ID: %d, Name: %s, Manager ID: %d", deptId, deptName, managerId);
    }
}
