package com.example.SpringSecurity.dto;

import java.math.BigInteger;

public class FindAllSellerDto {
    private BigInteger id;
    private String fullName;
    private String email;
    private String companyName;
//    private String companyAddress;
    private String address_line;
    private String city;
    private String state;
    private String country;
    private String zip_code;
    private String companyContact;
    private Boolean isActive;

    public  FindAllSellerDto(BigInteger id, String fullName, String email, Boolean isActive, String companyName, String address_line, String city, String state, String country,  String companyContact, String zip_code) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.companyName = companyName;
        this.address_line = address_line;
        this.city = city;
        this.state = state;
        this.country = country;
        this.zip_code = zip_code;
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

    public String getAddress_line() {
        return address_line;
    }

    public void setAddress_line(String address_line) {
        this.address_line = address_line;
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

    public String getZip_code() {
        return zip_code;
    }

    public void setZip_code(String zip_code) {
        this.zip_code = zip_code;
    }

    //Without getter setters the data is not shown while getting list in postman

}
