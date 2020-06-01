package com.example.springsecurity.repository;

import com.example.springsecurity.entity.products.Category;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryRepository extends CrudRepository<Category, Long> {
    Category findByName(String name);

    Category findByid(Long id);

    List<Category> findByParentId(Long id);

    List<Category> findAll(Pageable pageable);

    List<Category> findAll();

    @Query(value = "select * from category where id not in (select id from category where id in (select parent_id from category))", nativeQuery = true)
    List<Category> getLeafCategory();

    @Query(value = "select f.name,v.value from category_metadata_field_value v inner join category_metadata_field f on v.category_metadata_field_id =f.id where category_id= :Id",nativeQuery = true)
    List<Object[]> getMetadataByCategoryId(@Param("Id") long id);

    @Query(value = "select * from category where parent_id is null",nativeQuery = true)
    List<Category> getRootParent();

    @Query(value = "select f.name from category_metadata_field f inner join category_metadata_field_value  v  on f.id=v.category_metadata_field_id where category_id=:Id", nativeQuery = true)
    List<Object> getMetadataFieldsNameByCategoryId(@Param("Id") Long id);
}
