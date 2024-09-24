package com.BatiCuisine.util;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.UUID;

public class InputValidator {
    public static final Scanner scanner = new Scanner(System.in);



    public static int readIntInRange(int min, int max, String errorMessage) {
        while (true) {
            try {
                int input = Integer.parseInt(scanner.nextLine());
                if (input >= min && input <= max) {
                    return input;
                }
                System.out.println(errorMessage);
            } catch (NumberFormatException e) {
                System.out.println(errorMessage);
            }
        }
    }

    public static BigDecimal readPositiveBigDecimal(String errorMessage) {
        while (true) {
            try {
                BigDecimal input = new BigDecimal(scanner.nextLine());
                if (input.compareTo(BigDecimal.ZERO) > 0) {
                    return input;
                }
                System.out.println(errorMessage);
            } catch (NumberFormatException e) {
                System.out.println(errorMessage);
            }
        }
    }

    public static boolean readYesOrNo(String s) {
        return false;
    }


    public LocalDate readDate(String prompt, String errorMessage) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        while (true) {
            System.out.print(prompt);
            try {
                return LocalDate.parse(scanner.nextLine(), formatter);
            } catch (DateTimeParseException e) {
                System.out.println(errorMessage);
            }
        }
    }

    public boolean readBoolean(String prompt) {
        while (true) {
            System.out.print(prompt + " (y/n): ");
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("y")) return true;
            if (input.equalsIgnoreCase("n")) return false;
            System.out.println("Invalid input. Please answer 'y' or 'n'.");
        }
    }

    public String readPhoneNumber(String prompt) {
        return readStringWithPattern(prompt, "Invalid phone number. Please enter a valid number.", "\\d{10}");
    }

    public void close() {
        scanner.close();
    }

    private String readStringWithPattern(String prompt, String errorMessage, String pattern) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            if (input.matches(pattern)) return input;
            System.out.println(errorMessage);
        }
    }

    public static boolean isValidName(String name) {
        return name != null && name.matches("[a-zA-Z\\s]+");
    }

    public static boolean isValidAddress(String address) {
        return address != null && address.matches("\\d+\\s+([a-zA-Z]+|[a-zA-Z]+\\s[a-zA-Z]+)");
    }

    public static boolean isValidPhone(String phone) {
        return phone != null && phone.matches("^\\+?\\d{7,12}$");
    }

    public static boolean isPositiveNumber(String number) {
        try {
            return Double.parseDouble(number) > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean isValidEmail(String email) {
        return email != null && email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$");
    }

    public boolean isUUID(String uuid) {
        try {
            UUID.fromString(uuid);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public static int readPositiveInt(String s) {
        return 0;
    }

    public boolean readYesNo(String s) {
        return false;
    }

}
