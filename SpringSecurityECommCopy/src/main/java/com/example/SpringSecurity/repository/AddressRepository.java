package com.example.SpringSecurity.repository;

import com.example.SpringSecurity.entity.users.Address;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AddressRepository extends CrudRepository<Address, Long> {

    @Query(value = "select * from address where user_id =:Id", nativeQuery = true)
    public List<Address> getAddress(@Param("Id") Long id);

    @Query(value = "select * from address where user_id =:UserId and id =:AddressId", nativeQuery = true)
    public Address getAddressByUserAndAddressId(@Param("UserId") Long userId, @Param("AddressId") Long id);

    @Query(value = "select * from address where user_id =:UserId", nativeQuery = true)
    public Address getAddressByUserId(@Param("UserId") Long userId);
}
