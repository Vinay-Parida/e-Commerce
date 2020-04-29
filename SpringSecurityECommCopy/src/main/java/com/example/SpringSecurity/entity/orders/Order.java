package com.example.SpringSecurity.entity.orders;

import com.example.SpringSecurity.entity.users.Customer;

import javax.persistence.*;
import java.util.Date;

//@Entity
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_customer_id")
    private Customer customer;

    private double amountPaid;
    private Date dateCreated;
    private final String paymentMethod = "Cash";

    private String customer_address_city;
    private String customer_address_state;
    private String customer_address_country;
    private String customer_address_address_line;
    private Integer customer_address_zip_code;
    private String customer_address_label;


}
