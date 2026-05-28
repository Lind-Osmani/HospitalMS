package com.hospitalms.config;

public final class DoctorFormContext{

    private static Long editingDoctorId;

    private DoctorFormContext(){}

    public static void setEditingDoctorId(Long doctorId){
        editingDoctorId = doctorId;
    }

    public static Long getEditingDoctorId(){
        return editingDoctorId;
    }

    public static boolean isEditMode(){
        return editingDoctorId != null;
    }

    public static void clear(){
        editingDoctorId = null;
    }
}