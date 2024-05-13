package com.food.app.mapper;

import com.food.app.dto.AddressDto;
import com.food.app.dto.UserAddressDto;
import com.food.app.model.UserAddress;

public class UserAddressMapper {

    public static UserAddress mapToUserAddress(UserAddressDto userAddressDto, UserAddress userAddress) {
        userAddress.setAddressId(userAddress.getAddressId());
        userAddress.setId(userAddressDto.getId());
        userAddress.setUserId(userAddressDto.getUserId());
        return userAddress;
    }


    public static AddressDto mapToAddressDto(UserAddressDto userAddressDto, AddressDto addressDto) {
        addressDto.setAddress1(userAddressDto.getAddress1());
        addressDto.setAddress2(userAddressDto.getAddress2());
        addressDto.setAreaId(userAddressDto.getAreaId());
        return addressDto;
    }

}
