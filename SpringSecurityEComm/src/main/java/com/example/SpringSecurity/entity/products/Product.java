package com.example.SpringSecurity.entity.products;
import com.example.SpringSecurity.entity.users.Seller;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "seller_user_id")
    private Seller seller;

    private String name;
    private String description;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private Set<ProductVariation> productVariations;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductReview> productReviews;

    //Category ID??????? needs to be mapped but later

    private boolean is_cancellable;
    private boolean is_returnable;
    private boolean is_active;

    private String brand;







}
