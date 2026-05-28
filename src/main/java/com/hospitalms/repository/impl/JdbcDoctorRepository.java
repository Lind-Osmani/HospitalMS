package com.hospitalms.repository.impl;

import com.hospitalms.core.repository.AbstractJdbcRepository;
import com.hospitalms.exception.DatabaseException;
import com.hospitalms.model.Doctor;
import com.hospitalms.repository.DoctorRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class JdbcDoctorRepository extends AbstractJdbcRepository implements DoctorRepository {

    private static final String TABLE_NAME = "doctors";

    @Override
    public Doctor save(Doctor doctor){
        String sql = """
                INSERT INTO doctors(
                first_name,
                last_name,
                specialization,
                department,
                phone,
                email,
                address
                )
                VALUES (?, ?, ?, ?, ?, ?, ?)
                """;
        try(
                var connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ){
            fillDoctorStatement(statement, doctor);
            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();

            if (generatedKeys.next()) {
                Long id = generatedKeys.getLong(1);

                return new Doctor(
                        id,
                        doctor.getFirstName(),
                        doctor.getLastName(),
                        doctor.getSpecialization(),
                        doctor.getDepartment(),
                        doctor.getPhone(),
                        doctor.getEmail(),
                        doctor.getAddress()
                );
            }
            throw new DatabaseException("Creating doctor failed. No ID was generated.");

        } catch (Exception e) {
            throw new DatabaseException("Could not save doctor.", e);
        }
    }

    @Override
    public Doctor update(Doctor doctor) {
        String sql = """
                UPDATE doctors
                SET first_name = ?,
                    last_name = ?,
                    specialization = ?,
                    department = ?,
                    phone = ?,
                    email = ?,
                    address = ?
                WHERE id = ?
                """;

        try (
                var connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            fillDoctorStatement(statement, doctor);
            statement.setLong(8, doctor.getId());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new DatabaseException("Doctor not found with ID: " + doctor.getId());
            }

            return doctor;

        } catch (Exception e) {
            throw new DatabaseException("Could not update doctor.", e);
        }
    }

    @Override
    public Optional<Doctor> findById(Long id) {
        String sql = "SELECT * FROM doctors WHERE id = ?";

        try (
                var connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setLong(1, id);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(mapResultSetToDoctor(resultSet));
            }

            return Optional.empty();

        } catch (Exception e) {
            throw new DatabaseException("Could not find doctor by ID.", e);
        }
    }

    @Override
    public List<Doctor> findAll() {
        String sql = "SELECT * FROM doctors ORDER BY id DESC";

        try (
                var connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()
        ) {
            List<Doctor> doctors = new ArrayList<>();

            while (resultSet.next()) {
                doctors.add(mapResultSetToDoctor(resultSet));
            }

            return doctors;

        } catch (Exception e) {
            throw new DatabaseException("Could not load doctors.", e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        String sql = "DELETE FROM doctors WHERE id = ?";

        try (
                var connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;

        } catch (Exception e) {
            throw new DatabaseException("Could not delete doctor.", e);
        }
    }

    @Override
    public List<Doctor> searchByNameOrSpecialization(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return findAll();
        }

        String sql = """
                SELECT *
                FROM doctors
                WHERE LOWER(first_name) LIKE ?
                   OR LOWER(last_name) LIKE ?
                   OR LOWER(specialization) LIKE ?
                ORDER BY id DESC
                """;

        try (
                var connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            String searchValue = "%" + keyword.toLowerCase() + "%";

            statement.setString(1, searchValue);
            statement.setString(2, searchValue);
            statement.setString(3, searchValue);

            ResultSet resultSet = statement.executeQuery();

            List<Doctor> doctors = new ArrayList<>();

            while (resultSet.next()) {
                doctors.add(mapResultSetToDoctor(resultSet));
            }

            return doctors;

        } catch (Exception e) {
            throw new DatabaseException("Could not search doctors.", e);
        }
    }

    @Override
    public boolean existsByPhone(String phone) {
        return existsByColumn(TABLE_NAME, "phone", phone);
    }

    @Override
    public boolean existsByEmail(String email) {
        return existsByColumn(TABLE_NAME, "email", email);
    }

    @Override
    public boolean existsByPhoneAndIdNot(String phone, Long id){
        return existsByColumnAndIdNot(TABLE_NAME, "phone", phone, id);
    }

    @Override
    public boolean existsByEmailAndIdNot(String email, Long id){
        return existsByColumnAndIdNot(TABLE_NAME, "email", email, id);
    }

    private void fillDoctorStatement(PreparedStatement statement, Doctor doctor) throws Exception {
        statement.setString(1, doctor.getFirstName());
        statement.setString(2, doctor.getLastName());
        statement.setString(3, doctor.getSpecialization());
        statement.setString(4, doctor.getDepartment());
        statement.setString(5, doctor.getPhone());
        statement.setString(6, doctor.getEmail());
        statement.setString(7, doctor.getAddress());
    }

    private Doctor mapResultSetToDoctor(ResultSet resultSet) throws Exception {
        return new Doctor(
                resultSet.getLong("id"),
                resultSet.getString("first_name"),
                resultSet.getString("last_name"),
                resultSet.getString("specialization"),
                resultSet.getString("department"),
                resultSet.getString("phone"),
                resultSet.getString("email"),
                resultSet.getString("address")
        );
    }
}





