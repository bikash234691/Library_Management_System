package service;

import java.util.regex.Pattern;

public class InputValidator {

    // ISBN: 10 or 13 digits (basic check)
    public static boolean isValidISBN(String isbn) {
        return isbn != null && (isbn.matches("\\d{10}") || isbn.matches("\\d{13}"));
    }

    // Email basic format
    public static boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return email != null && Pattern.matches(emailRegex, email);
    }

    // Positive integer
    public static boolean isPositiveInteger(String str) {
        try {
            int num = Integer.parseInt(str);
            return num > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}