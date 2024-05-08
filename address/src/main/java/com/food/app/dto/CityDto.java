package com.food.app.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import jakarta.validation.groups.Default;
import lombok.Data;

@Data
@Schema(name = "City", description = "This Schema Holds City Information")
public class CityDto {
    public interface Create extends Default {}


    @Schema(description = "Unique identifier for the City", example = "1")
    @NegativeOrZero(groups = Default.class,message = "Not allow a negative , Zero Value!")
    @Null(groups = Create.class, message = "Id Not Require , Id is AutoGenerated")
    private Long id;



    @Schema(description = "Name of the city", example = "Ahmedabad")
    @NotEmpty(groups = {Default.class, Create.class}, message = "City name can not be null or empty")
    @Pattern(regexp = "^[a-zA-Z]+$", groups = {Default.class, Create.class}, message = "Only character values are allowed")
    private String name;
}