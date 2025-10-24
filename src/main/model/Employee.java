package main.model;

import java.time.LocalDate;

public class Employee {
    private int empId;
    private int userId;
    private String firstName;
    private String lastName;
    private String gender;
    private LocalDate dob;
    private String email;
    private String phone;
    private String address;
    private int deptId;
    private String designation;
    private LocalDate hireDate;
    private double salary;

    // Constructors
    public Employee() {}

    public Employee(int userId, String firstName, String lastName, String gender, 
                   LocalDate dob, String email, String phone, String address, 
                   int deptId, String designation, LocalDate hireDate, double salary) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.dob = dob;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.deptId = deptId;
        this.designation = designation;
        this.hireDate = hireDate;
        this.salary = salary;
    }

    // Getters and Setters
    public int getEmpId() { return empId; }
    public void setEmpId(int empId) { this.empId = empId; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public LocalDate getDob() { return dob; }
    public void setDob(LocalDate dob) { this.dob = dob; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public int getDeptId() { return deptId; }
    public void setDeptId(int deptId) { this.deptId = deptId; }

    public String getDesignation() { return designation; }
    public void setDesignation(String designation) { this.designation = designation; }

    public LocalDate getHireDate() { return hireDate; }
    public void setHireDate(LocalDate hireDate) { this.hireDate = hireDate; }

    public double getSalary() { return salary; }
    public void setSalary(double salary) { this.salary = salary; }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    @Override
    public String toString() {
        return String.format("ID: %d, Name: %s %s, Email: %s, Designation: %s, Salary: %.2f", 
                           empId, firstName, lastName, email, designation, salary);
    }
}
