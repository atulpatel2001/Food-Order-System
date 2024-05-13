package com.food.app.controller;

import com.food.app.constant.Constant;
import com.food.app.dto.ErrorResponseDto;
import com.food.app.dto.ResponseDto;
import com.food.app.dto.UserAddressDto;
import com.food.app.exception.ResourceNotFoundException;
import com.food.app.service.UserAddressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController


@RequestMapping(value = "/user", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
@Tag(
        name = "CRUD REST APIs for User Address",
        description = "CRUD REST APIs  CREATE, UPDATE, FETCH AND DELETE User Address details"
)
public class UserAddressController {


    @Autowired
    private UserAddressService addressService;


    /**
     * this endpoint provide a new Address Of User
     *
     * @param addressDto
     * @return ErrorResponse Dto With ResponseEntity | ResponseDto
     */
    @Operation(
            summary = "Add new Address Of User REST API",
            description = "REST API to Add New Address Of User"
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

    @PostMapping("/")
    public ResponseEntity<?> addUserAddress(@Validated(UserAddressDto.Create.class) @RequestBody UserAddressDto addressDto) {
        try {
            this.addressService.addUserAddress(addressDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDto(Constant.STATUS_201, Constant.MESSAGE_ADDRESS_201));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


    /**
     * this endpoint provide a Fetch All User Address
     *
     * @return ErrorResponse Dto With ResponseEntity | ResponseDto
     */
    @Operation(
            summary = " Fetch All User Address  REST API",
            description = "REST API to  Fetch All User Address "
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

    @GetMapping("/address")
    public ResponseEntity<?> getAllUserAddress() {
        try {

            return ResponseEntity.status(HttpStatus.OK).body(this.addressService.getAllUserAddress());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


    /**
     * this endpoint provide a Fetch User Address  BY UserId
     *
     * @param userId
     * @return ErrorResponse Dto With ResponseEntity | ResponseDto
     */
    @Operation(
            summary = "Fetch User Address  BY UserId REST API",
            description = "REST API to Fetch User Address  BY UserId"
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

    @GetMapping("/")
    public ResponseEntity<?> getAllUserAddressByUserId(@RequestParam("userId")
                                                       @NotNull(message = "user Id can not be null or empty") String userId) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(this.addressService.getUserAddressByUserId(userId));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }

    /**
     * this endpoint provide a delete User Address  BY UserId ,Address Id
     *
     * @param userId | AddressId
     * @return ErrorResponse Dto With ResponseEntity | ResponseDto
     */
    @Operation(
            summary = "delete User Address  BY UserId ,Address Id REST API",
            description = "REST API to delete User Address  BY UserId ,Address Id"
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

    @DeleteMapping("/")
    public ResponseEntity<?> deleteUserAddress(@RequestParam("userId")
                                               @NotNull(message = "user Id can not be null or empty") String userId,
                                               @RequestParam("addressId")
                                               @NotNull(message = "Address Id can not be null or empty") Long addressId) {
        try {
            this.addressService.deleteUserAddress(addressId, userId);
            return ResponseEntity.status(HttpStatus.OK).body("Address is Successfully Deleted!");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto(Constant.STATUS_417, Constant.MESSAGE_417_DELETE));
        }
    }

}
