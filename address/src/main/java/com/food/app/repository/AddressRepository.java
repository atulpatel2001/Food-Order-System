package com.food.app.repository;

import com.food.app.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address,Long> {
    @Query("SELECT DISTINCT a FROM Address a WHERE a.enable = true order by a.id desc")
    Optional<List<Address>> findDistinctFirstByIdAndEnableTrue();
    @Query("SELECT a FROM Address a WHERE a.enable = true and a.id =:addressId")
    Optional<Address> findAddressById(Long addressId);
    @Query("SELECT a FROM Address a WHERE a.enable = true and a.areaId =:areaId")
    Optional<List<Address>> findAddressByAreaId(Long areaId);

}
