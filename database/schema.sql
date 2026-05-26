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