package com.hospitalms.dto.appointment;

public class AppointmentResponse {

    private Long id;
    private String patientName;
    private String doctorName;
    private String appointmentDate;
    private String appointmentTime;
    private String reason;
    private String status;

    public AppointmentResponse() {
    }

    public AppointmentResponse(
            Long id,
            String patientName,
            String doctorName,
            String appointmentDate,
            String appointmentTime,
            String reason,
            String status
    ) {
        this.id = id;
        this.patientName = patientName;
        this.doctorName = doctorName;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.reason = reason;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public String getPatientName() {
        return patientName;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public String getAppointmentDate() {
        return appointmentDate;
    }

    public String getAppointmentTime() {
        return appointmentTime;
    }

    public String getReason() {
        return reason;
    }

    public String getStatus() {
        return status;
    }
}