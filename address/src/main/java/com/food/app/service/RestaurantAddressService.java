package com.food.app.service;

import com.food.app.dto.AddressDataDto;
import com.food.app.dto.RestaurantAddressDto;

import java.util.List;

public interface RestaurantAddressService {
    List<AddressDataDto> getRestaurantAddressByRestaurantId(String restaurantId);

    List<AddressDataDto> getAllRestaurantAddress();

    void deleteRestaurantAddress(Long addressId,String restaurantId);


    void addRestaurantAddress(RestaurantAddressDto restaurantAddress) throws Exception;

    boolean updateRestaurantAddress(RestaurantAddressDto restaurantAddress);
}
