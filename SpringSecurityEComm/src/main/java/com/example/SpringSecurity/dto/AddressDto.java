package com.example.SpringSecurity.dto;

import com.example.SpringSecurity.entity.users.Label;

public class AddressDto {
    private String city;
    private String state;
    private String country;
    private String address_line;
    private Label label;
    private String zip_code;

    public AddressDto(String city, String state, String country, String address_line, Label label, String zip_code) {
        this.city = city;
        this.state = state;
        this.country = country;
        this.address_line = address_line;
        this.label = label;
        this.zip_code = zip_code;
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

    public String getAddress_line() {
        return address_line;
    }

    public void setAddress_line(String address_line) {
        this.address_line = address_line;
    }

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    public String getZip_code() {
        return zip_code;
    }

    public void setZip_code(String zip_code) {
        this.zip_code = zip_code;
    }
}
