package com.food.app.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.groups.Default;
import lombok.Data;


@Data
@Schema(name = "Address", description = "This Schema Holds Address Information")
public class AddressDto {
    public interface Create extends Default {}
    public interface Update extends Default {}


    @Schema(description = "Unique identifier for the Address", example = "1")
    @Null(groups = Create.class, message = "Id Not Require , Id is AutoGenerated")
    @NotNull(groups = Update.class, message = "ID is required for update")
    private Long id;

    @Schema(description = "Address Line One", example = "Near This and other")
    @NotEmpty(groups = {Default.class, Create.class, Update.class}, message = "Line1 name can not be null or empty")
    private String address1;


    @Schema(description = "Address Line One", example = "Near This and other")
    private String address2;

    @Schema(description = "Area Id For Address", example = "1")
    @NotNull(groups = {Default.class, AddressDto.Create.class, AddressDto.Update.class}, message = "Area Id is Must Be Require!")
    private Long areaId;





}
