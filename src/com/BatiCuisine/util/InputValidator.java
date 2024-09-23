package com.BatiCuisine.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class InputValidator {

    private static final Scanner scanner = new Scanner(System.in);
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static int readPositiveInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                int number = Integer.parseInt(scanner.nextLine());
                if (number > 0) {
                    return number;
                } else {
                    System.out.println("Le nombre doit être supérieur à zéro. Veuillez réessayer.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrée non valide. Veuillez entrer un nombre entier.");
            }
        }
    }

    public static double readPositiveDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                double number = Double.parseDouble(scanner.nextLine());
                if (number > 0) {
                    return number;
                } else {
                    System.out.println("Le nombre doit être supérieur à zéro. Veuillez réessayer.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrée non valide. Veuillez entrer un nombre décimal.");
            }
        }
    }

    public static String readNonEmptyString(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            } else {
                System.out.println("Ce champ ne peut pas être vide. Veuillez réessayer.");
            }
        }
    }

    public static boolean readYesNo(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim().toLowerCase();
            if (input.equals("oui") || input.equals("yes")) {
                return true;
            } else if (input.equals("non") || input.equals("no")) {
                return false;
            } else {
                System.out.println("Réponse non valide. Veuillez entrer 'oui' ou 'non'.");
            }
        }
    }

    public static LocalDate readDate(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String dateString = scanner.nextLine();
                return LocalDate.parse(dateString, DATE_FORMATTER);
            } catch (DateTimeParseException e) {
                System.out.println("Format de date incorrect. Veuillez réessayer.");
            }
        }
    }
}
