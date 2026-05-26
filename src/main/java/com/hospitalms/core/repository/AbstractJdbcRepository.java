package com.hospitalms.core.repository;

import com.hospitalms.database.DatabaseConnection;
import com.hospitalms.exception.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public abstract class AbstractJdbcRepository {

    protected Connection getConnection() {
        return DatabaseConnection.getConnection();
    }

    protected boolean existsByColumn(String tableName, String columnName, String value) {
        if (value == null || value.trim().isEmpty()) {
            return false;
        }

        String sql = "SELECT COUNT(*) FROM " + tableName + " WHERE " + columnName + " = ?";

        try (
                Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setString(1, value);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }

            return false;

        } catch (Exception e) {
            throw new DatabaseException("Could not check if value exists in database.", e);
        }
    }

    protected boolean existsByColumnAndIdNot(String tableName, String columnName, String value, Long id) {
        if (value == null || value.trim().isEmpty()) {
            return false;
        }

        String sql = "SELECT COUNT(*) FROM " + tableName + " WHERE " + columnName + " = ? AND id <> ?";

        try (
                Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setString(1, value);
            statement.setLong(2, id);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }

            return false;

        } catch (Exception e) {
            throw new DatabaseException("Could not check if value exists in database.", e);
        }
    }
}