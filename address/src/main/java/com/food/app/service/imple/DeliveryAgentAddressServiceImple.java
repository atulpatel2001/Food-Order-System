package com.food.app.service.imple;

import com.food.app.dto.*;
import com.food.app.exception.AllDataNotFoundException;
import com.food.app.exception.ResourceNotFoundException;
import com.food.app.mapper.AddressMapper;
import com.food.app.mapper.DeliveryAgentAddressMapper;
import com.food.app.model.Address;
import com.food.app.model.DeliveryAgentAddress;
import com.food.app.repository.DeliveryAgentAddressRepository;
import com.food.app.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DeliveryAgentAddressServiceImple implements DeliveryAgentAddressService {

    @Autowired
    private DeliveryAgentAddressRepository deliveryAgentAddressRepository;

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
     * purpose of this function is get DeliveryAgent Address By DeliveryAgentId
     * @param deliveryAgentId |
     * @return  List<Object>
     */
    @Override
    public List<AddressDataDto> getDeliveryAgentAddressByDeliveryAgentId(String deliveryAgentId) {

        List<AddressDataDto> addressDataDtoList = null;
        Optional<List<DeliveryAgentAddress>> deliveryAgentAddresses = this.deliveryAgentAddressRepository.findAddressByDeliveryAgentId(deliveryAgentId);
        if (deliveryAgentAddresses.isPresent()) {
            addressDataDtoList = new ArrayList<>();

            for (DeliveryAgentAddress deliveryAgentAddress :
                    deliveryAgentAddresses.get()) {
                AddressDataDto addressDataDto = mapToAddressDataDto(deliveryAgentAddress);
                addressDataDtoList.add(addressDataDto);
            }
        } else {
            throw new AllDataNotFoundException("No Address Available Of this DeliveryAgent");
        }
        return addressDataDtoList;
    }



    /**
     * purpose of this function is get All  DeliveryAgent Address
     * @return  List<Object>
     */

    @Override
    public List<AddressDataDto> getAllDeliveryAgentAddress() {
        List<AddressDataDto> addressDataDtos = null;
        Optional<List<DeliveryAgentAddress>> deliveryAgentAddresses = this.deliveryAgentAddressRepository.findDistinctFirstByIdAndEnableTrue();
        if (deliveryAgentAddresses.isPresent()) {
            addressDataDtos = new ArrayList<>();

            for (DeliveryAgentAddress deliveryAgentAddress :
                    deliveryAgentAddresses.get()) {
                AddressDataDto addressDataDto = mapToAddressDataDto(deliveryAgentAddress);
                addressDataDtos.add(addressDataDto);
            }
        } else {
            throw new AllDataNotFoundException("No Address Available In this System");
        }
        return addressDataDtos;
    }



    /**
     * purpose of this function is delete DeliveryAgent Address By DeliveryAgent deliveryAgentId And AddressId
     * @param deliveryAgentId | AddressId
     *
     */
    @Override
    public void deleteDeliveryAgentAddress(Long id,String deliveryAgentId) {

         Optional<DeliveryAgentAddress> deliveryAgentAddressOptional = this.deliveryAgentAddressRepository.findByAddressIdAndDeliveryAgentId(id, deliveryAgentId);

        if(deliveryAgentAddressOptional.isPresent()){
             DeliveryAgentAddress deliveryAgentAddress = deliveryAgentAddressOptional.get();
             deliveryAgentAddress.setEnable(false);
             this.deliveryAgentAddressRepository.save(deliveryAgentAddress);
        }
        else {
            throw new ResourceNotFoundException("Address","DeliveryAgent Id And Address Id :-",deliveryAgentId+" And "+id);
        }

    }


    /**
     * purpose of this function is add DeliveryAgent Address in database
     * @param deliveryAgentAddress |
     * @Return Void |
     */
    @Override
    public void addDeliveryAgentAddress(DeliveryAgentAddressDto deliveryAgentAddress) throws Exception {
        //check deliveryAgent exists or not
        try {
            AddressDto addressDto = DeliveryAgentAddressMapper.mapToAddressDto(deliveryAgentAddress, new AddressDto());
            Address address = AddressMapper.mapToAddress(addressDto, new Address());
            address.setEnable(true);
            AddressDto save = this.addressService.addAddress(AddressMapper.mapToAddressDto(address, new AddressDto()));
            DeliveryAgentAddress deliveryAgentAddress1 = DeliveryAgentAddressMapper.mapToDeliveryAgentAddress(deliveryAgentAddress, new DeliveryAgentAddress());
            deliveryAgentAddress1.setAddressId(save.getId());
            deliveryAgentAddress1.setEnable(true);
            this.deliveryAgentAddressRepository.save(deliveryAgentAddress1);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }


    /**
     * purpose of this function is Update DeliveryAgent Address in database
     * @param deliveryAgentAddress |
     * @Return boolean |
     */
    @Override
    public boolean updateDeliveryAgentAddress(DeliveryAgentAddressDto deliveryAgentAddress) {
        boolean f=false;
         Optional<DeliveryAgentAddress> deliveryAgentAddressOptional = this.deliveryAgentAddressRepository.findByAddressIdAndDeliveryAgentId(deliveryAgentAddress.getId(), deliveryAgentAddress.getDeliveryAgentId());
         if(deliveryAgentAddressOptional.isPresent()){
             // DeliveryAgentAddress oldDeliveryAgentAddress = deliveryAgentAddressOptional.get();
              AddressDto addressDto = this.addressService.getAddressById(deliveryAgentAddress.getId());
               addressDto.setAddress1(deliveryAgentAddress.getAddress1());
               addressDto.setAddress2(deliveryAgentAddress.getAddress2());
               addressDto.setAreaId(deliveryAgentAddress.getAreaId());
                this.addressService.updateAddress(addressDto);
               f=true;
         }
         else {
             f=false;
             throw new ResourceNotFoundException("Address","DeliveryAgent Id And Address Id :-",deliveryAgentAddress.getDeliveryAgentId()+" And "+deliveryAgentAddress.getId());

         }
        return f;
    }



    /**
     * purpose of this function is map DeliveryAgent Address to AddressDataDto
     * @param deliveryAgentAddress |
     * @Return AddressDataDto |
     */
     AddressDataDto mapToAddressDataDto(DeliveryAgentAddress deliveryAgentAddress) {
        AddressDataDto addressDataDto = new AddressDataDto();
        addressDataDto.setPersonId(deliveryAgentAddress.getDeliveryAgentId());

        AddressDto address = null;
        if (deliveryAgentAddress.getAddressId() != null) {
            address = this.addressService.getAddressById(deliveryAgentAddress.getAddressId());
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
