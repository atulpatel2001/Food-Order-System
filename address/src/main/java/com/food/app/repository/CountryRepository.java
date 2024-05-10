package com.food.app.repository;

import com.food.app.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CountryRepository extends JpaRepository<Country,Long> {

    @Query("SELECT DISTINCT c FROM Country c WHERE c.enable = true order by c.id desc")
    Optional<List<Country>> findDistinctFirstByIdAndEnableTrue();


    @Query("SELECT c FROM Country c WHERE c.enable = true and c.id =:countryId")
    Optional<Country> findCountryById(Long countryId);
    Optional<Country> findByName(String name);
}
