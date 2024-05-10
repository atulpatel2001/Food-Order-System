package com.food.app.service.imple;

import com.food.app.dto.CountryDto;
import com.food.app.exception.AllDataNotFoundException;
import com.food.app.exception.AlreadyExistsException;
import com.food.app.exception.ResourceNotFoundException;

import com.food.app.mapper.CountryMapper;
import com.food.app.model.Country;

import com.food.app.repository.CountryRepository;
import com.food.app.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CountryServiceImple implements CountryService {

    @Autowired
    private CountryRepository CountryRepository;

    @Override
    public CountryDto getCountryById(Long CountryId) {

        Optional<Country> CountryOptional = this.CountryRepository.findCountryById(CountryId);

        if (CountryOptional.isEmpty()) {
            throw new ResourceNotFoundException("country", "country id", CountryId.toString());
        }

        return CountryMapper.mapToCountryDto(CountryOptional.get(), new CountryDto());

    }

    @Override
    public CountryDto getCountryByName(String name) {
        Optional<Country> CountryOptional = this.CountryRepository.findByName(name.toLowerCase());
        if (CountryOptional.isEmpty()) {
            throw new ResourceNotFoundException("country", "country name", name);
        }
        return CountryMapper.mapToCountryDto(CountryOptional.get(), new CountryDto());
    }

    @Override
    public List<CountryDto> getAllCountry() {
        Optional<List<Country>> optionalCountries = this.CountryRepository.findDistinctFirstByIdAndEnableTrue();

        if (optionalCountries.isEmpty()) {
            throw new AllDataNotFoundException("Countries");
        }

        List<CountryDto> CountryDtos = new ArrayList<>();

        optionalCountries.get().forEach(country -> CountryDtos.add(CountryMapper.mapToCountryDto(country, new CountryDto())));

        return CountryDtos;
    }

    @Override
    public void deleteCountry(Long id) {
        Optional<Country> optionalCountry = this.CountryRepository.findById(id);

        if (optionalCountry.isEmpty()) {
            throw new ResourceNotFoundException("country", "country id", id.toString());
        }

        Country country = optionalCountry.get();

        country.setEnable(false);
        CountryRepository.save(country);

    }

    @Override
    public CountryDto addCountry(CountryDto country) {
        Optional<Country> optionalCountry = this.CountryRepository.findByName(country.getName().trim().toLowerCase());
        if (optionalCountry.isPresent()) {
            throw new AlreadyExistsException("This country is Already register With This Name "+country.getName());
        }
        Country country1 = CountryMapper.mapToCountry(country, new Country());
        country1.setName(country1.getName().trim().toLowerCase());

        country1.setEnable(true);
        return CountryMapper.mapToCountryDto(this.CountryRepository.save(country1), new CountryDto());

    }

    @Override
    public boolean updateCountry(CountryDto country) {
        boolean f = false;
        Optional<Country> optionalCountry = this.CountryRepository.findById(country.getId());
        if (optionalCountry.isEmpty()) {
            f = false;
            throw new ResourceNotFoundException("country", "id", country.getId().toString());
        } else if (this.CountryRepository.findByName(country.getName().trim().toLowerCase()).isPresent()) {
            f = false;
            throw new AlreadyExistsException("country is Already register With This Name");
        } else {
            Country oldCountry = optionalCountry.get();
            oldCountry.setName(country.getName().trim().toLowerCase());
            this.CountryRepository.save(oldCountry);
            f = true;
        }
        return f;

    }
}
