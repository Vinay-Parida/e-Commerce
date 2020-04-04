package com.example.SpringSecurity.entity.users;

import com.example.SpringSecurity.entity.products.Product;

import javax.persistence.*;
import java.util.List;

@Entity
@DiscriminatorValue(value = "seller")
public class Seller extends User {

    private double gst;

    private int company_contact;
    private String company_name;

    @OneToMany(mappedBy = "seller", cascade = CascadeType.ALL)
    private List<Product> product;

    Seller(){}

    public double getGst() {
        return gst;
    }

    public void setGst(double gst) {
        this.gst = gst;
    }

    public int getCompany_contact() {
        return company_contact;
    }

    public void setCompany_contact(int company_contact) {
        this.company_contact = company_contact;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String comapany_name) {
        this.company_name = comapany_name;
    }
}
