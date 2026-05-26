package com.hospitalms.repository.impl;

import com.hospitalms.core.repository.AbstractJdbcRepository;
import com.hospitalms.exception.DatabaseException;
import com.hospitalms.model.Patient;
import com.hospitalms.repository.PatientRepository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcPatientRepository extends AbstractJdbcRepository implements PatientRepository {

    @Override
    public Patient save(Patient patient) {
        String sql = """
                INSERT INTO patients (
                    first_name,
                    last_name,
                    gender,
                    date_of_birth,
                    phone,
                    email,
                    blood_group,
                    address
                )
                VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (
                var connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            fillPatientStatement(statement, patient);

            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();

            if (generatedKeys.next()) {
                Long id = generatedKeys.getLong(1);

                return new Patient(
                        id,
                        patient.getFirstName(),
                        patient.getLastName(),
                        patient.getGender(),
                        patient.getDateOfBirth(),
                        patient.getPhone(),
                        patient.getEmail(),
                        patient.getBloodGroup(),
                        patient.getAddress()
                );
            }

            throw new DatabaseException("Creating patient failed. No ID was generated.");

        } catch (Exception e) {
            throw new DatabaseException("Could not save patient.", e);
        }
    }

    @Override
    public Patient update(Patient patient) {
        String sql = """
                UPDATE patients
                SET first_name = ?,
                    last_name = ?,
                    gender = ?,
                    date_of_birth = ?,
                    phone = ?,
                    email = ?,
                    blood_group = ?,
                    address = ?
                WHERE id = ?
                """;

        try (
                var connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            fillPatientStatement(statement, patient);
            statement.setLong(9, patient.getId());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new DatabaseException("Patient not found with ID: " + patient.getId());
            }

            return patient;

        } catch (Exception e) {
            throw new DatabaseException("Could not update patient.", e);
        }
    }

    @Override
    public Optional<Patient> findById(Long id) {
        String sql = "SELECT * FROM patients WHERE id = ?";

        try (
                var connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setLong(1, id);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(mapResultSetToPatient(resultSet));
            }

            return Optional.empty();

        } catch (Exception e) {
            throw new DatabaseException("Could not find patient by ID.", e);
        }
    }

    @Override
    public List<Patient> findAll() {
        String sql = "SELECT * FROM patients ORDER BY id DESC";

        try (
                var connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()
        ) {
            List<Patient> patients = new ArrayList<>();

            while (resultSet.next()) {
                patients.add(mapResultSetToPatient(resultSet));
            }

            return patients;

        } catch (Exception e) {
            throw new DatabaseException("Could not load patients.", e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        String sql = "DELETE FROM patients WHERE id = ?";

        try (
                var connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setLong(1, id);

            int affectedRows = statement.executeUpdate();

            return affectedRows > 0;

        } catch (Exception e) {
            throw new DatabaseException("Could not delete patient.", e);
        }
    }

    @Override
    public List<Patient> searchByName(String keyword) {
        String sql = """
                SELECT *
                FROM patients
                WHERE LOWER(first_name) LIKE ?
                   OR LOWER(last_name) LIKE ?
                ORDER BY id DESC
                """;

        try (
                var connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            String searchValue = "%" + keyword.toLowerCase() + "%";

            statement.setString(1, searchValue);
            statement.setString(2, searchValue);

            ResultSet resultSet = statement.executeQuery();

            List<Patient> patients = new ArrayList<>();

            while (resultSet.next()) {
                patients.add(mapResultSetToPatient(resultSet));
            }

            return patients;

        } catch (Exception e) {
            throw new DatabaseException("Could not search patients.", e);
        }
    }

    @Override
    public boolean existsByPhone(String phone) {
        String sql = "SELECT COUNT(*) FROM patients WHERE phone = ?";

        try (
                var connection = getConnection();
                var statement = connection.prepareStatement(sql)
        ) {
            statement.setString(1, phone);

            var resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }

            return false;

        } catch (Exception e) {
            throw new DatabaseException("Could not check phone uniqueness.", e);
        }
    }

    @Override
    public boolean existsByEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }

        String sql = "SELECT COUNT(*) FROM patients WHERE email = ?";

        try (
                var connection = getConnection();
                var statement = connection.prepareStatement(sql)
        ) {
            statement.setString(1, email);

            var resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }

            return false;

        } catch (Exception e) {
            throw new DatabaseException("Could not check email uniqueness.", e);
        }
    }

    @Override
    public boolean existsByPhoneAndIdNot(String phone, Long id) {
        String sql = "SELECT COUNT(*) FROM patients WHERE phone = ? AND id <> ?";

        try (
                var connection = getConnection();
                var statement = connection.prepareStatement(sql)
        ) {
            statement.setString(1, phone);
            statement.setLong(2, id);

            var resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }

            return false;

        } catch (Exception e) {
            throw new DatabaseException("Could not check phone uniqueness for update.", e);
        }
    }

    @Override
    public boolean existsByEmailAndIdNot(String email, Long id) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }

        String sql = "SELECT COUNT(*) FROM patients WHERE email = ? AND id <> ?";

        try (
                var connection = getConnection();
                var statement = connection.prepareStatement(sql)
        ) {
            statement.setString(1, email);
            statement.setLong(2, id);

            var resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }

            return false;

        } catch (Exception e) {
            throw new DatabaseException("Could not check email uniqueness for update.", e);
        }
    }

    private void fillPatientStatement(PreparedStatement statement, Patient patient) throws Exception {
        statement.setString(1, patient.getFirstName());
        statement.setString(2, patient.getLastName());
        statement.setString(3, patient.getGender());

        if (patient.getDateOfBirth() != null) {
            statement.setDate(4, Date.valueOf(patient.getDateOfBirth()));
        } else {
            statement.setDate(4, null);
        }

        statement.setString(5, patient.getPhone());
        statement.setString(6, patient.getEmail());
        statement.setString(7, patient.getBloodGroup());
        statement.setString(8, patient.getAddress());
    }

    private Patient mapResultSetToPatient(ResultSet resultSet) throws Exception {
        Date dateOfBirth = resultSet.getDate("date_of_birth");

        return new Patient(
                resultSet.getLong("id"),
                resultSet.getString("first_name"),
                resultSet.getString("last_name"),
                resultSet.getString("gender"),
                dateOfBirth != null ? dateOfBirth.toLocalDate() : null,
                resultSet.getString("phone"),
                resultSet.getString("email"),
                resultSet.getString("blood_group"),
                resultSet.getString("address")
        );
    }


}