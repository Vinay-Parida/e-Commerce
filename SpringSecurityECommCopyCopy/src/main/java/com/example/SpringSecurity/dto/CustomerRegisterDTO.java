package com.example.SpringSecurity.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class CustomerRegisterDTO {

    @NotNull
    @Pattern(regexp = "[a-zA-Z][a-zA-Z0-9_.]*@[a-zA-Z]+[.][a-zA-Z]+", message = "Invalid Email Format")
    private String email;

    @NotNull
    private String firstName;
    private String middleName;
    @NotNull
    private String lastName;

    @NotNull
    @Pattern(regexp="(^$|[0-9]{10})",message = "Contact can be only 10 digits")
    private String contact;

    @NotNull
    @Pattern(regexp="((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{8,})",message="Password must be 8 characters long")
    private String password;

    @NotNull
    @Pattern(regexp="((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{8,})",message="Password must be 8 characters long")
    private String confirmPassword;

    public CustomerRegisterDTO(@NotNull @Pattern(regexp = "[a-zA-Z][a-zA-Z0-9_.]*@[a-zA-Z]+[.][a-zA-Z]+", message = "Invalid Email Format") String email, @NotNull String firstName, String middleName, @NotNull String lastName, @Pattern(regexp = "(^$|[0-9]{10})", message = "Contact can be only 10 digits") String contact, @NotNull @Pattern(regexp = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{8,})", message = "Password must be 8 characters long") String password, @NotNull @Pattern(regexp = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{8,})", message = "Password must be 8 characters long") String confirmPassword) {
        this.email = email;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.contact = contact;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String  getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
