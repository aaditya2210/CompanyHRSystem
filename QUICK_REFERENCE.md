# Quick Reference Guide - Available IDs

## 🔑 **Default Login Credentials**

### Admin Account
- **Username**: `admin`
- **Password**: `admin123`
- **User ID**: `1`

### Employee Accounts
- **Username**: `john.doe` | **Password**: `password123` | **User ID**: `2`
- **Username**: `jane.smith` | **Password**: `password123` | **User ID**: `3`  
- **Username**: `mike.wilson` | **Password**: `password123` | **User ID**: `4`

## 📊 **Sample Data IDs**

### Users Table
- User ID `1` - admin (Admin role)
- User ID `2` - john.doe (Employee role)
- User ID `3` - jane.smith (Employee role)
- User ID `4` - mike.wilson (Employee role)

### Departments Table
- Department ID `1` - Human Resources
- Department ID `2` - Information Technology
- Department ID `3` - Sales
- Department ID `4` - Marketing

### Employees Table
- Employee ID `1` - John Doe (User ID: 2, Dept ID: 1)
- Employee ID `2` - Jane Smith (User ID: 3, Dept ID: 2)
- Employee ID `3` - Mike Wilson (User ID: 4, Dept ID: 3)

## 🛠️ **How to Fix the Foreign Key Error**

When adding a department, use these valid Manager IDs:
- **1** (admin user)
- **2** (john.doe)
- **3** (jane.smith)
- **4** (mike.wilson)

**❌ Don't use**: 301, 999, or any other non-existent IDs

## 💡 **Tips for Using the System**

1. **Always check available IDs** - The system now shows available IDs before asking for input
2. **Use existing User IDs** - When assigning managers to departments
3. **Use existing Department IDs** - When adding employees to departments
4. **Start with sample data** - The system comes with pre-configured sample data

## 🚀 **Quick Start**

1. Run: `compile_and_run.bat`
2. Login as admin: `admin` / `admin123`
3. Try adding a department with Manager ID: `1` or `2`
4. Try adding an employee with Department ID: `1` or `2`

The system now provides better error handling and shows available options to prevent foreign key constraint violations!
