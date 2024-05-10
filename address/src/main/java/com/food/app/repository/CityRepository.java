package com.food.app.repository;

import com.food.app.model.City;
import com.food.app.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CityRepository extends JpaRepository<City,Long> {

    @Query("SELECT DISTINCT c FROM City c WHERE c.enable = true order by c.id desc")
    Optional<List<City>> findDistinctFirstByIdAndEnableTrue();

    @Query("SELECT c FROM City c WHERE c.enable = true and c.id =:cityId")
    Optional<City> findCityById(Long cityId);

    @Query("SELECT c FROM City c WHERE c.enable = true and c.districtId =:districtId")
    Optional<List<City>> findCityByDistrictId(Long districtId);
    Optional<City> findByName(String name);
}
