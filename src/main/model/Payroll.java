package main.model;

import java.time.LocalDate;

public class Payroll {
    private int payrollId;
    private int empId;
    private double basicSalary;
    private double bonus;
    private double deductions;
    private double netSalary;
    private LocalDate payDate;

    // Constructors
    public Payroll() {}

    public Payroll(int empId, double basicSalary, double bonus, double deductions) {
        this.empId = empId;
        this.basicSalary = basicSalary;
        this.bonus = bonus;
        this.deductions = deductions;
        this.netSalary = basicSalary + bonus - deductions;
        this.payDate = LocalDate.now();
    }

    // Getters and Setters
    public int getPayrollId() { return payrollId; }
    public void setPayrollId(int payrollId) { this.payrollId = payrollId; }

    public int getEmpId() { return empId; }
    public void setEmpId(int empId) { this.empId = empId; }

    public double getBasicSalary() { return basicSalary; }
    public void setBasicSalary(double basicSalary) { 
        this.basicSalary = basicSalary;
        calculateNetSalary();
    }

    public double getBonus() { return bonus; }
    public void setBonus(double bonus) { 
        this.bonus = bonus;
        calculateNetSalary();
    }

    public double getDeductions() { return deductions; }
    public void setDeductions(double deductions) { 
        this.deductions = deductions;
        calculateNetSalary();
    }

    public double getNetSalary() { return netSalary; }
    public void setNetSalary(double netSalary) { this.netSalary = netSalary; }

    public LocalDate getPayDate() { return payDate; }
    public void setPayDate(LocalDate payDate) { this.payDate = payDate; }

    private void calculateNetSalary() {
        this.netSalary = this.basicSalary + this.bonus - this.deductions;
    }

    @Override
    public String toString() {
        return String.format("Payroll ID: %d, Employee ID: %d, Basic: %.2f, Bonus: %.2f, Deductions: %.2f, Net: %.2f", 
                           payrollId, empId, basicSalary, bonus, deductions, netSalary);
    }
}
