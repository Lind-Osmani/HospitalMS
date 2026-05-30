package com.hospitalms.model;

public enum AppointmentStatus {

    SCHEDULED("Scheduled"),
    COMPLETED("Completed"),
    CANCELLED("Cancelled");

    private final String displayName;

    AppointmentStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public boolean isFinalStatus() {
        return this == COMPLETED || this == CANCELLED;
    }

    public boolean canTransitionTo(AppointmentStatus newStatus) {
        if (newStatus == null) {
            return false;
        }

        if (this.isFinalStatus()) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return displayName;
    }
}