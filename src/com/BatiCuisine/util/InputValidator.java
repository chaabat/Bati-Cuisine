package com.BatiCuisine.util;

import java.math.BigDecimal;

import java.util.Scanner;


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


    public static boolean isValidName(String name) {
        return name != null && name.matches("[a-zA-Z\\s]+");
    }

    public static boolean isValidAddress(String address) {
        return address != null && address.matches("\\d+\\s+([a-zA-Z]+|[a-zA-Z]+\\s[a-zA-Z]+)");
    }

    public static boolean isValidPhone(String phone) {
        return phone != null && phone.matches("^\\+?\\d{7,12}$");
    }



}
