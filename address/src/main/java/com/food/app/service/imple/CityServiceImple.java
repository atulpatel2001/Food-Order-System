package com.food.app.service.imple;

import com.food.app.dto.CityDto;
import com.food.app.exception.AllDataNotFoundException;
import com.food.app.exception.AlreadyExistsException;
import com.food.app.exception.ResourceNotFoundException;
import com.food.app.mapper.CityMapper;
import com.food.app.model.City;
import com.food.app.repository.CityRepository;
import com.food.app.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CityServiceImple implements CityService {

    @Autowired
    private CityRepository cityRepository;

    @Override
    public CityDto getCityById(Long cityId) {

        Optional<City> cityOptional = this.cityRepository.findById(cityId);

        if (cityOptional.isEmpty()) {
            throw new ResourceNotFoundException("City", "city id", cityId.toString());
        }

        return CityMapper.mapToCityDto(cityOptional.get(), new CityDto());

    }

    @Override
    public CityDto getCityByName(String name) {
        Optional<City> cityOptional = this.cityRepository.findByName(name.toLowerCase());
        if (cityOptional.isEmpty()) {
            throw new ResourceNotFoundException("City", "city name", name);
        }
        return CityMapper.mapToCityDto(cityOptional.get(), new CityDto());
    }

    @Override
    public List<CityDto> getAllCity() {
        Optional<List<City>> optionalCities = this.cityRepository.findDistinctFirstByIdAndEnableTrue();

        if (optionalCities.isEmpty()) {
            throw new AllDataNotFoundException("Cities");
        }

        List<CityDto> cityDtos = new ArrayList<>();

        optionalCities.get().forEach(city -> cityDtos.add(CityMapper.mapToCityDto(city, new CityDto())));

        return cityDtos;
    }

    @Override
    public void deleteCity(Long id) {
        Optional<City> optionalCity = this.cityRepository.findById(id);

        if (optionalCity.isEmpty()) {
            throw new ResourceNotFoundException("City", "city id", id.toString());
        }

        City city = optionalCity.get();

        city.setEnable(false);
        cityRepository.save(city);

    }

    @Override
    public CityDto addCity(CityDto city) {
        Optional<City> optionalCity = this.cityRepository.findByName(city.getName().trim().toLowerCase());
        if (optionalCity.isPresent()) {
            throw new AlreadyExistsException("This City is Already register With This Name");
        }
        City city1 = CityMapper.mapToCity(city, new City());
        city1.setName(city1.getName().trim().toLowerCase());

        city1.setEnable(true);
        return CityMapper.mapToCityDto(this.cityRepository.save(city1), new CityDto());

    }

    @Override
    public boolean updateCity(CityDto city) {
        boolean f = false;
        Optional<City> optionalCity = this.cityRepository.findById(city.getId());
        if (optionalCity.isEmpty()) {
            f = false;
            throw new ResourceNotFoundException("City", "id", city.getId().toString());
        } else if (this.cityRepository.findByName(city.getName().trim().toLowerCase()).isPresent()) {
            f = false;
            throw new AlreadyExistsException("City is Already register With This Name");
        } else {
            City oldCity = optionalCity.get();
            oldCity.setName(city.getName().trim().toLowerCase());
            this.cityRepository.save(oldCity);
            f = true;
        }
        return f;

    }
}
