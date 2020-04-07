package com.example.SpringSecurity.entity.users;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

@Embeddable
public class Name {

    @NotNull
    private String first_name;
    private String middle_name;
    @NotNull
    private String last_name;

//    public Name(){}
//
//    public Name(@NotNull String first_name, String middle_name, @NotNull String last_name) {
//        this.first_name = first_name;
//        this.middle_name = middle_name;
//        this.last_name = last_name;
//    }

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
}
