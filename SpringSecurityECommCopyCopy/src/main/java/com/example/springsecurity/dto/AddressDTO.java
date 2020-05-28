package com.example.springsecurity.dto;

import com.example.springsecurity.entity.users.Label;

public class AddressDTO {
    private String city;
    private String state;
    private String country;
    private String addressLine;
    private Label label;
    private String zipCode;

    public AddressDTO(String city, String state, String country, String addressLine, Label label, String zipCode) {
        this.city = city;
        this.state = state;
        this.country = country;
        this.addressLine = addressLine;
        this.label = label;
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAddressLine() {
        return addressLine;
    }

    public void setAddressLine(String addressLine) {
        this.addressLine = addressLine;
    }

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
}
