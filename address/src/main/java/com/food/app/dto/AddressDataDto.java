package com.food.app.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "AddressDataDto", description = "This Schema Holds Address Info For display Information")
public class AddressDataDto {

    private String personId;
    private CountryDto countryDto;

    private StateDto stateDto;

    private DistrictDto districtDto;

    private CityDto cityDto;

    private AreaDto areaDto;
    private AddressDto addressDto;


}
