package com.food.app.mapper;

import com.food.app.dto.AddressDto;
import com.food.app.dto.RestaurantAddressDto;
import com.food.app.model.RestaurantAddress;

public class RestaurantAddressMapper {

    public static RestaurantAddress mapToRestaurantAddress(RestaurantAddressDto restaurantAddressDto, RestaurantAddress restaurantAddress) {
        restaurantAddress.setAddressId(restaurantAddress.getAddressId());
        restaurantAddress.setId(restaurantAddressDto.getId());
        restaurantAddress.setRestaurantId(restaurantAddressDto.getRestaurantId());
        return restaurantAddress;
    }


    public static AddressDto mapToAddressDto(RestaurantAddressDto restaurantAddressDto, AddressDto addressDto) {
        addressDto.setAddress1(restaurantAddressDto.getAddress1());
        addressDto.setAddress2(restaurantAddressDto.getAddress2());
        addressDto.setAreaId(restaurantAddressDto.getAreaId());
        return addressDto;
    }

}
