package com.food.app.service;
import com.food.app.dto.CountryDto;
import java.util.List;
public interface CountryService {


    CountryDto getCountryById(Long CountryId);

    CountryDto getCountryByName(String name);

    List<CountryDto> getAllCountry();


    void deleteCountry(Long id);


    CountryDto addCountry(CountryDto Country);

    boolean updateCountry(CountryDto Country);
}
