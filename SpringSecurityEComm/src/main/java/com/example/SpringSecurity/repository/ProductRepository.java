package com.example.SpringSecurity.repository;

import com.example.SpringSecurity.entity.products.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends CrudRepository<Product, Long> {

    @Query(value = "select * from product where brand =:Brand and category_id =:CategoryId and seller_user_id =:SellerId and name =:Name",nativeQuery = true)
    public Product getUniqueProduct(@Param("Brand")String brand, @Param("CategoryId")Long categoryId, @Param("SellerId")Long sellerId, @Param("Name")String name);

}
