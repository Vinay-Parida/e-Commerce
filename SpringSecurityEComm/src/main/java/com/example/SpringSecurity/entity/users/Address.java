package com.example.SpringSecurity.entity.users;

import javax.persistence.*;

@Entity
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String city;
    private String state;
    private String country;
    private String address_line;
    private String zip_code;
    private Label label;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

//    @OneToOne(targetEntity = Seller.class, fetch = FetchType.EAGER)
//    @JoinColumn(nullable = false, name = "seller_id")
//    private Seller seller;

//
//    Address(){}
//
//    public Address(String city, String state, String country, String address_line, int zip_code, Label label) {
//        this.city = city;
//        this.state = state;
//        this.country = country;
//        this.address_line = address_line;
//        this.zip_code = zip_code;
//        this.label = label;
//    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getAddress_line() {
        return address_line;
    }

    public void setAddress_line(String address_line) {
        this.address_line = address_line;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
