package com.food.app.service.imple;

import com.food.app.dto.*;
import com.food.app.exception.AllDataNotFoundException;
import com.food.app.exception.ResourceNotFoundException;
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
    private AreaService areaService;
    @Autowired
    private CityService cityService;

    @Autowired
    private CountryService countryService;

    @Autowired
    private DistrictService districtService;

    @Autowired
    private StateService stateService;


    /**
     * purpose of this function is get User Address By User Id
     * @param userId |
     * @return  List<Object>
     */
    @Override
    public List<AddressDataDto> getUserAddressByUserId(String userId) {

        List<AddressDataDto> addressDataDtoList = null;
        Optional<List<UserAddress>> userAddresses = this.userAddressRepository.findAddressByUserId(userId);
        if (userAddresses.isPresent()) {
            addressDataDtoList = new ArrayList<>();

            for (UserAddress userAddress :
                    userAddresses.get()) {
                AddressDataDto addressDataDto = mapToAddressDataDto(userAddress);
                addressDataDtoList.add(addressDataDto);
            }
        } else {
            throw new AllDataNotFoundException("No Address Available Of this User");
        }
        return addressDataDtoList;
    }



    /**
     * purpose of this function is get All  User Address
     * @return  List<Object>
     */

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
        } else {
            throw new AllDataNotFoundException("No Address Available In this System");
        }
        return addressDataDtos;
    }



    /**
     * purpose of this function is delete User Address By User userId And AddressId
     * @param userId | AddressId
     *
     */
    @Override
    public void deleteUserAddress(Long id,String userId) {

         Optional<UserAddress> userAddressOptional = this.userAddressRepository.findByAddressIdAndUserId(id, userId);

        if(userAddressOptional.isPresent()){
             UserAddress userAddress = userAddressOptional.get();
             userAddress.setEnable(false);
             this.userAddressRepository.save(userAddress);
        }
        else {
            throw new ResourceNotFoundException("Address","User Id And Address Id :-",userId+" And "+id);
        }

    }


    /**
     * purpose of this function is add User Address in database
     * @param userAddress |
     * @Return Void |
     */
    @Override
    public void addUserAddress(UserAddressDto userAddress) throws Exception {
        //check user exists or not
        try {
            AddressDto addressDto = UserAddressMapper.mapToAddressDto(userAddress, new AddressDto());
            Address address = AddressMapper.mapToAddress(addressDto, new Address());
            address.setEnable(true);
            AddressDto save = this.addressService.addAddress(AddressMapper.mapToAddressDto(address, new AddressDto()));
            UserAddress userAddress1 = UserAddressMapper.mapToUserAddress(userAddress, new UserAddress());
            userAddress1.setAddressId(save.getId());
            userAddress1.setEnable(true);
            this.userAddressRepository.save(userAddress1);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }


    /**
     * purpose of this function is Update User Address in database
     * @param userAddress |
     * @Return boolean |
     */
    @Override
    public boolean updateUserAddress(UserAddressDto userAddress) {
         Optional<UserAddress> userAddressOptional = this.userAddressRepository.findByAddressIdAndUserId(userAddress.getId(), userAddress.getUserId());
        return false;
    }



    /**
     * purpose of this function is map User Address to AddressDataDto
     * @param userAddress |
     * @Return AddressDataDto |
     */
     AddressDataDto mapToAddressDataDto(UserAddress userAddress) {
        AddressDataDto addressDataDto = new AddressDataDto();
        addressDataDto.setPersonId(userAddress.getUserId());

        AddressDto address = null;
        if (userAddress.getAddressId() != null) {
            address = this.addressService.getAddressById(userAddress.getAddressId());
            if (address != null) {
                addressDataDto.setAddressDto(address);
                AreaDto area = null;
                if (address.getAreaId() != null) {
                    try {
                        area = this.areaService.getAreaById(address.getAreaId());
                        if (area != null) {
                            addressDataDto.setAreaDto(area);
                            CityDto city = null;
                            if (area.getCityId() != null) {
                                try {
                                    city = this.cityService.getCityById(area.getCityId());
                                    if (city != null) {
                                        addressDataDto.setCityDto(city);
                                        DistrictDto district = null;
                                        if (city.getDistrictId() != null) {
                                            district = this.districtService.getDistrictById(city.getDistrictId());
                                            if (district != null) {
                                                addressDataDto.setDistrictDto(district);
                                                StateDto state = null;
                                                if (district.getStateId() != null) {
                                                    state = this.stateService.getStateById(district.getStateId());
                                                    if (state != null) {
                                                        addressDataDto.setStateDto(state);
                                                        CountryDto country = null;
                                                        if (state.getCountryId() != null) {
                                                            country = this.countryService.getCountryById(state.getCountryId());
                                                            if (country != null) {
                                                                addressDataDto.setCountryDto(country);
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                } catch (ResourceNotFoundException ex) {
                                    addressDataDto.setCityDto(null);
                                }
                            }
                        }
                    } catch (ResourceNotFoundException ex) {
                        addressDataDto.setAreaDto(null);
                    }
                }
            }
        }
        return addressDataDto;
    }


}
