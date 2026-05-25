package com.hospitalms.config;

public final class PatientFormContext {

    private static Long editingPatientId;

    private PatientFormContext() {
    }

    public static void setEditingPatientId(Long patientId) {
        editingPatientId = patientId;
    }

    public static Long getEditingPatientId() {
        return editingPatientId;
    }

    public static boolean isEditMode() {
        return editingPatientId != null;
    }

    public static void clear() {
        editingPatientId = null;
    }
}