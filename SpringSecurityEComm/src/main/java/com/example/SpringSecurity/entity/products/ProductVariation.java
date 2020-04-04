package com.example.SpringSecurity.entity.products;
import javax.persistence.*;

@Entity
public class ProductVariation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private int quantity_available;
    private double price;
    private String metadata;
    private String primary_image_name;


}
