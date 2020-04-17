package com.example.SpringSecurity.repository;

import com.example.SpringSecurity.entity.users.User;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {

    User findByEmail(String username);

    User getUserById(Long id);

    @Modifying
    @Transactional
    @Query(value = "update user set password = :Password where id = :Id", nativeQuery = true)
    public void updatePassword(@Param("Password") String password, @Param("Id") Long id);

    // /admin/customer
    @Query(value = "select id, first_name, middle_name, last_name, email, is_active from user where id in (select id from customer)", nativeQuery = true)
    public List<Object[]> getCustomerDetails(PageRequest pageable);

//    @Query(value = "select u.id, u.first_name, u.middle_name, u.last_name, u.email, u.is_active, s.company_name, s.company_contact from user u inner join seller s on u.id=s.id", nativeQuery = true)
//    public List<Object[]> getSellerDetails(PageRequest pageable);

    @Query(value = "select u.id, u.first_name, u.middle_name, u.last_name, u.email, u.is_active, s.company_name, s.company_contact,a.address_line, a.city, a.country, a.state, a.zip_code from user u inner join seller s on u.id=s.id inner join address a on a.user_id = s.id", nativeQuery = true)
    public List<Object[]> getSellerDetails(PageRequest pageable);



}
