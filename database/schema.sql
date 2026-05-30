CREATE DATABASE IF NOT EXISTS hospitalms_db;

USE hospitalms_db;

CREATE TABLE IF NOT EXISTS patients (
                                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                        first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    gender VARCHAR(20),
    date_of_birth DATE,
    phone VARCHAR(30) NOT NULL,
    email VARCHAR(150),
    blood_group VARCHAR(10),
    address TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );

CREATE TABLE IF NOT EXISTS doctors (
                                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                       first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    specialization VARCHAR(150) NOT NULL,
    department VARCHAR(150),
    phone VARCHAR(30) NOT NULL UNIQUE,
    email VARCHAR(150) UNIQUE,
    address TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );

CREATE TABLE IF NOT EXISTS appointments (
                                            id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                            patient_id BIGINT NOT NULL,
                                            doctor_id BIGINT NOT NULL,
                                            appointment_date DATE NOT NULL,
                                            appointment_time TIME NOT NULL,
                                            reason TEXT NOT NULL,
                                            status VARCHAR(30) NOT NULL DEFAULT 'SCHEDULED',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_appointments_patient
    FOREIGN KEY (patient_id) REFERENCES patients(id)
    ON DELETE CASCADE,

    CONSTRAINT fk_appointments_doctor
    FOREIGN KEY (doctor_id) REFERENCES doctors(id)
    ON DELETE CASCADE
    );

CREATE TABLE IF NOT EXISTS medical_records (
                                               id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                               appointment_id BIGINT NOT NULL UNIQUE,
                                               patient_id BIGINT NOT NULL,
                                               doctor_id BIGINT NOT NULL,
                                               diagnosis TEXT NOT NULL,
                                               treatment TEXT NOT NULL,
                                               prescription TEXT NOT NULL,
                                               notes TEXT NOT NULL,
                                               created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

                                               CONSTRAINT fk_medical_records_appointment
                                               FOREIGN KEY (appointment_id) REFERENCES appointments(id)
    ON DELETE CASCADE,

    CONSTRAINT fk_medical_records_patient
    FOREIGN KEY (patient_id) REFERENCES patients(id)
    ON DELETE CASCADE,

    CONSTRAINT fk_medical_records_doctor
    FOREIGN KEY (doctor_id) REFERENCES doctors(id)
    ON DELETE CASCADE
    );