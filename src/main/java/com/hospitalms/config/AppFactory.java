package com.hospitalms.config;

import com.hospitalms.repository.PatientRepository;
import com.hospitalms.repository.impl.InMemoryPatientRepository;
import com.hospitalms.service.PatientService;
import com.hospitalms.service.impl.PatientServiceImpl;

public final class AppFactory {

    private static final PatientRepository patientRepository = new InMemoryPatientRepository();
    private static final PatientService patientService = new PatientServiceImpl(patientRepository);

    private AppFactory() {
    }

    public static PatientService getPatientService() {
        return patientService;
    }
}