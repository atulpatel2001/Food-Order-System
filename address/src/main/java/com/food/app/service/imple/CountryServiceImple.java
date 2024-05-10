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
            throw new ResourceNotFoundException("Country", "Country id", CountryId.toString());
        }

        return CountryMapper.mapToCountryDto(CountryOptional.get(), new CountryDto());

    }

    @Override
    public CountryDto getCountryByName(String name) {
        Optional<Country> CountryOptional = this.CountryRepository.findByName(name.toLowerCase());
        if (CountryOptional.isEmpty()) {
            throw new ResourceNotFoundException("Country", "Country name", name);
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

        optionalCountries.get().forEach(Country -> CountryDtos.add(CountryMapper.mapToCountryDto(Country, new CountryDto())));

        return CountryDtos;
    }

    @Override
    public void deleteCountry(Long id) {
        Optional<Country> optionalCountry = this.CountryRepository.findById(id);

        if (optionalCountry.isEmpty()) {
            throw new ResourceNotFoundException("Country", "Country id", id.toString());
        }

        Country Country = optionalCountry.get();

        Country.setEnable(false);
        CountryRepository.save(Country);

    }

    @Override
    public CountryDto addCountry(CountryDto Country) {
        Optional<Country> optionalCountry = this.CountryRepository.findByName(Country.getName().trim().toLowerCase());
        if (optionalCountry.isPresent()) {
            throw new AlreadyExistsException("This Country is Already register With This Name");
        }
        Country Country1 = CountryMapper.mapToCountry(Country, new Country());
        Country1.setName(Country1.getName().trim().toLowerCase());

        Country1.setEnable(true);
        return CountryMapper.mapToCountryDto(this.CountryRepository.save(Country1), new CountryDto());

    }

    @Override
    public boolean updateCountry(CountryDto Country) {
        boolean f = false;
        Optional<Country> optionalCountry = this.CountryRepository.findById(Country.getId());
        if (optionalCountry.isEmpty()) {
            f = false;
            throw new ResourceNotFoundException("Country", "id", Country.getId().toString());
        } else if (this.CountryRepository.findByName(Country.getName().trim().toLowerCase()).isPresent()) {
            f = false;
            throw new AlreadyExistsException("Country is Already register With This Name");
        } else {
            Country oldCountry = optionalCountry.get();
            oldCountry.setName(Country.getName().trim().toLowerCase());
            this.CountryRepository.save(oldCountry);
            f = true;
        }
        return f;

    }
}
