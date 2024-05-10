package com.food.app.service;

import com.food.app.dto.DistrictDto;
import com.food.app.model.District;

import java.util.List;
import java.util.Optional;

public interface DistrictService {


    DistrictDto getDistrictById(Long districtId);
    List<DistrictDto>  getDistrictByStateId(Long stateId);
    DistrictDto getDistrictByName(String name);

    List<DistrictDto> getAllDistrict();


    void deleteDistrict(Long id);


    DistrictDto addDistrict(DistrictDto district);

    boolean updateDistrict(DistrictDto district);
}
