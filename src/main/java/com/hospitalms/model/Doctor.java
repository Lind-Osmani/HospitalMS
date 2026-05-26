package com.hospitalms.model;



public class Doctor{

    private Long id;
    private String firstName;
    private String lastName;
    private String specialization;
    private String department;
    private String phone;
    private String email;
    private String address;

    public Doctor() {
    }

    public Doctor(
            Long id,
            String firstName,
            String lastName,
            String specialization,
            String department,
            String phone,
            String email,
            String address
    ){
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.specialization = specialization;
        this.department = department;
        this.phone = phone;
        this.email = email;
        this.address = address;
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