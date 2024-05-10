package com.food.app.mapper;

import com.food.app.dto.AreaDto;
import com.food.app.model.Area;

public class AreaMapper {


    public static Area mapToArea(AreaDto areaDto,Area area){
        area.setName(areaDto.getName());
        area.setId(areaDto.getId());
        area.setCityId(areaDto.getCityId());
        area.setPinCode(areaDto.getPinCode());
        return area;
    }

    public  static AreaDto mapToAreaDto(Area area,AreaDto areaDto){
        areaDto.setId(area.getId());
        areaDto.setName(area.getName());
        areaDto.setCityId(area.getCityId());
        areaDto.setPinCode(area.getPinCode());
        return areaDto;
    }
}
