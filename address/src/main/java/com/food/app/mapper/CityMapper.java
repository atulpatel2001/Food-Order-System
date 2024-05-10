package com.food.app.mapper;

import com.food.app.dto.CityDto;
import com.food.app.model.City;

public class CityMapper {


    public static City mapToCity(CityDto cityDto,City city){
        city.setName(cityDto.getName());
        city.setId(cityDto.getId());
        city.setDistrictId(cityDto.getDistrictId());
        return city;
    }

    public  static CityDto mapToCityDto(City city,CityDto cityDto){
        cityDto.setId(city.getId());
        cityDto.setName(city.getName());
        cityDto.setDistrictId(city.getDistrictId());
        return cityDto;
    }
}
