package com.example.springsecurity.entity.products;
import javax.persistence.*;
import java.util.Map;

@Entity
public class ProductVariation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private Integer quantityAvailable;
    private Double price;

    @Convert(converter = HashMapConverter.class)
    private Map<String,String> metadata;

    private String primaryImageName;
    private String secondaryImage;

    private Boolean isActive = true;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQuantityAvailable() {
        return quantityAvailable;
    }

    public void setQuantityAvailable(Integer quantityAvailable) {
        this.quantityAvailable = quantityAvailable;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Map<String, String> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, String> metadata) {
        this.metadata = metadata;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public String getSecondaryImage() {
        return secondaryImage;
    }

    public void setSecondaryImage(String secondaryImage) {
        this.secondaryImage = secondaryImage;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public String getPrimaryImageName() {
        return primaryImageName;
    }

    public void setPrimaryImageName(String primaryImageName) {
        this.primaryImageName = primaryImageName;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    @Override
    public String toString() {
        return "ProductVariation{" +
                "id=" + id +
                ", product=" + product +
                ", quantityAvailable=" + quantityAvailable +
                ", price=" + price +
                ", metadata=" + metadata +
                ", primaryImageName='" + primaryImageName + '\'' +
                ", secondaryImage='" + secondaryImage + '\'' +
                ", isActive=" + isActive +
                '}';
    }
}
