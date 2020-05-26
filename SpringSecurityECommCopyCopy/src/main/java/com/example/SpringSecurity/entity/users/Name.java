package com.example.SpringSecurity.entity.users;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

@Embeddable
public class Name {

    @NotNull
    private String firstName;
    private String middleName;
    @NotNull
    private String lastName;

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
}
