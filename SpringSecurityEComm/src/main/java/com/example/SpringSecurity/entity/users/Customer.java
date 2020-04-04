package com.example.SpringSecurity.entity.users;
import com.example.SpringSecurity.entity.products.ProductReview;

import javax.persistence.*;
import java.util.List;

@Entity
@DiscriminatorValue(value = "customer")
public class Customer extends User {

    private int contact;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<ProductReview> productReviews;

    public int getContact() {
        return contact;
    }

    public void setContact(int contact) {
        this.contact = contact;
    }
}
