package com.example.SpringSecurity.dto;

import java.math.BigInteger;

public class FindAllSellerDTO {
    private BigInteger id;
    private String fullName;
    private String email;
    private String companyName;
    private String addressLine;
    private String city;
    private String state;
    private String country;
    private String zipCode;
    private String companyContact;
    private Boolean isActive;

    public FindAllSellerDTO(BigInteger id, String fullName, String email, Boolean isActive, String companyName, String addressLine, String city, String state, String country, String companyContact, String zipCode) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.companyName = companyName;
        this.addressLine = addressLine;
        this.city = city;
        this.state = state;
        this.country = country;
        this.zipCode = zipCode;
        this.companyContact = companyContact;
        this.isActive = isActive;
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyContact() {
        return companyContact;
    }

    public void setCompanyContact(String companyContact) {
        this.companyContact = companyContact;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public String getAddressLine() {
        return addressLine;
    }

    public void setAddressLine(String addressLine) {
        this.addressLine = addressLine;
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

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    //Without getter setters the data is not shown while getting list in postman

}
