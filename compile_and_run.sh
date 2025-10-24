#!/bin/bash
echo "=========================================="
echo "    COMPANY HR MANAGEMENT SYSTEM"
echo "    Compilation Script"
echo "=========================================="
echo

echo "Compiling Java files..."
javac -cp "lib/mysql-connector-j-9.3.0.jar" -d . src/*.java src/main/DB/*.java src/main/dao/*.java src/main/model/*.java src/main/ui/*.java src/main/util/*.java

if [ $? -eq 0 ]; then
    echo
    echo "✓ Compilation successful!"
    echo
    echo "Starting HR Management System..."
    echo
    java -cp ".:lib/mysql-connector-j-9.3.0.jar" HRManagementCLI
else
    echo
    echo "✗ Compilation failed! Please check the error messages above."
    read -p "Press Enter to continue..."
fi
