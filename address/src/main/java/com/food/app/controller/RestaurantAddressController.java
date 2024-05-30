package com.food.app.controller;

import com.food.app.constant.Constant;
import com.food.app.dto.ErrorResponseDto;
import com.food.app.dto.ResponseDto;
import com.food.app.dto.RestaurantAddressDto;
import com.food.app.exception.ResourceNotFoundException;
import com.food.app.service.RestaurantAddressService;
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


@RequestMapping(value = "/restaurant", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
@Tag(
        name = "CRUD REST APIs for Restaurant Address",
        description = "CRUD REST APIs  CREATE, UPDATE, FETCH AND DELETE Restaurant Address details"
)
@CrossOrigin("*")
public class RestaurantAddressController {


    @Autowired
    private RestaurantAddressService addressService;


    /**
     * this endpoint provide a new Address Of Restaurant
     *
     * @param addressDto |
     * @return ErrorResponse Dto With ResponseEntity | ResponseDto
     */
    @Operation(
            summary = "Add new Address Of Restaurant REST API",
            description = "REST API to Add New Address Of Restaurant"
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
    public ResponseEntity<?> addRestaurantAddress(@Validated(RestaurantAddressDto.Create.class) @RequestBody RestaurantAddressDto addressDto) {
        try {
            this.addressService.addRestaurantAddress(addressDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDto(Constant.STATUS_201, Constant.MESSAGE_ADDRESS_201));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


    /**
     * this endpoint provide a Fetch All Restaurant Address
     *
     * @return ErrorResponse Dto With ResponseEntity | ResponseDto
     */
    @Operation(
            summary = " Fetch All Restaurant Address  REST API",
            description = "REST API to  Fetch All Restaurant Address "
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
    public ResponseEntity<?> getAllRestaurantAddress() {
        try {

            return ResponseEntity.status(HttpStatus.OK).body(this.addressService.getAllRestaurantAddress());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


    /**
     * this endpoint provide a Fetch Restaurant Address  BY RestaurantId
     *
     * @param restaurantId
     * @return ErrorResponse Dto With ResponseEntity | ResponseDto
     */
    @Operation(
            summary = "Fetch Restaurant Address  BY RestaurantId REST API",
            description = "REST API to Fetch Restaurant Address  BY RestaurantId"
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
    public ResponseEntity<?> getAllRestaurantAddressByRestaurantId(@RequestParam("restaurantId")
                                                       @NotNull(message = "restaurant Id can not be null or empty") String restaurantId) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(this.addressService.getRestaurantAddressByRestaurantId(restaurantId));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }

    /**
     * this endpoint provide a delete Restaurant Address  BY RestaurantId ,Address Id
     *
     * @param restaurantId | AddressId
     * @return ErrorResponse Dto With ResponseEntity | ResponseDto
     */
    @Operation(
            summary = "delete Restaurant Address  BY RestaurantId ,Address Id REST API",
            description = "REST API to delete Restaurant Address  BY RestaurantId ,Address Id"
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
    public ResponseEntity<?> deleteRestaurantAddress(@RequestParam("restaurantId")
                                               @NotNull(message = "restaurant Id can not be null or empty") String restaurantId,
                                               @RequestParam("addressId")
                                               @NotNull(message = "Address Id can not be null or empty") Long addressId) {
        try {
            this.addressService.deleteRestaurantAddress(addressId, restaurantId);
            return ResponseEntity.status(HttpStatus.OK).body("Address is Successfully Deleted!");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto(Constant.STATUS_417, Constant.MESSAGE_417_DELETE));
        }
    }


    /**
     * this endpoint provide a new Address Of Restaurant
     *
     * @param addressDto
     * @return ErrorResponse Dto With ResponseEntity | ResponseDto
     */
    @Operation(
            summary = "Add new Address Of Restaurant REST API",
            description = "REST API to Add New Address Of Restaurant"
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

    @PutMapping("/")
    public ResponseEntity<?> updateRestaurantAddress(@Validated(RestaurantAddressDto.Update.class) @RequestBody RestaurantAddressDto addressDto) {
        try {
            boolean flag = this.addressService.updateRestaurantAddress(addressDto);

            if (flag) {
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(Constant.STATUS_200, Constant.MESSAGE_200));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto(Constant.STATUS_417, Constant.MESSAGE_417_UPDATE));

            }
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
