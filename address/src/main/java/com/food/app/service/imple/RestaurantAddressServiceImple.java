package com.food.app.service.imple;

import com.food.app.dto.*;
import com.food.app.exception.AllDataNotFoundException;
import com.food.app.exception.ResourceNotFoundException;
import com.food.app.mapper.AddressMapper;
import com.food.app.mapper.RestaurantAddressMapper;
import com.food.app.model.Address;
import com.food.app.model.RestaurantAddress;
import com.food.app.repository.RestaurantAddressRepository;
import com.food.app.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RestaurantAddressServiceImple implements RestaurantAddressService {

    @Autowired
    private RestaurantAddressRepository restaurantAddressRepository;

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
     * purpose of this function is get Restaurant Address By RestaurantId
     * @param restaurantId |
     * @return  List<Object>
     */
    @Override
    public List<AddressDataDto> getRestaurantAddressByRestaurantId(String restaurantId) {

        List<AddressDataDto> addressDataDtoList = null;
        Optional<List<RestaurantAddress>> restaurantAddresses = this.restaurantAddressRepository.findAddressByRestaurantId(restaurantId);
        if (restaurantAddresses.isPresent()) {
            addressDataDtoList = new ArrayList<>();

            for (RestaurantAddress restaurantAddress :
                    restaurantAddresses.get()) {
                AddressDataDto addressDataDto = mapToAddressDataDto(restaurantAddress);
                addressDataDtoList.add(addressDataDto);
            }
        } else {
            throw new AllDataNotFoundException("No Address Available Of this Restaurant");
        }
        return addressDataDtoList;
    }



    /**
     * purpose of this function is get All  Restaurant Address
     * @return  List<Object>
     */

    @Override
    public List<AddressDataDto> getAllRestaurantAddress() {
        List<AddressDataDto> addressDataDtos = null;
        Optional<List<RestaurantAddress>> restaurantAddresses = this.restaurantAddressRepository.findDistinctFirstByIdAndEnableTrue();
        if (restaurantAddresses.isPresent()) {
            addressDataDtos = new ArrayList<>();

            for (RestaurantAddress restaurantAddress :
                    restaurantAddresses.get()) {
                AddressDataDto addressDataDto = mapToAddressDataDto(restaurantAddress);
                addressDataDtos.add(addressDataDto);
            }
        } else {
            throw new AllDataNotFoundException("No Address Available In this System");
        }
        return addressDataDtos;
    }



    /**
     * purpose of this function is delete Restaurant Address By Restaurant restaurantId And AddressId
     * @param restaurantId | AddressId
     *
     */
    @Override
    public void deleteRestaurantAddress(Long id,String restaurantId) {

         Optional<RestaurantAddress> restaurantAddressOptional = this.restaurantAddressRepository.findByAddressIdAndRestaurantId(id, restaurantId);

        if(restaurantAddressOptional.isPresent()){
             RestaurantAddress restaurantAddress = restaurantAddressOptional.get();
             restaurantAddress.setEnable(false);
             this.restaurantAddressRepository.save(restaurantAddress);
        }
        else {
            throw new ResourceNotFoundException("Address","Restaurant Id And Address Id :-",restaurantId+" And "+id);
        }

    }


    /**
     * purpose of this function is add Restaurant Address in database
     * @param restaurantAddress |
     * @Return Void |
     */
    @Override
    public void addRestaurantAddress(RestaurantAddressDto restaurantAddress) throws Exception {
        //check restaurant exists or not
        try {
            AddressDto addressDto = RestaurantAddressMapper.mapToAddressDto(restaurantAddress, new AddressDto());
            Address address = AddressMapper.mapToAddress(addressDto, new Address());
            address.setEnable(true);
            AddressDto save = this.addressService.addAddress(AddressMapper.mapToAddressDto(address, new AddressDto()));
            RestaurantAddress restaurantAddress1 = RestaurantAddressMapper.mapToRestaurantAddress(restaurantAddress, new RestaurantAddress());
            restaurantAddress1.setAddressId(save.getId());
            restaurantAddress1.setEnable(true);
            this.restaurantAddressRepository.save(restaurantAddress1);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }


    /**
     * purpose of this function is Update Restaurant Address in database
     * @param restaurantAddress |
     * @Return boolean |
     */
    @Override
    public boolean updateRestaurantAddress(RestaurantAddressDto restaurantAddress) {
        boolean f=false;
         Optional<RestaurantAddress> restaurantAddressOptional = this.restaurantAddressRepository.findByAddressIdAndRestaurantId(restaurantAddress.getId(), restaurantAddress.getRestaurantId());
         if(restaurantAddressOptional.isPresent()){
             // RestaurantAddress oldRestaurantAddress = restaurantAddressOptional.get();
              AddressDto addressDto = this.addressService.getAddressById(restaurantAddress.getId());
               addressDto.setAddress1(restaurantAddress.getAddress1());
               addressDto.setAddress2(restaurantAddress.getAddress2());
               addressDto.setAreaId(restaurantAddress.getAreaId());
                this.addressService.updateAddress(addressDto);
               f=true;
         }
         else {
             f=false;
             throw new ResourceNotFoundException("Address","Restaurant Id And Address Id :-",restaurantAddress.getRestaurantId()+" And "+restaurantAddress.getId());

         }
        return f;
    }



    /**
     * purpose of this function is map Restaurant Address to AddressDataDto
     * @param restaurantAddress |
     * @Return AddressDataDto |
     */
     AddressDataDto mapToAddressDataDto(RestaurantAddress restaurantAddress) {
        AddressDataDto addressDataDto = new AddressDataDto();
        addressDataDto.setPersonId(restaurantAddress.getRestaurantId());

        AddressDto address = null;
        if (restaurantAddress.getAddressId() != null) {
            address = this.addressService.getAddressById(restaurantAddress.getAddressId());
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
