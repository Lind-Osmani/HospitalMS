package com.hospitalms.dto.doctor;

public class DoctorCreateRequest{

    private String firstName;
    private String lastName;
    private String specialization;
    private String department;
    private String phone;
    private String email;
    private String address;

    public DoctorCreateRequest() {
    }

    public DoctorCreateRequest(
            String firstName,
            String lastName,
            String specialization,
            String department,
            String phone,
            String email,
            String address
    ) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.specialization = specialization;
        this.department = department;
        this.phone = phone;
        this.email = email;
        this.address = address;
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

    public String getDepartment() {
        return department;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }
}