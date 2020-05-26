package com.example.SpringSecurity.dto;

import java.math.BigInteger;

public class CustomerProfileDTO {

    private BigInteger id;
    private String firstName;
    private String lastName;
    private Boolean isActive;
    private String contact;
    private String image;

    public CustomerProfileDTO(BigInteger id, String firstName, String lastName, Boolean isActive, String contact, String image) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.isActive = isActive;
        this.contact = contact;
        this.image = image;
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
