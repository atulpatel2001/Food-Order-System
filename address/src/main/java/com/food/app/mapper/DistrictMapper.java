package com.food.app.mapper;


import com.food.app.dto.DistrictDto;
import com.food.app.model.District;

public class DistrictMapper {


    public static District mapToDistrict(DistrictDto stateDto, District district){
        district.setName(stateDto.getName());
        district.setId(stateDto.getId());
        district.setStateId(stateDto.getStateId());
        return district;
    }

    public  static DistrictDto mapToDistrictDto(District district,DistrictDto stateDto){
        stateDto.setId(district.getId());
        stateDto.setName(district.getName());
        stateDto.setStateId(district.getStateId());
        return stateDto;
    }
}
