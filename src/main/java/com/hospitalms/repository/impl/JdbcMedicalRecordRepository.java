package com.hospitalms.repository.impl;

import com.hospitalms.core.repository.AbstractJdbcRepository;
import com.hospitalms.exception.DatabaseException;
import com.hospitalms.model.MedicalRecord;
import com.hospitalms.repository.MedicalRecordRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcMedicalRecordRepository extends AbstractJdbcRepository implements MedicalRecordRepository {

    @Override
    public MedicalRecord save(MedicalRecord medicalRecord) {
        String sql = """
                INSERT INTO medical_records (
                    appointment_id,
                    patient_id,
                    doctor_id,
                    diagnosis,
                    treatment,
                    prescription,
                    notes
                )
                VALUES (?, ?, ?, ?, ?, ?, ?)
                """;

        try (
                var connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            fillMedicalRecordStatement(statement, medicalRecord);
            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();

            if (generatedKeys.next()) {
                Long id = generatedKeys.getLong(1);

                return findById(id)
                        .orElseThrow(() -> new DatabaseException("Medical record was saved but could not be loaded."));
            }

            throw new DatabaseException("Creating medical record failed. No ID was generated.");

        } catch (Exception e) {
            throw new DatabaseException("Could not save medical record.", e);
        }
    }

    @Override
    public MedicalRecord update(MedicalRecord medicalRecord) {
        String sql = """
                UPDATE medical_records
                SET diagnosis = ?,
                    treatment = ?,
                    prescription = ?,
                    notes = ?
                WHERE id = ?
                """;

        try (
                var connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setString(1, medicalRecord.getDiagnosis());
            statement.setString(2, medicalRecord.getTreatment());
            statement.setString(3, medicalRecord.getPrescription());
            statement.setString(4, medicalRecord.getNotes());
            statement.setLong(5, medicalRecord.getId());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new DatabaseException("Medical record not found with ID: " + medicalRecord.getId());
            }

            return findById(medicalRecord.getId())
                    .orElseThrow(() -> new DatabaseException("Medical record was updated but could not be loaded."));

        } catch (Exception e) {
            throw new DatabaseException("Could not update medical record.", e);
        }
    }

    @Override
    public Optional<MedicalRecord> findById(Long id) {
        String sql = baseSelectQuery() + " WHERE mr.id = ?";

        try (
                var connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setLong(1, id);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(mapResultSetToMedicalRecord(resultSet));
            }

            return Optional.empty();

        } catch (Exception e) {
            throw new DatabaseException("Could not find medical record by ID.", e);
        }
    }

    @Override
    public List<MedicalRecord> findAll() {
        String sql = baseSelectQuery() + " ORDER BY mr.id DESC";

        try (
                var connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()
        ) {
            List<MedicalRecord> medicalRecords = new ArrayList<>();

            while (resultSet.next()) {
                medicalRecords.add(mapResultSetToMedicalRecord(resultSet));
            }

            return medicalRecords;

        } catch (Exception e) {
            throw new DatabaseException("Could not load medical records.", e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        String sql = "DELETE FROM medical_records WHERE id = ?";

        try (
                var connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;

        } catch (Exception e) {
            throw new DatabaseException("Could not delete medical record.", e);
        }
    }

    @Override
    public Optional<MedicalRecord> findByAppointmentId(Long appointmentId) {
        String sql = baseSelectQuery() + " WHERE mr.appointment_id = ?";

        try (
                var connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setLong(1, appointmentId);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(mapResultSetToMedicalRecord(resultSet));
            }

            return Optional.empty();

        } catch (Exception e) {
            throw new DatabaseException("Could not find medical record by appointment ID.", e);
        }
    }

    @Override
    public boolean existsByAppointmentId(Long appointmentId) {
        String sql = "SELECT COUNT(*) FROM medical_records WHERE appointment_id = ?";

        try (
                var connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setLong(1, appointmentId);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }

            return false;

        } catch (Exception e) {
            throw new DatabaseException("Could not check if medical record exists.", e);
        }
    }

    private String baseSelectQuery() {
        return """
                SELECT mr.*,
                       CONCAT(p.first_name, ' ', p.last_name) AS patient_name,
                       CONCAT(d.first_name, ' ', d.last_name) AS doctor_name
                FROM medical_records mr
                JOIN patients p ON mr.patient_id = p.id
                JOIN doctors d ON mr.doctor_id = d.id
                """;
    }

    private void fillMedicalRecordStatement(
            PreparedStatement statement,
            MedicalRecord medicalRecord
    ) throws Exception {
        statement.setLong(1, medicalRecord.getAppointmentId());
        statement.setLong(2, medicalRecord.getPatientId());
        statement.setLong(3, medicalRecord.getDoctorId());
        statement.setString(4, medicalRecord.getDiagnosis());
        statement.setString(5, medicalRecord.getTreatment());
        statement.setString(6, medicalRecord.getPrescription());
        statement.setString(7, medicalRecord.getNotes());
    }

    private MedicalRecord mapResultSetToMedicalRecord(ResultSet resultSet) throws Exception {
        Timestamp createdAt = resultSet.getTimestamp("created_at");

        return new MedicalRecord(
                resultSet.getLong("id"),
                resultSet.getLong("appointment_id"),
                resultSet.getLong("patient_id"),
                resultSet.getLong("doctor_id"),
                resultSet.getString("patient_name"),
                resultSet.getString("doctor_name"),
                resultSet.getString("diagnosis"),
                resultSet.getString("treatment"),
                resultSet.getString("prescription"),
                resultSet.getString("notes"),
                createdAt != null ? createdAt.toLocalDateTime() : null
        );
    }
}
