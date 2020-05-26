package com.example.SpringSecurity.entity.users;

import com.example.SpringSecurity.entity.products.Product;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@DiscriminatorValue(value = "seller")
public class Seller extends User {

    @NotNull
    private String gst;

    private String companyContact;
    private String companyName;

    @OneToMany(mappedBy = "seller", cascade = CascadeType.ALL)
    private List<Product> product;


    public String  getGst() {
        return gst;
    }

    public void setGst(String  gst) {
        this.gst = gst;
    }

    public String getCompanyContact() {
        return companyContact;
    }

    public void setCompanyContact(String companyContact) {
        this.companyContact = companyContact;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String comapanyName) {
        this.companyName = comapanyName;
    }
}
