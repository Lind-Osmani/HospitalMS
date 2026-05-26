package com.hospitalms.dto.doctor;

public class DoctorResponse{

    private Long id;
    private String firstName;
    private String lastName;
    private String specialization;
    private String phone;
    private String email;

    public DoctorResponse() {
    }

    public DoctorResponse(
            Long id,
            String firstName,
            String lastName,
            String specialization,
            String phone,
            String email
    ) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.specialization = specialization;
        this.phone = phone;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getSpecialization() {
        return specialization;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }
}