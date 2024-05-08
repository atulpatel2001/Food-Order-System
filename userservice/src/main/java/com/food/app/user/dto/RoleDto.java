package com.food.app.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Schema(name = "Role", description = "This Schema Holds Role Information")
public class RoleDto {

    @Schema(description = "Role Name ", example = "admin")
    @NotEmpty(message = "Role Name can not be null or empty")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Only character values are allowed")
    private String name;
}
