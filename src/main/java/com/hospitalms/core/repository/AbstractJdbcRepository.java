package com.hospitalms.core.repository;

import com.hospitalms.database.DatabaseConnection;

import java.sql.Connection;

public abstract class AbstractJdbcRepository {

    protected Connection getConnection() {
        return DatabaseConnection.getConnection();
    }
}