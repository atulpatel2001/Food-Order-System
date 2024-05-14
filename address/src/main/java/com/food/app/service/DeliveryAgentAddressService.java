package com.food.app.service;

import com.food.app.dto.AddressDataDto;
import com.food.app.dto.DeliveryAgentAddressDto;

import java.util.List;

public interface DeliveryAgentAddressService {
    List<AddressDataDto> getDeliveryAgentAddressByDeliveryAgentId(String deliveryAgentId);

    List<AddressDataDto> getAllDeliveryAgentAddress();

    void deleteDeliveryAgentAddress(Long addressId,String deliveryAgentId);


    void addDeliveryAgentAddress(DeliveryAgentAddressDto deliveryAgentAddress) throws Exception;

    boolean updateDeliveryAgentAddress(DeliveryAgentAddressDto deliveryAgentAddress);
}
