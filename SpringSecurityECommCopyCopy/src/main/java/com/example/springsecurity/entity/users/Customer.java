package com.example.springsecurity.entity.users;
import com.example.springsecurity.entity.products.ProductReview;

import javax.persistence.*;
import java.util.List;

@Entity
@DiscriminatorValue(value = "customer")
public class Customer extends User {

    private String contact;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<ProductReview> productReviews;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Address> addresses;

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
