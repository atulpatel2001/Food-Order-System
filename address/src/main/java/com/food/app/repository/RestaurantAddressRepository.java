package com.food.app.repository;

import com.food.app.model.RestaurantAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RestaurantAddressRepository extends JpaRepository<RestaurantAddress,Long> {
    @Query("SELECT DISTINCT r FROM RestaurantAddress r WHERE r.enable = true order by r.id desc")
    Optional<List<RestaurantAddress>> findDistinctFirstByIdAndEnableTrue();
    @Query("SELECT r FROM RestaurantAddress r WHERE r.enable = true and r.restaurantId =:restaurantId")
    Optional<List<RestaurantAddress>> findAddressByRestaurantId(String  restaurantId);

    Optional<RestaurantAddress> findByAddressIdAndRestaurantId(Long addressId,String restaurantId);

}
