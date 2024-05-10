package com.food.app.mapper;

import com.food.app.dto.CountryDto;
import com.food.app.model.Country;

public class CountryMapper {


    public static Country mapToCountry(CountryDto CountryDto,Country Country){
        Country.setName(CountryDto.getName());
        Country.setId(CountryDto.getId());
        return Country;
    }

    public  static CountryDto mapToCountryDto(Country Country,CountryDto CountryDto){
        CountryDto.setId(Country.getId());
        CountryDto.setName(Country.getName());
        return CountryDto;
    }
}
