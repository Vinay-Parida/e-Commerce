package com.example.SpringSecurity.repository;

import com.example.SpringSecurity.entity.products.ProductVariation;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductVariationRepository extends CrudRepository<ProductVariation, Long> {

    @Query(value = "select * from product_variation where product_id =:productId",nativeQuery = true)
    public List<ProductVariation> getAllVariationByProductId(@Param("productId") Long parentId, Pageable pageable);

    List<ProductVariation> findByProductId(Long productId);

    @Query(value = "select email from user where id in " +
            "(select seller_user_id from product where id in " +
            "(select product_id from product_variation where quantity_available = 0))", nativeQuery = true)
    public List<String> getSellerEmailWithProductVariationQuantityNone();

    @Query(value = "select u.email, pv.metadata, p.name, p.brand, p.description from " +
            "user u inner join product p on u.id = p.seller_user_id " +
            "inner join product_variation pv on p.id = pv.product_id where  pv.quantity_available =0", nativeQuery = true)
    public List<Object[]> getDetailedProductAndEmailWithProductVariationNone();

    public List<ProductVariation> findAll();
}
