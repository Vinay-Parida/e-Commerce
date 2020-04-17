package com.example.SpringSecurity.dto;

import com.example.SpringSecurity.entity.users.Label;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class SellerRegisterDto {

    @NotNull
    @Pattern(regexp = "[a-zA-Z][a-zA-Z0-9_.]*@[a-zA-Z]+[.][a-zA-Z]+", message = "Invalid Email Format")
    private String email;

    @NotNull
    private String first_name;
    private String middle_name;
    @NotNull
    private String last_name;

    @NotNull
    private String company_name;

    private String address_line;
    private String city;
    private String state;
    private String country;
    private String zip_code;
    private Label label;

    @Pattern(regexp="(^$|[0-9]{10})",message = "Contact can be only 10 digits")
    private String company_contact;

    @NotNull
    @Pattern(regexp="(\\d{2}[A-Z]{5}\\d{4}[A-Z]{1}[A-Z\\d]{1}[Z]{1}[A-Z\\d]{1})")    //Pattern for gst no. Remove it for sake of complexity
    private String gst;

    @NotNull
    @Pattern(regexp="((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{8,})",message="Password must be 8 characters long")
    private String password;

    public SellerRegisterDto(@NotNull @Pattern(regexp = "[a-zA-Z][a-zA-Z0-9_.]*@[a-zA-Z]+[.][a-zA-Z]+", message = "Invalid Email Format") String email, @NotNull String first_name, String middle_name, @NotNull String last_name, @NotNull String company_name, @Pattern(regexp = "(^$|[0-9]{10})", message = "Contact can be only 10 digits") String company_contact, @NotNull @Pattern(regexp = "(\\d{2}[A-Z]{5}\\d{4}[A-Z]{1}[A-Z\\d]{1}[Z]{1}[A-Z\\d]{1})") String gst, @NotNull @Pattern(regexp = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{8,})", message = "Password must be 8 characters long") String password) {
        this.email = email;
        this.first_name = first_name;
        this.middle_name = middle_name;
        this.last_name = last_name;
        this.company_name = company_name;
        this.company_contact = company_contact;
        this.gst = gst;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getMiddle_name() {
        return middle_name;
    }

    public void setMiddle_name(String middle_name) {
        this.middle_name = middle_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getCompany_contact() {
        return company_contact;
    }

    public void setCompany_contact(String company_contact) {
        this.company_contact = company_contact;
    }

    public String  getGst() {
        return gst;
    }

    public void setGst(String gst) {
        this.gst = gst;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }
}
