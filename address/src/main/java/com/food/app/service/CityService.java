package com.food.app.service;

import com.food.app.dto.CityDto;
import com.food.app.model.City;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface CityService {


    CityDto getCityById(Long cityId);

    CityDto getCityByName(String name);

    List<CityDto> getAllCity();


    void deleteCity(Long id);


    CityDto addCity(CityDto city);

    boolean updateCity(CityDto city);
}
