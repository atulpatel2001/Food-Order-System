package com.food.app.service;

import com.food.app.dto.AddressDataDto;
import com.food.app.dto.UserAddressDto;

import java.util.List;

public interface UserAddressService {
    List<AddressDataDto> getUserAddressByUserId(String userId);

    List<AddressDataDto> getAllUserAddress();

    void deleteUserAddress(Long addressId,String userId);


    void addUserAddress(UserAddressDto userAddress) throws Exception;

    boolean updateUserAddress(UserAddressDto userAddress);
}
