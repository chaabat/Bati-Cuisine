package com.BatiCuisine.util;

import java.util.UUID;

public class InputValidator {

    // Handle names (only alphabetic characters and spaces)
    public boolean handleString(String name) {
        return name != null && name.matches("[a-zA-Z\\s]+");
    }

    // Handle addresses (numbers followed by one or more words)
    public boolean handleAddress(String address) {
        return address != null && address.matches("\\d+\\s+([a-zA-Z]+|[a-zA-Z]+\\s[a-zA-Z]+)");
    }

    // Handle phone numbers (optional country code followed by 7-12 digits)
    public boolean handlePhone(String phone) {
        return phone != null && phone.matches("^\\+?\\d{7,12}$");
    }

    // Handle doubles (must be greater than 0)
    public boolean handleDouble(String number) {
        try {
            double value = Double.parseDouble(number);
            return value > 0;
        } catch (NumberFormatException e) {
            return false;  // Invalid double format
        }
    }

    // Handle integers (must be greater than 0)
    public boolean handleInt(String number) {
        try {
            int value = Integer.parseInt(number);
            return value > 0;
        } catch (NumberFormatException e) {
            return false;  // Invalid integer format
        }
    }

    // Validate email format
    public boolean handleEmail(String email) {
        return email != null && email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$");
    }

    // Check if the string is a valid UUID
    public boolean isUUID(String uuid) {
        try {
            UUID.fromString(uuid);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
