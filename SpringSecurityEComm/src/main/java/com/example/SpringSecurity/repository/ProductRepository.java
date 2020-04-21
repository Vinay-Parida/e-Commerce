package com.example.SpringSecurity.repository;

import com.example.SpringSecurity.entity.products.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends CrudRepository<Product, Long> {

    @Query(value = "select * from product where brand =:Brand and category_id =:CategoryId and seller_user_id =:SellerId and name =:Name",nativeQuery = true)
    public Product getUniqueProduct(@Param("Brand")String brand, @Param("CategoryId")Long categoryId, @Param("SellerId")Long sellerId, @Param("Name")String name);

    @Query(value = "select * from product where category_id=:categoryId",nativeQuery = true)
    public List<Product> getProduct(@Param("categoryId")Long categoryId);

    @Query(value = "select * from product where seller_user_id=:sellerId and is_deleted=false",nativeQuery = true)
    public List<Product> getAllProduct(@Param("sellerId") Long sellerId, Pageable pageable);

    List<Product>  findByCategoryId(Long categoryId, Pageable pageable);

}

