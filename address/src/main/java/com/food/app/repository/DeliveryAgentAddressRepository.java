package com.food.app.repository;

import com.food.app.model.DeliveryAgentAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface DeliveryAgentAddressRepository extends JpaRepository<DeliveryAgentAddress,Long> {
    @Query("SELECT DISTINCT d FROM DeliveryAgentAddress d WHERE d.enable = true order by d.id desc")
    Optional<List<DeliveryAgentAddress>> findDistinctFirstByIdAndEnableTrue();
    @Query("SELECT d FROM DeliveryAgentAddress d WHERE d.enable = true and d.deliveryAgentId =:deliveryAgentId")
    Optional<List<DeliveryAgentAddress>> findAddressByDeliveryAgentId(String  deliveryAgentId);

    Optional<DeliveryAgentAddress> findByAddressIdAndDeliveryAgentId(Long addressId,String deliveryAgentId);

}
