package com.food.app.user.controller;

import com.food.app.user.constant.UserConstant;
import com.food.app.user.dto.ErrorResponseDto;
import com.food.app.user.dto.LoginDto;
import com.food.app.user.dto.ResponseDto;
import com.food.app.user.dto.UserDto;
import com.food.app.user.exception.UserAlreadyExistsException;
import com.food.app.user.services.KeyCloakAdminClientToUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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

@RestController
@RequestMapping(value = "/home", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
@Tag(
        name = "Register and login user",
        description = "Register and Login user get token"
)
@CrossOrigin("*")
public class HomeController {

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
}
