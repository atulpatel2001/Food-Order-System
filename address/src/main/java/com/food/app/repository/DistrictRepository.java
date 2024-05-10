package com.food.app.repository;

import com.food.app.model.District;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface DistrictRepository extends JpaRepository<District,Long> {
    @Query("SELECT DISTINCT d FROM District d WHERE d.enable = true order by d.id desc")
    Optional<List<District>> findDistinctFirstByIdAndEnableTrue();
    @Query("SELECT d FROM District d WHERE d.enable = true and d.id =:stateId")
    Optional<District> findDistrictById(Long stateId);
    @Query("SELECT d FROM District d WHERE d.enable = true and d.stateId =:stateId")
    Optional<List<District>> findDistrictByStateId(Long stateId);
    Optional<District> findByName(String name);
}
