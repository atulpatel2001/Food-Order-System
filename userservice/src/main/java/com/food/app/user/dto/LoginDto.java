package com.food.app.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;

@Data
@Schema(name = "Login Info", description = "This Schema Holds Login Credential")
public class LoginDto {

    @Schema(description = "Username of the user", example = "atulpatel")
    @NotEmpty(message = "UserName Name can not be a null or empty")
    @Size(min = 3, message = "The length of the User Name should be  3")
    String userName;


    @Schema(description = "Password of the user", example = "Password@123")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$", message = "Password must contain at least 8 characters with at least one special character and One Uppercase Character.")
    String password;
}
