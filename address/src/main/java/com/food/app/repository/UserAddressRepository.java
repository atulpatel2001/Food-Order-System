package com.food.app.repository;

import com.food.app.model.Address;
import com.food.app.model.UserAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserAddressRepository extends JpaRepository<UserAddress,Long> {
    @Query("SELECT DISTINCT a FROM UserAddress a WHERE a.enable = true order by a.id desc")
    Optional<List<UserAddress>> findDistinctFirstByIdAndEnableTrue();
    @Query("SELECT a FROM UserAddress a WHERE a.enable = true and a.userId =:userId")
    Optional<List<UserAddress>> findAddressByUserId(String  userId);

    Optional<UserAddress> findByAddressIdAndUserId(Long addressId,String userId);

}
