package com.food.app.controller;

import com.food.app.constant.Constant;
import com.food.app.dto.ErrorResponseDto;
import com.food.app.dto.ResponseDto;
import com.food.app.dto.DeliveryAgentAddressDto;
import com.food.app.exception.ResourceNotFoundException;
import com.food.app.service.DeliveryAgentAddressService;
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


@RequestMapping(value = "/deliveryAgent", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
@Tag(
        name = "CRUD REST APIs for DeliveryAgent Address",
        description = "CRUD REST APIs  CREATE, UPDATE, FETCH AND DELETE DeliveryAgent Address details"
)
@CrossOrigin("*")
public class DeliveryAgentAddressController {


    @Autowired
    private DeliveryAgentAddressService addressService;


    /**
     * this endpoint provide a new Address Of DeliveryAgent
     *
     * @param addressDto |
     * @return ErrorResponse Dto With ResponseEntity | ResponseDto
     */
    @Operation(
            summary = "Add new Address Of DeliveryAgent REST API",
            description = "REST API to Add New Address Of DeliveryAgent"
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
    public ResponseEntity<?> addDeliveryAgentAddress(@Validated(DeliveryAgentAddressDto.Create.class) @RequestBody DeliveryAgentAddressDto addressDto) {
        try {
            this.addressService.addDeliveryAgentAddress(addressDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDto(Constant.STATUS_201, Constant.MESSAGE_ADDRESS_201));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


    /**
     * this endpoint provide a Fetch All DeliveryAgent Address
     *
     * @return ErrorResponse Dto With ResponseEntity | ResponseDto
     */
    @Operation(
            summary = " Fetch All DeliveryAgent Address  REST API",
            description = "REST API to  Fetch All DeliveryAgent Address "
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
    public ResponseEntity<?> getAllDeliveryAgentAddress() {
        try {

            return ResponseEntity.status(HttpStatus.OK).body(this.addressService.getAllDeliveryAgentAddress());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


    /**
     * this endpoint provide a Fetch DeliveryAgent Address  BY DeliveryAgentId
     *
     * @param deliveryAgentId |
     * @return ErrorResponse Dto With ResponseEntity | ResponseDto
     */
    @Operation(
            summary = "Fetch DeliveryAgent Address  BY DeliveryAgentId REST API",
            description = "REST API to Fetch DeliveryAgent Address  BY DeliveryAgentId"
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
    public ResponseEntity<?> getAllDeliveryAgentAddressByDeliveryAgentId(@RequestParam("deliveryAgentId")
                                                       @NotNull(message = "deliveryAgent Id can not be null or empty") String deliveryAgentId) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(this.addressService.getDeliveryAgentAddressByDeliveryAgentId(deliveryAgentId));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }

    /**
     * this endpoint provide a delete DeliveryAgent Address  BY DeliveryAgentId ,Address Id
     *
     * @param deliveryAgentId | AddressId
     * @return ErrorResponse Dto With ResponseEntity | ResponseDto
     */
    @Operation(
            summary = "delete DeliveryAgent Address  BY DeliveryAgentId ,Address Id REST API",
            description = "REST API to delete DeliveryAgent Address  BY DeliveryAgentId ,Address Id"
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
    public ResponseEntity<?> deleteDeliveryAgentAddress(@RequestParam("deliveryAgentId")
                                               @NotNull(message = "deliveryAgent Id can not be null or empty") String deliveryAgentId,
                                               @RequestParam("addressId")
                                               @NotNull(message = "Address Id can not be null or empty") Long addressId) {
        try {
            this.addressService.deleteDeliveryAgentAddress(addressId, deliveryAgentId);
            return ResponseEntity.status(HttpStatus.OK).body("Address is Successfully Deleted!");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto(Constant.STATUS_417, Constant.MESSAGE_417_DELETE));
        }
    }


    /**
     * this endpoint provide a new Address Of DeliveryAgent
     *
     * @param addressDto
     * @return ErrorResponse Dto With ResponseEntity | ResponseDto
     */
    @Operation(
            summary = "Add new Address Of DeliveryAgent REST API",
            description = "REST API to Add New Address Of DeliveryAgent"
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
    public ResponseEntity<?> updateDeliveryAgentAddress(@Validated(DeliveryAgentAddressDto.Update.class) @RequestBody DeliveryAgentAddressDto addressDto) {
        try {
            boolean flag = this.addressService.updateDeliveryAgentAddress(addressDto);

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
