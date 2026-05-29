package com.hospitalms.config;

public final class AppointmentDetailsContext {

    private static Long selectedAppointmentId;

    private AppointmentDetailsContext() {
    }

    public static void setSelectedAppointmentId(Long appointmentId) {
        selectedAppointmentId = appointmentId;
    }

    public static Long getSelectedAppointmentId() {
        return selectedAppointmentId;
    }

    public static boolean hasSelectedAppointment() {
        return selectedAppointmentId != null;
    }

    public static void clear() {
        selectedAppointmentId = null;
    }
}