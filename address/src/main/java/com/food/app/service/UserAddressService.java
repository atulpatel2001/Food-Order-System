package com.food.app.service;

import com.food.app.dto.AddressDataDto;
import com.food.app.dto.UserAddressDto;

import java.util.List;

public interface UserAddressService {
    UserAddressDto getUserAddressByUserId(Long userAddressId);

    List<AddressDataDto> getAllUserAddress();

    void deleteUserAddress(Long id);

    void addUserAddress(UserAddressDto userAddress) throws Exception;

    boolean updateUserAddress(UserAddressDto userAddress);
}
