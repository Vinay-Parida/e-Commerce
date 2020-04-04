package com.example.SpringSecurity.entity.products;

import com.example.SpringSecurity.entity.users.Customer;

import javax.persistence.*;

@Entity
public class ProductReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_user_id")
    private Customer customer;

    private String review;
    private float rating;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
