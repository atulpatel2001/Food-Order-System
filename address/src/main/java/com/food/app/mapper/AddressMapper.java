package com.food.app.mapper;

import com.food.app.dto.AddressDto;
import com.food.app.model.Address;

public class AddressMapper {


    public static Address mapToAddress(AddressDto addressDto,Address address){
        address.setLine1(addressDto.getAddress1());
        address.setId(addressDto.getId());
        address.setLine2(addressDto.getAddress2());
        address.setAreaId(addressDto.getAreaId());
        return address;
    }

    public  static AddressDto mapToAddressDto(Address address,AddressDto addressDto){
        addressDto.setId(address.getId());
        addressDto.setAddress1(address.getLine1());
        addressDto.setAddress2(address.getLine2());
        addressDto.setAreaId(address.getAreaId());
        return addressDto;
    }
}
