package com.hospitalms.util;

public final class ValidationUtil {

    private ValidationUtil() {
    }

    public static boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    public static boolean isValidEmail(String email) {
        if (isBlank(email)) {
            return true;
        }

        return email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    }

    public static boolean isValidPhone(String phone) {
        if (isBlank(phone)) {
            return false;
        }

        return phone.matches("^[0-9+\\-\\s]{6,20}$");
    }

    public static boolean hasMinimumLength(String value, int length) {
        return value != null && value.trim().length() >= length;
    }
}