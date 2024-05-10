package com.food.app.service.imple;

import com.food.app.dto.DistrictDto;
import com.food.app.exception.AllDataNotFoundException;
import com.food.app.exception.AlreadyExistsException;
import com.food.app.exception.ResourceNotFoundException;
import com.food.app.mapper.DistrictMapper;
import com.food.app.model.District;
import com.food.app.repository.DistrictRepository;
import com.food.app.service.DistrictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DistrictServiceImple implements DistrictService {

    @Autowired
    private DistrictRepository districtRepository;

    @Override
    public DistrictDto getDistrictById(Long districtId) {

        Optional<District> districtOptional = this.districtRepository.findDistrictById(districtId);

        if (districtOptional.isEmpty()) {
            throw new ResourceNotFoundException("District", "District id", districtId.toString());
        }

        return DistrictMapper.mapToDistrictDto(districtOptional.get(), new DistrictDto());

    }

    @Override
    public List<DistrictDto> getDistrictByStateId(Long stateId) {
        Optional<List<District>> optionalDistricts = this.districtRepository.findDistrictByStateId(stateId);

        if (optionalDistricts.isEmpty()) {
            throw new AllDataNotFoundException("No City Available From This State in system");
        }

        List<DistrictDto> districtDtos = new ArrayList<>();

        optionalDistricts.get().forEach(District -> districtDtos.add(DistrictMapper.mapToDistrictDto(District, new DistrictDto())));

        return districtDtos;
    }
    

    @Override
    public DistrictDto getDistrictByName(String name) {
        Optional<District> DistrictOptional = this.districtRepository.findByName(name.toLowerCase());
        if (DistrictOptional.isEmpty()) {
            throw new ResourceNotFoundException("District", "District name", name);
        }
        return DistrictMapper.mapToDistrictDto(DistrictOptional.get(), new DistrictDto());
    }

    @Override
    public List<DistrictDto> getAllDistrict() {
        Optional<List<District>> optionalDistricts = this.districtRepository.findDistinctFirstByIdAndEnableTrue();

        if (optionalDistricts.isEmpty()) {
            throw new AllDataNotFoundException("in System not have any District");
        }

        List<DistrictDto> districtDtos = new ArrayList<>();

        optionalDistricts.get().forEach(District -> districtDtos.add(DistrictMapper.mapToDistrictDto(District, new DistrictDto())));

        return districtDtos;
    }

    @Override
    public void deleteDistrict(Long id) {
        Optional<District> optionalDistrict = this.districtRepository.findById(id);

        if (optionalDistrict.isEmpty()) {
            throw new ResourceNotFoundException("District", "District id", id.toString());
        }

        District district = optionalDistrict.get();

        district.setEnable(false);
        districtRepository.save(district);

    }

    @Override
    public DistrictDto addDistrict(DistrictDto district) {
        Optional<District> optionalDistrict = this.districtRepository.findByName(district.getName().trim().toLowerCase());
        if (optionalDistrict.isPresent()) {
            throw new AlreadyExistsException("This District is Already register With This Name :-"+district.getName());
        }
        District district1 = DistrictMapper.mapToDistrict(district, new District());
        district1.setName(district1.getName().trim().toLowerCase());
        district1.setEnable(true);
        return DistrictMapper.mapToDistrictDto(this.districtRepository.save(district1), new DistrictDto());

    }

    @Override
    public boolean updateDistrict(DistrictDto district) {
        boolean f = false;
        Optional<District> optionalDistrict = this.districtRepository.findById(district.getId());
        if (optionalDistrict.isEmpty()) {
            f = false;
            throw new ResourceNotFoundException("District", "id", district.getId().toString());
        } else if (this.districtRepository.findByName(district.getName().trim().toLowerCase()).isPresent()) {
            f = false;
            throw new AlreadyExistsException("District is Already register With This Name");
        } else {
            District oldDistrict = optionalDistrict.get();
            oldDistrict.setName(district.getName().trim().toLowerCase());
            oldDistrict.setStateId(district.getStateId()); ;
            this.districtRepository.save(oldDistrict);
            f = true;
        }
        return f;

    }
}
