package com.food.app.repository;

import com.food.app.model.Area;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AreaRepository extends JpaRepository<Area,Long> {
    @Query("SELECT DISTINCT a FROM Area a WHERE a.enable = true order by a.id desc")
    Optional<List<Area>> findDistinctFirstByIdAndEnableTrue();
    @Query("SELECT a FROM Area a WHERE a.enable = true and a.id =:areaId")
    Optional<Area> findAreaById(Long areaId);
    @Query("SELECT a FROM Area a WHERE a.enable = true and a.cityId =:cityId")
    Optional<List<Area>> findAreaByCityId(Long cityId);

    @Query("SELECT a FROM Area a WHERE a.enable = true and a.pinCode =:pinCode")
    Optional<List<Area>> findAreaByPinCode(String pinCode);

    Optional<Area> findByName(String name);
}
