package com.example.SpringSecurity.dto;

import java.math.BigInteger;

public class SellerProfileDTO {

    private BigInteger id;
    private String first_name;
    private String last_name;
    private Boolean is_Active;
    private String company_contact;
    private String company_name;
    private String image;
    private String gst;
    private String address_line;
    private String city;
    private String state;
    private String country;
    private String zip_code;

    public SellerProfileDTO(BigInteger id, String first_name, String last_name, Boolean is_Active, String company_contact, String company_name, String image, String gst, String address_line, String city, String state, String country, String zip_code) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.is_Active = is_Active;
        this.company_contact = company_contact;
        this.company_name = company_name;
        this.image = image;
        this.gst = gst;
        this.address_line = address_line;
        this.city = city;
        this.state = state;
        this.country = country;
        this.zip_code = zip_code;
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

    public String getCompany_contact() {
        return company_contact;
    }

    public void setCompany_contact(String company_contact) {
        this.company_contact = company_contact;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getGst() {
        return gst;
    }

    public void setGst(String gst) {
        this.gst = gst;
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
}
