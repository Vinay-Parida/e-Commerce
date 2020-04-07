package com.example.SpringSecurity.entity.users;

public enum Label {
    OFFICE("office"), HOME("home");

    private String label;

    private Label(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
