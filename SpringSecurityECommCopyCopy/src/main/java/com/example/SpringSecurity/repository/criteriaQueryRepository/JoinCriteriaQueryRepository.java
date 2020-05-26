package com.example.SpringSecurity.repository.criteriaQueryRepository;

import com.example.SpringSecurity.entity.products.Category;
import com.example.SpringSecurity.entity.products.CategoryMetadataField;
import com.example.SpringSecurity.entity.products.CategoryMetadataFieldValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class JoinCriteriaQueryRepository {

    @Autowired
    EntityManager em;

    public List<Object[]> categoryAndMetadataFieldListJoin(){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);

        Root<Category> categoryRoot = cq.from(Category.class);
        Root<CategoryMetadataFieldValue> categoryMetadataFieldValueRoot = cq.from(CategoryMetadataFieldValue.class);

        cq.multiselect(categoryRoot,categoryMetadataFieldValueRoot);
        TypedQuery<Object[]> query = em.createQuery(cq);

        return query.getResultList();
    }


    public List<Object[]> categoryAndMetadataFieldList(){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);

        Root<Category> categoryRoot = cq.from(Category.class);
        Root<CategoryMetadataFieldValue> categoryMetadataFieldValueRoot = cq.from(CategoryMetadataFieldValue.class);

        // If want to display every data of 2 tables but without DTO we can use mutliselect that returns an object
        cq.multiselect(categoryRoot,categoryMetadataFieldValueRoot).where(cb.equal(categoryRoot.get("id"), categoryMetadataFieldValueRoot.get("category")));

        TypedQuery<Object[]> query = em.createQuery(cq);

        return query.getResultList();
    }

    public List<Category> categoryListJoin(){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Category> cq= cb.createQuery(Category.class);

        Root<CategoryMetadataFieldValue> valueRoot = cq.from(CategoryMetadataFieldValue.class);
        Join<CategoryMetadataFieldValue, Category> valueCategoryJoin = valueRoot.join("category");

        //Basic Join
        cq.select(valueCategoryJoin);

        TypedQuery<Category> query = em.createQuery(cq);

        return query.getResultList();
    }

    public List<CategoryMetadataField> categoryAndValueListJoin(){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<CategoryMetadataField> cq= cb.createQuery(CategoryMetadataField.class);

        Root<CategoryMetadataField> valueRoot = cq.from(CategoryMetadataField.class);

        Join<CategoryMetadataFieldValue, CategoryMetadataField> valueCategoryJoin = valueRoot.join("categoryMetadataFieldValueList");

        // We don't have to equal the primary key and foreign key as it automatically does it
        cq.select(valueCategoryJoin);

        TypedQuery<CategoryMetadataField> query = em.createQuery(cq);

        return query.getResultList();
    }

    // Joining 3 tables
    public List<CategoryMetadataField> tripleJoin(){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<CategoryMetadataField> cq= cb.createQuery(CategoryMetadataField.class);

        Root<CategoryMetadataField> valueRoot = cq.from(CategoryMetadataField.class);
        Join<CategoryMetadataFieldValue, CategoryMetadataField> valueCategoryJoin = valueRoot.join("categoryMetadataFieldValueList");
        Join<Category, CategoryMetadataFieldValue> categoryJoin = valueRoot.join("categoryMetadataFieldValueList");

        List<Predicate> conditions = new ArrayList<>();

//       We can add more conditions with this: conditions.add(cb.equal(categoryJoin.get("categoryMetadataField"), (long)2));
//        conditions.add(cb.equal(valueCategoryJoin.get("category"),(long)1));

        TypedQuery<CategoryMetadataField> typedQuery = em.createQuery(cq
                .select(valueRoot)
                .where(conditions.toArray(new Predicate[] {}))
        );
        return typedQuery.getResultList();
    }

}
