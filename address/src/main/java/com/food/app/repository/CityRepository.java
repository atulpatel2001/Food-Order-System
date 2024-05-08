package com.food.app.repository;

import com.food.app.model.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CityRepository extends JpaRepository<City,Long> {

    @Query("SELECT DISTINCT c FROM City c WHERE c.enable = true order by c.id desc")
    Optional<List<City>> findDistinctFirstByIdAndEnableTrue();


    Optional<City> findByName(String name);
}
