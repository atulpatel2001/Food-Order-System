package com.food.app.user.controller;

import com.food.app.user.constant.UserConstant;
import com.food.app.user.dto.ErrorResponseDto;
import com.food.app.user.dto.LoginDto;
import com.food.app.user.dto.ResponseDto;
import com.food.app.user.dto.UserDto;
import com.food.app.user.exception.ResourceNotFoundException;
import com.food.app.user.exception.UserAlreadyExistsException;
import com.food.app.user.services.KeyCloakAdminClientToUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.AccessTokenResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.BadRequestException;
import java.util.List;

@RestController


@RequestMapping(value = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
@Tag(
        name = "CRUD REST APIs for Users",
        description = "CRUD REST APIs  CREATE, UPDATE, FETCH AND DELETE Users details"
)
@CrossOrigin("*")
public class UserController {


    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private KeyCloakAdminClientToUserService keyCloakAdminClientToUserService;


    /**
     * this endpoint provide a creat user and store to keycloak database and application database
     *
     * @param userDto
     * @return ErrorResponse Dto With ResponseEntity
     */
    @Operation(
            summary = "Register User REST API",
            description = "REST API to Register New User"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "HTTP Status CREATED"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @PostMapping("/create")
    public ResponseEntity<?> createUser(@Valid @RequestBody UserDto userDto) {
      try {
          boolean isRegistered = this.keyCloakAdminClientToUserService.createKeyCloakUser(userDto);

          if (isRegistered) {
              return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDto(UserConstant.STATUS_201, UserConstant.MESSAGE_201));
          } else {
              return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto(UserConstant.STATUS_400, UserConstant.MESSAGE_400));
          }
      }
      catch (UserAlreadyExistsException e){
          return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
      }
    }


    /**
     * this endpoint provide a login a user in a system and authenticate user
     *
     * @param loginDto
     * @return AccessTokenResponse
     */
    @Operation(
            summary = "Login User REST API",
            description = "REST API to Login User"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDto loginDto) {
        Keycloak keycloak = this.keyCloakAdminClientToUserService.login(loginDto).build();
        AccessTokenResponse accessTokenResponse = null;
        try {
            accessTokenResponse = keycloak.tokenManager().getAccessToken();
            return ResponseEntity.status(HttpStatus.OK).body(accessTokenResponse);
        } catch (BadRequestException ex) {
            logger.warn("invalid account. User probably hasn't verified email.", ex);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ResponseDto(UserConstant.STATUS_401, UserConstant.MESSAGE_401));
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ResponseDto(UserConstant.STATUS_401, UserConstant.MESSAGE_401));

        }

    }


    /**
     * this endpoint provide a delete user from keycloak db and also application db
     *
     * @param userId
     * @return ResponseEntity ?
     */
    @Operation(
            summary = "Delete User REST API",
            description = "REST API to Delete User"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUser(@RequestParam("userId") @NotEmpty(message = "User ID is required!") String userId) {
        try {
            boolean b = keyCloakAdminClientToUserService.deleteUser(userId);
            if (b) {
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(UserConstant.STATUS_200, UserConstant.MESSAGE_200));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseDto(UserConstant.STATUS_417, UserConstant.MESSAGE_417_DELETE));
            }
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

    }


    /**
     * this endpoint provide a Fetch user from database
     *
     * @param userId
     * @return UserDto
     */
    @Operation(
            summary = "Fetch User REST API",
            description = "REST API to Fetch User"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @GetMapping("/fetch/")
    public ResponseEntity<?> getUserById(@RequestParam("userId") @NotEmpty(message = "User ID is required!") String userId) {
        try {
            UserDto userDto = this.keyCloakAdminClientToUserService.getUserById(userId);
            return ResponseEntity.status(HttpStatus.OK).body(userDto);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong!");
        }
    }


    /**
     * this endpoint provide a Fetch all  user from database
     *
     * @return List<UserDto>
     */
    @Operation(
            summary = "Fetch Users REST API",
            description = "REST API to Fetch Users"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers() {
        try {
            List<UserDto> users = this.keyCloakAdminClientToUserService.getUsers();
            return ResponseEntity.status(HttpStatus.OK).body(users);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong!");
        }
    }


    /**
     * this endpoint provide a Update user from database
     *
     * @return ResponseEntity<?></?>
     */
    @Operation(
            summary = "Update User REST API",
            description = "REST API to Update User"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @PutMapping("/update-user")
    public ResponseEntity<?> updateUser(@Validated(UserDto.Update.class) @RequestBody UserDto userDto) {
        try {
            boolean updated = this.keyCloakAdminClientToUserService.updateUser(userDto);
            if (updated) {
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body(new ResponseDto(UserConstant.STATUS_200, UserConstant.MESSAGE_200));
            } else {
                return ResponseEntity
                        .status(HttpStatus.EXPECTATION_FAILED)
                        .body(new ResponseDto(UserConstant.STATUS_417, UserConstant.MESSAGE_417_UPDATE));
            }
        } catch (UserAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage()); // Use CONFLICT for existing user error
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage()); // Maintain for user not found in database
        } catch (RuntimeException e) { // Catch RuntimeException (wrapped unexpected exceptions)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while updating the user.");
        }
    }


}
