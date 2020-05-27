package com.example.springsecurity.repository;

import com.example.springsecurity.entity.products.CategoryMetadataFieldValue;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryMetadataFieldValueRepository extends CrudRepository<CategoryMetadataFieldValue, Long> {

    @Query(value = "select * from category_metadata_field_value where category_id =:CategoryId and category_metadata_field_id =:CategoryMetadataFieldId", nativeQuery = true)
    CategoryMetadataFieldValue findByCategoryAndMetadataField(@Param("CategoryId") Long categoryId,
                                                              @Param("CategoryMetadataFieldId") Long fieldId);

    @Query(value = "select f.name from category_metadata_field f inner join category_metadata_field_value v on f.id =v.category_metadata_field_id where v.category_id =:categoryId",nativeQuery = true)
    public List<Object> getMetadataFieldName(@Param("categoryId") Long categoryId);

    @Query(value = "select value from category_metadata_field_value where category_id =:CategoryId and category_metadata_field_id =:FieldId",nativeQuery = true)
    public List<Object> getMetadataFieldValuesByCategoryAndFieldId(@Param("CategoryId")Long categoryId, @Param("FieldId")Long fieldId);
}
