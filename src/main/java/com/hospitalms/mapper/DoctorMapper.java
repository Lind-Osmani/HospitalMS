package com.hospitalms.mapper;

import com.hospitalms.core.mapper.BaseMapper;
import com.hospitalms.dto.doctor.DoctorResponse;
import com.hospitalms.model.Doctor;

import java.util.List;

public class DoctorMapper implements BaseMapper<Doctor, DoctorResponse> {

    @Override
    public DoctorResponse toResponse(Doctor doctor) {
        return new DoctorResponse(
                doctor.getId(),
                doctor.getFirstName(),
                doctor.getLastName(),
                doctor.getSpecialization(),
                doctor.getPhone(),
                doctor.getEmail()
        );
    }

    @Override
    public List<DoctorResponse> toResponseList(List<Doctor> doctors) {
        return doctors.stream()
                .map(this::toResponse)
                .toList();
    }
}