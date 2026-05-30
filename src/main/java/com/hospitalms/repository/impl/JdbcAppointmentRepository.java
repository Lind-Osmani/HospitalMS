package com.hospitalms.repository.impl;

import com.hospitalms.core.repository.AbstractJdbcRepository;
import com.hospitalms.exception.DatabaseException;
import com.hospitalms.model.Appointment;
import com.hospitalms.model.AppointmentStatus;
import com.hospitalms.repository.AppointmentRepository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.time.LocalDate;
import java.time.LocalTime;

public class JdbcAppointmentRepository extends AbstractJdbcRepository implements AppointmentRepository {

    @Override
    public Appointment save(Appointment appointment) {
        String sql = """
                INSERT INTO appointments (
                    patient_id,
                    doctor_id,
                    appointment_date,
                    appointment_time,
                    reason,
                    status
                )
                VALUES (?, ?, ?, ?, ?, ?)
                """;

        try (
                var connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setLong(1, appointment.getPatientId());
            statement.setLong(2, appointment.getDoctorId());
            statement.setDate(3, Date.valueOf(appointment.getAppointmentDate()));
            statement.setTime(4, Time.valueOf(appointment.getAppointmentTime()));
            statement.setString(5, appointment.getReason());
            statement.setString(6, appointment.getStatus().name());

            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();

            if (generatedKeys.next()) {
                Long id = generatedKeys.getLong(1);
                return findById(id)
                        .orElseThrow(() -> new DatabaseException("Appointment was saved but could not be loaded."));
            }

            throw new DatabaseException("Creating appointment failed. No ID was generated.");

        } catch (Exception e) {
            throw new DatabaseException("Could not save appointment.", e);
        }
    }

    @Override
    public Appointment update(Appointment appointment) {
        String sql = """
            UPDATE appointments
            SET patient_id = ?,
                doctor_id = ?,
                appointment_date = ?,
                appointment_time = ?,
                reason = ?,
                status = ?
            WHERE id = ?
            """;

        try (
                var connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setLong(1, appointment.getPatientId());
            statement.setLong(2, appointment.getDoctorId());
            statement.setDate(3, Date.valueOf(appointment.getAppointmentDate()));
            statement.setTime(4, Time.valueOf(appointment.getAppointmentTime()));
            statement.setString(5, appointment.getReason());
            statement.setString(6, appointment.getStatus().name());
            statement.setLong(7, appointment.getId());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new DatabaseException("Appointment not found with ID: " + appointment.getId());
            }

            return findById(appointment.getId())
                    .orElseThrow(() -> new DatabaseException("Appointment was updated but could not be loaded."));

        } catch (Exception e) {
            throw new DatabaseException("Could not update appointment.", e);
        }
    }

    @Override
    public Optional<Appointment> findById(Long id) {
        String sql = """
                SELECT a.*,
                       CONCAT(p.first_name, ' ', p.last_name) AS patient_name,
                       CONCAT(d.first_name, ' ', d.last_name) AS doctor_name
                FROM appointments a
                JOIN patients p ON a.patient_id = p.id
                JOIN doctors d ON a.doctor_id = d.id
                WHERE a.id = ?
                """;

        try (
                var connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setLong(1, id);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(mapResultSetToAppointment(resultSet));
            }

            return Optional.empty();

        } catch (Exception e) {
            throw new DatabaseException("Could not find appointment by ID.", e);
        }
    }

    @Override
    public List<Appointment> findAll() {
        String sql = """
                SELECT a.*,
                       CONCAT(p.first_name, ' ', p.last_name) AS patient_name,
                       CONCAT(d.first_name, ' ', d.last_name) AS doctor_name
                FROM appointments a
                JOIN patients p ON a.patient_id = p.id
                JOIN doctors d ON a.doctor_id = d.id
                ORDER BY a.id DESC
                """;

        try (
                var connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()
        ) {
            List<Appointment> appointments = new ArrayList<>();

            while (resultSet.next()) {
                appointments.add(mapResultSetToAppointment(resultSet));
            }

            return appointments;

        } catch (Exception e) {
            throw new DatabaseException("Could not load appointments.", e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        String sql = "DELETE FROM appointments WHERE id = ?";

        try (
                var connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;

        } catch (Exception e) {
            throw new DatabaseException("Could not delete appointment.", e);
        }
    }

    @Override
    public boolean existsByDoctorAndDateTime(
            Long doctorId,
            LocalDate appointmentDate,
            LocalTime appointmentTime
    ) {
        String sql = """
            SELECT COUNT(*)
            FROM appointments
            WHERE doctor_id = ?
              AND appointment_date = ?
              AND appointment_time = ?
            """;

        try (
                var connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setLong(1, doctorId);
            statement.setDate(2, Date.valueOf(appointmentDate));
            statement.setTime(3, Time.valueOf(appointmentTime));

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }

            return false;

        } catch (Exception e) {
            throw new DatabaseException("Could not check appointment conflict.", e);
        }
    }

    @Override
    public boolean existsByDoctorAndDateTimeAndIdNot(
            Long doctorId,
            LocalDate appointmentDate,
            LocalTime appointmentTime,
            Long appointmentId
    ) {
        String sql = """
            SELECT COUNT(*)
            FROM appointments
            WHERE doctor_id = ?
              AND appointment_date = ?
              AND appointment_time = ?
              AND id <> ?
            """;

        try (
                var connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setLong(1, doctorId);
            statement.setDate(2, Date.valueOf(appointmentDate));
            statement.setTime(3, Time.valueOf(appointmentTime));
            statement.setLong(4, appointmentId);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }

            return false;

        } catch (Exception e) {
            throw new DatabaseException("Could not check appointment conflict for update.", e);
        }
    }

    @Override
    public List<Appointment> search(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return findAll();
        }

        String sql = """
                SELECT a.*,
                       CONCAT(p.first_name, ' ', p.last_name) AS patient_name,
                       CONCAT(d.first_name, ' ', d.last_name) AS doctor_name
                FROM appointments a
                JOIN patients p ON a.patient_id = p.id
                JOIN doctors d ON a.doctor_id = d.id
                WHERE LOWER(CONCAT(p.first_name, ' ', p.last_name)) LIKE ?
                   OR LOWER(CONCAT(d.first_name, ' ', d.last_name)) LIKE ?
                   OR LOWER(a.reason) LIKE ?
                   OR LOWER(a.status) LIKE ?
                ORDER BY a.id DESC
                """;

        try (
                var connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            String searchValue = "%" + keyword.toLowerCase() + "%";

            statement.setString(1, searchValue);
            statement.setString(2, searchValue);
            statement.setString(3, searchValue);
            statement.setString(4, searchValue);

            ResultSet resultSet = statement.executeQuery();

            List<Appointment> appointments = new ArrayList<>();

            while (resultSet.next()) {
                appointments.add(mapResultSetToAppointment(resultSet));
            }

            return appointments;

        } catch (Exception e) {
            throw new DatabaseException("Could not search appointments.", e);
        }
    }

    private Appointment mapResultSetToAppointment(ResultSet resultSet) throws Exception {
        return new Appointment(
                resultSet.getLong("id"),
                resultSet.getLong("patient_id"),
                resultSet.getLong("doctor_id"),
                resultSet.getString("patient_name"),
                resultSet.getString("doctor_name"),
                resultSet.getDate("appointment_date").toLocalDate(),
                resultSet.getTime("appointment_time").toLocalTime(),
                resultSet.getString("reason"),
                AppointmentStatus.valueOf(resultSet.getString("status"))
        );
    }
}