package com.hospitalms.database;

import com.hospitalms.config.DatabaseConfig;
import com.hospitalms.exception.DatabaseException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DatabaseConnection {

    private DatabaseConnection() {
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(
                    DatabaseConfig.URL,
                    DatabaseConfig.USERNAME,
                    DatabaseConfig.PASSWORD
            );
        } catch (SQLException e) {
            throw new DatabaseException("Could not connect to the database.", e);
        }
    }
}