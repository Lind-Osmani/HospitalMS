package com.hospitalms.validator;

import com.hospitalms.util.ValidationUtil;

public class MedicalRecordValidator {

    public String validate(String diagnosis, String treatment, String prescription, String notes) {
        if (ValidationUtil.isBlank(diagnosis)) {
            return "Diagnosis is required.";
        }

        if (ValidationUtil.isBlank(treatment)) {
            return "Treatment is required.";
        }

        if (ValidationUtil.isBlank(prescription)) {
            return "Prescription is required.";
        }

        if (ValidationUtil.isBlank(notes)) {
            return "Notes are required.";
        }

        return null;
    }
}