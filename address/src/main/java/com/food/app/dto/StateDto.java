package com.food.app.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.groups.Default;
import lombok.Data;


@Data
@Schema(name = "State", description = "This Schema Holds State Information")
public class StateDto {
    public interface Create extends Default {}
    public interface Update extends Default {}


    @Schema(description = "Unique identifier for the State", example = "1")
    @Null(groups = Create.class, message = "Id Not Require , Id is AutoGenerated")
    @NotNull(groups = Update.class, message = "ID is required for update")
    private Long id;

    @Schema(description = "Name of the State", example = "india")
    @NotEmpty(groups = {Default.class, Create.class, Update.class}, message = "State name can not be null or empty")
    @Pattern(regexp = "^[a-zA-Z]+$", groups = {Default.class, Create.class, Update.class}, message = "Only character values are allowed")
    private String name;

    @Schema(description = "Country Id For State", example = "1")
    @NotNull(groups = {Default.class, Create.class, Update.class}, message = "Country Id is Must Be Require!")
    private Long countryId;

}