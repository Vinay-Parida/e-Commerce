package com.example.SpringSecurity.dto;

import java.math.BigInteger;

public class FindAllSellerDto {
    private BigInteger id;
    private String fullName;
    private String email;
    private String companyName;
//    private String companyAddress;
    private String companyContact;
    private Boolean isActive;

    public FindAllSellerDto(BigInteger id, String fullName, String email, Boolean isActive, String companyName, String companyContact) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.companyName = companyName;
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
}
