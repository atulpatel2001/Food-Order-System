package com.food.app.service.imple;

import com.food.app.dto.*;
import com.food.app.mapper.AddressMapper;
import com.food.app.mapper.UserAddressMapper;
import com.food.app.model.Address;
import com.food.app.model.UserAddress;
import com.food.app.repository.AddressRepository;
import com.food.app.repository.UserAddressRepository;
import com.food.app.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserAddressServiceImple implements UserAddressService {
    @Autowired
    private UserAddressRepository userAddressRepository;
    @Autowired
    private AddressService addressService;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private AreaService areaService;
    @Autowired
    private CityService cityService;

    @Autowired
    private CountryService countryService;

    @Autowired
    private DistrictService districtService;

    @Autowired
    private StateService stateService;


    @Override
    public UserAddressDto getUserAddressByUserId(Long userAddressId) {
        return null;
    }

    @Override
    public List<AddressDataDto> getAllUserAddress() {
        List<AddressDataDto> addressDataDtos = null;
            Optional<List<UserAddress>> userAddresses = this.userAddressRepository.findDistinctFirstByIdAndEnableTrue();
            if (userAddresses.isPresent()) {
                addressDataDtos = new ArrayList<>();

                for (UserAddress userAddress :
                        userAddresses.get()) {
                    AddressDataDto addressDataDto = mapToAddressDataDto(userAddress);
                    addressDataDtos.add(addressDataDto);
                }
            }
        return addressDataDtos;
    }

    @Override
    public void deleteUserAddress(Long id) {

    }

    @Override
    public void addUserAddress(UserAddressDto userAddress) throws Exception {
        //check user exsits or not

        try {
            AddressDto addressDto = UserAddressMapper.mapToAddressDto(userAddress, new AddressDto());
            Address address = AddressMapper.mapToAddress(addressDto, new Address());
            address.setEnable(true);
            Address save = this.addressRepository.save(address);
            UserAddress userAddress1 = UserAddressMapper.mapToUserAddress(userAddress, new UserAddress());
            userAddress1.setAddressId(save.getId());
            userAddress1.setEnable(true);
            this.userAddressRepository.save(userAddress1);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public boolean updateUserAddress(UserAddressDto userAddress) {
        return false;
    }

    private AddressDataDto mapToAddressDataDto(UserAddress userAddress) {
        AddressDataDto addressDataDto = new AddressDataDto();
         addressDataDto.setPersonId(userAddress.getUserId());
         AddressDto address = this.addressService.getAddressById(userAddress.getAddressId());
         AreaDto area = this.areaService.getAreaById(address.getAreaId());
         CityDto city = this.cityService.getCityById(area.getCityId());
         DistrictDto district = this.districtService.getDistrictById(city.getDistrictId());
         StateDto state = this.stateService.getStateById(district.getStateId());
         CountryDto country = this.countryService.getCountryById(state.getCountryId());
         addressDataDto.setAddressDto(address);
         addressDataDto.setAreaDto(area);
         addressDataDto.setCityDto(city);
         addressDataDto.setDistrictDto(district);
         addressDataDto.setStateDto(state);
         addressDataDto.setCountryDto(country);
        return addressDataDto;
    }

}
