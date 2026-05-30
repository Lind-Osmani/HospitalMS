package com.hospitalms.config;

import com.hospitalms.repository.DoctorRepository;
import com.hospitalms.repository.PatientRepository;
import com.hospitalms.repository.AppointmentRepository;
import com.hospitalms.repository.MedicalRecordRepository;

import com.hospitalms.repository.impl.JdbcDoctorRepository;
import com.hospitalms.repository.impl.JdbcPatientRepository;
import com.hospitalms.repository.impl.JdbcAppointmentRepository;
import com.hospitalms.repository.impl.JdbcMedicalRecordRepository;

import com.hospitalms.service.DoctorService;
import com.hospitalms.service.PatientService;
import com.hospitalms.service.AppointmentService;
import com.hospitalms.service.MedicalRecordService;

import com.hospitalms.service.impl.DoctorServiceImpl;
import com.hospitalms.service.impl.PatientServiceImpl;
import com.hospitalms.service.impl.AppointmentServiceImpl;
import com.hospitalms.service.impl.MedicalRecordServiceImpl;

public final class AppFactory {

    private static final PatientRepository patientRepository = new JdbcPatientRepository();
    private static final PatientService patientService = new PatientServiceImpl(patientRepository);

    private static final DoctorRepository doctorRepository = new JdbcDoctorRepository();
    private static final DoctorService doctorService = new DoctorServiceImpl(doctorRepository);

    private static final AppointmentRepository appointmentRepository = new JdbcAppointmentRepository();
    private static final AppointmentService appointmentService = new AppointmentServiceImpl(appointmentRepository);

    private static final MedicalRecordRepository medicalRecordRepository = new JdbcMedicalRecordRepository();
    private static final MedicalRecordService medicalRecordService =
            new MedicalRecordServiceImpl(
                    medicalRecordRepository,
                    appointmentService
            );

    private AppFactory() {
    }

    public static PatientService getPatientService() {
        return patientService;
    }

    public static DoctorService getDoctorService() {
        return doctorService;
    }

    public static AppointmentService getAppointmentService() {
        return appointmentService;
    }

    public static MedicalRecordService getMedicalRecordService() {
        return medicalRecordService;
    }
}