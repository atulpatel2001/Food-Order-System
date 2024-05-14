package com.food.app.mapper;

import com.food.app.dto.AddressDto;
import com.food.app.dto.DeliveryAgentAddressDto;
import com.food.app.model.DeliveryAgentAddress;

public class DeliveryAgentAddressMapper {

    public static DeliveryAgentAddress mapToDeliveryAgentAddress(DeliveryAgentAddressDto deliveryAgentAddressDto, DeliveryAgentAddress deliveryAgentAddress) {
        deliveryAgentAddress.setAddressId(deliveryAgentAddress.getAddressId());
        deliveryAgentAddress.setId(deliveryAgentAddressDto.getId());
        deliveryAgentAddress.setDeliveryAgentId(deliveryAgentAddressDto.getDeliveryAgentId());
        return deliveryAgentAddress;
    }


    public static AddressDto mapToAddressDto(DeliveryAgentAddressDto deliveryAgentAddressDto, AddressDto addressDto) {
        addressDto.setAddress1(deliveryAgentAddressDto.getAddress1());
        addressDto.setAddress2(deliveryAgentAddressDto.getAddress2());
        addressDto.setAreaId(deliveryAgentAddressDto.getAreaId());
        return addressDto;
    }

}
