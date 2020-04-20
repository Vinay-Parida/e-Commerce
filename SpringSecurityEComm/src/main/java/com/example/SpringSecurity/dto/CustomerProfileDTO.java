package com.example.SpringSecurity.dto;

import java.math.BigInteger;

public class CustomerProfileDTO {

    private BigInteger id;
    private String first_name;
    private String last_name;
    private Boolean is_Active;
    private String contact;
    private String image;

    public CustomerProfileDTO(BigInteger id, String first_name, String last_name, Boolean is_Active, String contact, String image) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.is_Active = is_Active;
        this.contact = contact;
        this.image = image;
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public Boolean getIs_Active() {
        return is_Active;
    }

    public void setIs_Active(Boolean is_Active) {
        this.is_Active = is_Active;
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
