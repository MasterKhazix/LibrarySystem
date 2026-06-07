package com.qingshan.librarysystem.util;

import java.util.regex.Pattern;

public final class InputValidator {
    private static final Pattern PHONE_PATTERN = Pattern.compile("\\d{11}");
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    private InputValidator() {
    }

    public static String trimToNull(String value) {
        if (value == null) {
            return null;
        }
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }

    public static boolean isValidPhone(String phone) {
        return phone == null || PHONE_PATTERN.matcher(phone).matches();
    }

    public static boolean isValidEmail(String email) {
        return email == null || EMAIL_PATTERN.matcher(email).matches();
    }
}
