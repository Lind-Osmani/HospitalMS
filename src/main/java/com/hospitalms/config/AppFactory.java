package com.hospitalms.config;

import com.hospitalms.repository.DoctorRepository;
import com.hospitalms.repository.PatientRepository;
import com.hospitalms.repository.impl.JdbcDoctorRepository;
import com.hospitalms.repository.impl.JdbcPatientRepository;
import com.hospitalms.service.DoctorService;
import com.hospitalms.service.PatientService;
import com.hospitalms.service.impl.DoctorServiceImpl;
import com.hospitalms.service.impl.PatientServiceImpl;

public final class AppFactory {

    private static final PatientRepository patientRepository = new JdbcPatientRepository();
    private static final PatientService patientService = new PatientServiceImpl(patientRepository);

    private static final DoctorRepository doctorRepository = new JdbcDoctorRepository();
    private static final DoctorService doctorService = new DoctorServiceImpl(doctorRepository);

    private AppFactory() {
    }

    public static PatientService getPatientService() {
        return patientService;
    }

    public static DoctorService getDoctorService(){ return doctorService; }

}