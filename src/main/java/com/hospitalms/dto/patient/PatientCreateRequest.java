package com.hospitalms.dto.patient;

import java.time.LocalDate;

public class PatientCreateRequest {

    private String firstName;
    private String lastName;
    private String gender;
    private LocalDate dateOfBirth;
    private String phone;
    private String email;
    private String bloodGroup;
    private String address;

    public PatientCreateRequest() {
    }

    public PatientCreateRequest(
            String firstName,
            String lastName,
            String gender,
            LocalDate dateOfBirth,
            String phone,
            String email,
            String bloodGroup,
            String address
    ) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.phone = phone;
        this.email = email;
        this.bloodGroup = bloodGroup;
        this.address = address;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getGender() {
        return gender;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public String getAddress() {
        return address;
    }
}