package com.example.springsecurity.repository.criteriaqueryrepository;

import com.example.springsecurity.entity.products.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class CriteriaQueryRepository {

    @Autowired
    EntityManager em;

    public List<Category> categoryList(){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Category> cq = cb.createQuery(Category.class);

        //This will do select * from Category
        Root<Category> categoryRoot = cq.from(Category.class);
        cq.select(categoryRoot);

        TypedQuery<Category> query = em.createQuery(cq);
        return query.getResultList();
    }
}
