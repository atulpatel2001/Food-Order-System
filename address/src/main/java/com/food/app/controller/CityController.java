package com.food.app.controller;

import com.food.app.constant.Constant;
import com.food.app.dto.CityDto;
import com.food.app.dto.ErrorResponseDto;
import com.food.app.exception.AllDataNotFoundException;
import com.food.app.exception.AlreadyExistsException;
import com.food.app.dto.ResponseDto;
import com.food.app.exception.ResourceNotFoundException;
import com.food.app.service.CityService;
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

import java.util.List;

@RestController


@RequestMapping(value = "/city", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
@Tag(
        name = "CRUD REST APIs for City",
        description = "CRUD REST APIs  CREATE, UPDATE, FETCH AND DELETE City details"
)
public class CityController {

    @Autowired
    private CityService cityService;


    /**
     * this endpoint provide a new city to application database
     *
     * @param cityDto
     * @return ErrorResponse Dto With ResponseEntity | ResponseDto
     */
    @Operation(
            summary = "Add New City REST API",
            description = "REST API to Add New City"
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
    public ResponseEntity<?> createCity(@Validated(CityDto.Create.class) @RequestBody CityDto cityDto) {
        try {
            this.cityService.addCity(cityDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDto(Constant.STATUS_201, Constant.MESSAGE_CITY_201));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto(Constant.STATUS_400, Constant.MESSAGE_400));

        }
    }


    /**
     * this endpoint provide a Fetch city from database
     *
     * @param cityId
     * @return ErrorResponse Dto With ResponseEntity | ResponseDto
     */
    @Operation(
            summary = "Fetch City By Id REST API",
            description = "REST API to Fetch City By Id"
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
    @GetMapping("/")
    public ResponseEntity<?> getCityById(@RequestParam("id")
                                         @NotNull(message = "City Id can not be null or empty")
                                         Long cityId) {
        try {
            CityDto city = this.cityService.getCityById(cityId);
            return ResponseEntity.status(HttpStatus.OK).body(city);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto(Constant.STATUS_400, Constant.MESSAGE_400));
        }
    }


    /**
     * this endpoint is use for delete city from database
     *
     * @param cityId
     * @return ErrorResponse Dto With ResponseEntity | ResponseDto
     */
    @Operation(
            summary = "delete By Id REST API",
            description = "REST API to delete City By Id"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "200",
                    description = "Request processed successfully",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "Delete operation failed. Please try again or contact Dev team",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @DeleteMapping("/")
    public ResponseEntity<?> deleteCity(@RequestParam("cityId") @NotNull(message = "City Id can not be null or empty") Long cityId) {
        try {
            this.cityService.deleteCity(cityId);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDto(Constant.STATUS_200, Constant.MESSAGE_200));

        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto(Constant.STATUS_417, Constant.MESSAGE_417_DELETE));
        }
    }

    /**
     * this endpoint provide a Fetch all  city from database
     *
     * @return ErrorResponse Dto With ResponseEntity | ResponseDto
     */
    @Operation(
            summary = "Fetch All City  REST API",
            description = "REST API to Fetch All City"
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
    @GetMapping("/cities")
    public ResponseEntity<?> getAllCities() {
        try {
            List<CityDto> allCity = this.cityService.getAllCity();
            return ResponseEntity.status(HttpStatus.OK).body(allCity);
        } catch (AllDataNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


    /**
     * this endpoint is use for update city from database
     *
     * @param cityDto
     * @return ErrorResponse Dto With ResponseEntity | ResponseDto
     */
    @Operation(
            summary = "update city REST API",
            description = "REST API to update City"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "200",
                    description = "Request processed successfully",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "Update operation failed. Please try again or contact Dev team",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @PutMapping("/")
    public ResponseEntity<?> updateCity( @Validated(CityDto.Update.class) @RequestBody CityDto cityDto) {
        try {
            boolean b = this.cityService.updateCity(cityDto);
            if (b) {
                return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDto(Constant.STATUS_200, Constant.MESSAGE_200));

            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto(Constant.STATUS_417, Constant.MESSAGE_417_UPDATE));

            }

        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());

        }
    }

}
