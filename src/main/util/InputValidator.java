package main.util;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class InputValidator {
    private static Scanner scanner = new Scanner(System.in);
    
    // Email validation pattern
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
        "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$"
    );
    
    // Phone validation pattern (basic)
    private static final Pattern PHONE_PATTERN = Pattern.compile("^[0-9]{10,15}$");
    
    public static int getValidInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                String input = scanner.nextLine().trim();
                if (input.isEmpty()) {
                    System.out.println("Input cannot be empty. Please try again.");
                    continue;
                }
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid integer number.");
            }
        }
    }
    
    public static double getValidDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                String input = scanner.nextLine().trim();
                if (input.isEmpty()) {
                    System.out.println("Input cannot be empty. Please try again.");
                    continue;
                }
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid decimal number.");
            }
        }
    }
    
    public static String getValidString(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("Input cannot be empty. Please try again.");
                continue;
            }
            return input;
        }
    }
    
    public static String getValidEmail(String prompt) {
        while (true) {
            System.out.print(prompt);
            String email = scanner.nextLine().trim();
            if (email.isEmpty()) {
                System.out.println("Email cannot be empty. Please try again.");
                continue;
            }
            if (isValidEmail(email)) {
                return email;
            } else {
                System.out.println("Please enter a valid email address.");
            }
        }
    }
    
    public static String getValidPhone(String prompt) {
        while (true) {
            System.out.print(prompt);
            String phone = scanner.nextLine().trim();
            if (phone.isEmpty()) {
                System.out.println("Phone number cannot be empty. Please try again.");
                continue;
            }
            if (isValidPhone(phone)) {
                return phone;
            } else {
                System.out.println("Please enter a valid phone number (10-15 digits).");
            }
        }
    }
    
    public static LocalDate getValidDate(String prompt) {
        while (true) {
            System.out.print(prompt);
            String dateStr = scanner.nextLine().trim();
            if (dateStr.isEmpty()) {
                System.out.println("Date cannot be empty. Please try again.");
                continue;
            }
            try {
                return LocalDate.parse(dateStr);
            } catch (DateTimeParseException e) {
                System.out.println("Please enter a valid date in YYYY-MM-DD format.");
            }
        }
    }
    
    public static String getValidGender(String prompt) {
        while (true) {
            System.out.print(prompt);
            String gender = scanner.nextLine().trim().toLowerCase();
            if (gender.isEmpty()) {
                System.out.println("Gender cannot be empty. Please try again.");
                continue;
            }
            if (gender.equals("male") || gender.equals("female") || gender.equals("other")) {
                return gender.substring(0, 1).toUpperCase() + gender.substring(1);
            } else {
                System.out.println("Please enter 'Male', 'Female', or 'Other'.");
            }
        }
    }
    
    public static String getValidAttendanceStatus(String prompt) {
        while (true) {
            System.out.print(prompt);
            String status = scanner.nextLine().trim().toLowerCase();
            if (status.isEmpty()) {
                System.out.println("Status cannot be empty. Please try again.");
                continue;
            }
            if (status.equals("present") || status.equals("absent") || status.equals("leave")) {
                return status.substring(0, 1).toUpperCase() + status.substring(1);
            } else {
                System.out.println("Please enter 'Present', 'Absent', or 'Leave'.");
            }
        }
    }
    
    public static String getValidLeaveStatus(String prompt) {
        while (true) {
            System.out.print(prompt);
            String status = scanner.nextLine().trim().toLowerCase();
            if (status.isEmpty()) {
                System.out.println("Status cannot be empty. Please try again.");
                continue;
            }
            if (status.equals("pending") || status.equals("approved") || status.equals("rejected")) {
                return status.substring(0, 1).toUpperCase() + status.substring(1);
            } else {
                System.out.println("Please enter 'Pending', 'Approved', or 'Rejected'.");
            }
        }
    }
    
    public static boolean getConfirmation(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim().toLowerCase();
            if (input.equals("y") || input.equals("yes")) {
                return true;
            } else if (input.equals("n") || input.equals("no")) {
                return false;
            } else {
                System.out.println("Please enter 'y' for yes or 'n' for no.");
            }
        }
    }
    
    public static int getValidChoice(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            try {
                String input = scanner.nextLine().trim();
                if (input.isEmpty()) {
                    System.out.println("Input cannot be empty. Please try again.");
                    continue;
                }
                int choice = Integer.parseInt(input);
                if (choice >= min && choice <= max) {
                    return choice;
                } else {
                    System.out.println("Please enter a number between " + min + " and " + max + ".");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }
    
    public static String getOptionalString(String prompt, String defaultValue) {
        System.out.print(prompt + " [" + defaultValue + "]: ");
        String input = scanner.nextLine().trim();
        return input.isEmpty() ? defaultValue : input;
    }
    
    public static int getOptionalInt(String prompt, int defaultValue) {
        System.out.print(prompt + " [" + defaultValue + "]: ");
        String input = scanner.nextLine().trim();
        if (input.isEmpty()) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Using default value: " + defaultValue);
            return defaultValue;
        }
    }
    
    public static double getOptionalDouble(String prompt, double defaultValue) {
        System.out.print(prompt + " [" + defaultValue + "]: ");
        String input = scanner.nextLine().trim();
        if (input.isEmpty()) {
            return defaultValue;
        }
        try {
            return Double.parseDouble(input);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Using default value: " + defaultValue);
            return defaultValue;
        }
    }
    
    public static LocalDate getOptionalDate(String prompt, LocalDate defaultValue) {
        System.out.print(prompt + " [" + defaultValue + "]: ");
        String input = scanner.nextLine().trim();
        if (input.isEmpty()) {
            return defaultValue;
        }
        try {
            return LocalDate.parse(input);
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Using default value: " + defaultValue);
            return defaultValue;
        }
    }
    
    private static boolean isValidEmail(String email) {
        return EMAIL_PATTERN.matcher(email).matches();
    }
    
    private static boolean isValidPhone(String phone) {
        return PHONE_PATTERN.matcher(phone).matches();
    }
    
    public static void pauseForUser() {
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }
    
    public static void clearScreen() {
        // Clear screen for better user experience
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
    }
    
    public static void displayError(String message) {
        System.out.println("✗ ERROR: " + message);
    }
    
    public static void displaySuccess(String message) {
        System.out.println("✓ SUCCESS: " + message);
    }
    
    public static void displayInfo(String message) {
        System.out.println("ℹ INFO: " + message);
    }
    
    public static void displayWarning(String message) {
        System.out.println("⚠ WARNING: " + message);
    }
}
