package com.hospitalms.core.ui;

public class ComboBoxItem {

    private final Long id;
    private final String label;

    public ComboBoxItem(Long id, String label) {
        this.id = id;
        this.label = label;
    }

    public Long getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return label;
    }
}