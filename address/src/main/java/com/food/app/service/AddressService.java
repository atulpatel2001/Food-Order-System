package com.food.app.service;

import com.food.app.dto.AddressDto;

import java.util.List;

public interface AddressService {


    AddressDto getAddressById(Long addressId);
    List<AddressDto> getAddressByAreaId(Long cityId);

    List<AddressDto> getAllAddress();

    void deleteAddress(Long id);

    AddressDto addAddress(AddressDto address);

    boolean updateAddress(AddressDto address);
}
