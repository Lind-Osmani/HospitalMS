package com.hospitalms.security;

import com.hospitalms.enums.UserRole;

public class SessionManager {

    private static String currentUsername;

    private static UserRole currentUserRole;

    private static boolean loggedIn = false;


    public static void login(String username, UserRole role) {

        currentUsername = username;
        currentUserRole = role;
        loggedIn = true;

    }


    public static void logout() {

        currentUsername = null;
        currentUserRole = null;
        loggedIn = false;

    }


    public static String getCurrentUsername() {
        return currentUsername;
    }


    public static UserRole getCurrentUserRole() {
        return currentUserRole;
    }


    public static boolean isLoggedIn() {
        return loggedIn;
    }

}
