package com.food.app.service.imple;

import com.food.app.dto.AddressDto;
import com.food.app.exception.AllDataNotFoundException;
import com.food.app.exception.ResourceNotFoundException;
import com.food.app.mapper.AddressMapper;
import com.food.app.model.Address;
import com.food.app.repository.AddressRepository;
import com.food.app.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AddressServiceImple implements AddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Override
    public AddressDto getAddressById(Long addressId) {

        Optional<Address> addressOptional = this.addressRepository.findAddressById(addressId);

        if (addressOptional.isEmpty()) {
            throw new ResourceNotFoundException("Address", "Address id", addressId.toString());
        }

        return AddressMapper.mapToAddressDto(addressOptional.get(), new AddressDto());

    }

    @Override
    public List<AddressDto> getAddressByAreaId(Long areaId) {
        Optional<List<Address>> addressByCountryId = this.addressRepository.findAddressByAreaId(areaId);

        if (addressByCountryId.isEmpty()) {
            throw new AllDataNotFoundException("No Address Available From This Area in system");
        }

        List<AddressDto> addressDtos = new ArrayList<>();

        addressByCountryId.get().forEach(Address -> addressDtos.add(AddressMapper.mapToAddressDto(Address, new AddressDto())));

        return addressDtos;
    }


    @Override
    public List<AddressDto> getAllAddress() {
        Optional<List<Address>> optionalAddress = this.addressRepository.findDistinctFirstByIdAndEnableTrue();

        if (optionalAddress.isEmpty()) {
            throw new AllDataNotFoundException("in System not have any Address");
        }

        List<AddressDto> addressDtos = new ArrayList<>();

        optionalAddress.get().forEach(Address -> addressDtos.add(AddressMapper.mapToAddressDto(Address, new AddressDto())));

        return addressDtos;
    }

    @Override
    public void deleteAddress(Long id) {
        Optional<Address> optionalAddress = this.addressRepository.findById(id);

        if (optionalAddress.isEmpty()) {
            throw new ResourceNotFoundException("Address", "Address id", id.toString());
        }

        Address address = optionalAddress.get();

        address.setEnable(false);
        addressRepository.save(address);

    }

    @Override
    public AddressDto addAddress(AddressDto address) {
        Address address1 = AddressMapper.mapToAddress(address, new Address());
        address1.setEnable(true);
        return AddressMapper.mapToAddressDto(this.addressRepository.save(address1), new AddressDto());
    }

    @Override
    public boolean updateAddress(AddressDto address) {
        boolean f = false;
        Optional<Address> optionalAddress = this.addressRepository.findById(address.getId());
        if (optionalAddress.isEmpty()) {
            f = false;
            throw new ResourceNotFoundException("Address", "id", address.getId().toString());
        } else {
            Address oldAddress = optionalAddress.get();
            oldAddress.setAreaId(address.getAreaId());
            oldAddress.setLine1(address.getAddress1()); ;
            oldAddress.setLine2(address.getAddress2().trim());
            this.addressRepository.save(oldAddress);
            f = true;
        }
        return f;

    }
}
