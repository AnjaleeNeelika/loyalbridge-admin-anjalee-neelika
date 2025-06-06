package com.backend.backend.auth.helper;

public class PasswordCheck {
    public static boolean isPasswordStrong(String password) {
        return password.length() >= 12 && password.matches(".*[A-Z].*") && password.matches(".*[a-zA-Z0-9].*");
    }
}
