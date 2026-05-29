package com.hospitalms.config;

public final class AppointmentFormContext {

    private static Long editingAppointmentId;

    private AppointmentFormContext() {
    }

    public static void setEditingAppointmentId(Long appointmentId) {
        editingAppointmentId = appointmentId;
    }

    public static Long getEditingAppointmentId() {
        return editingAppointmentId;
    }

    public static boolean isEditMode() {
        return editingAppointmentId != null;
    }

    public static void clear() {
        editingAppointmentId = null;
    }
}