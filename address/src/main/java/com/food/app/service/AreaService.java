package com.food.app.service;

import com.food.app.dto.AreaDto;

import java.util.List;

public interface AreaService {


    AreaDto getAreaById(Long areaId);
    List<AreaDto> getAreaByCityId(Long cityId);
    List<AreaDto> getAreaByPinCode(String pinCode);
    AreaDto getAreaByName(String name);

    List<AreaDto> getAllArea();


    void deleteArea(Long id);


    AreaDto addArea(AreaDto area);

    boolean updateArea(AreaDto area);
}
