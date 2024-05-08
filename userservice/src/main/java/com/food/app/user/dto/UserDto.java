package com.food.app.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import jakarta.validation.groups.Default;
import lombok.Data;

@Data
@Schema(name = "User", description = "This Schema Holds User Information")
public class UserDto {

    public interface Update extends Default {}

    @Schema(description = "Unique identifier for the user", example = "12345")
    private String userId;

    @Schema(description = "First name of the user", example = "atul")
    @NotEmpty(message = "First Name can not be null or empty")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Only character values are allowed")
    private String firstName;

    @Schema(description = "Last name of the user", example = "patel")
    @NotEmpty(message = "Last Name can not be null or empty")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Only character values are allowed")
    private String lastName;

    @Schema(description = "Username of the user", example = "atulpatel")
    @NotEmpty(message = "User Name can not be null or empty")
    @Size(min = 3, max = 12, message = "The length of the User Name should be between 3 and 12")
    private String userName;

    @Schema(description = "Email address of the user", example = "atulpatel@example.com")
    @NotEmpty(message = "Email address can not be null or empty")
    @Email(message = "Email address should be a valid value")
    private String email;

    @Schema(description = "Password of the user", example = "Password@123")
    @Pattern(groups = Default.class, regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$", message = "Password must contain at least 8 characters with at least one special character and One Uppercase Character.")
    @Null(groups = Update.class, message = "Password cannot be updated")
    private String password;

    @Schema(description = "Mobile number of the user", example = "1234567890")
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits without any special characters or alphabets")
    @NotEmpty(message = "Mobile Number can not be null or empty")
    private String mobileNumber;
}
