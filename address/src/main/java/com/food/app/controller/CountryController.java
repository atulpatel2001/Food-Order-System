package com.food.app.controller;

import com.food.app.constant.Constant;
import com.food.app.dto.CountryDto;
import com.food.app.dto.ErrorResponseDto;
import com.food.app.dto.ResponseDto;
import com.food.app.exception.AllDataNotFoundException;
import com.food.app.exception.AlreadyExistsException;
import com.food.app.exception.ResourceNotFoundException;
import com.food.app.service.CountryService;
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


@RequestMapping(value = "/country", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
@Tag(
        name = "CRUD REST APIs for Country",
        description = "CRUD REST APIs  CREATE, UPDATE, FETCH AND DELETE Country details"
)
@CrossOrigin("*")
public class CountryController {

    @Autowired
    private CountryService countryService;


    /**
     * this endpoint provide a new country to application database
     *
     * @param countryDto
     * @return ErrorResponse Dto With ResponseEntity | ResponseDto
     */
    @Operation(
            summary = "Add New Country REST API",
            description = "REST API to Add New Country"
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
    public ResponseEntity<?> createCountry(@Validated(CountryDto.Create.class) @RequestBody CountryDto countryDto) {
        try {
            this.countryService.addCountry(countryDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDto(Constant.STATUS_201, Constant.MESSAGE_COUNTRY_201));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto(Constant.STATUS_400, Constant.MESSAGE_400));

        }
    }


    /**
     * this endpoint provide a Fetch country from database
     *
     * @param countryId
     * @return ErrorResponse Dto With ResponseEntity | ResponseDto
     */
    @Operation(
            summary = "Fetch Country By Id REST API",
            description = "REST API to Fetch Country By Id"
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
    public ResponseEntity<?> getCountryById(@RequestParam("id")
                                            @NotNull(message = "Country Id can not be null or empty")
                                            Long countryId) {
        try {
            CountryDto country = this.countryService.getCountryById(countryId);
            return ResponseEntity.status(HttpStatus.OK).body(country);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto(Constant.STATUS_400, Constant.MESSAGE_400));
        }
    }


    /**
     * this endpoint is use for delete country from database
     *
     * @param countryId
     * @return ErrorResponse Dto With ResponseEntity | ResponseDto
     */
    @Operation(
            summary = "delete By Id REST API",
            description = "REST API to delete Country By Id"
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
    public ResponseEntity<?> deleteCountry(@RequestParam("countryId") @NotNull(message = "Country Id can not be null or empty") Long countryId) {
        try {
            this.countryService.deleteCountry(countryId);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDto(Constant.STATUS_200, Constant.MESSAGE_200));

        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto(Constant.STATUS_417, Constant.MESSAGE_417_DELETE));
        }
    }

    /**
     * this endpoint provide a Fetch all  country from database
     *
     * @return ErrorResponse Dto With ResponseEntity | ResponseDto
     */
    @Operation(
            summary = "Fetch All Country  REST API",
            description = "REST API to Fetch All Country"
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
    @GetMapping("/countries")
    public ResponseEntity<?> getAllCities() {
        try {
            List<CountryDto> allCountry = this.countryService.getAllCountry();
            return ResponseEntity.status(HttpStatus.OK).body(allCountry);
        } catch (AllDataNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


    /**
     * this endpoint is use for update country from database
     *
     * @param countryDto
     * @return ErrorResponse Dto With ResponseEntity | ResponseDto
     */
    @Operation(
            summary = "update country REST API",
            description = "REST API to update Country"
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
    public ResponseEntity<?> updateCountry(@Validated(CountryDto.Update.class) @RequestBody CountryDto countryDto) {
        try {
            boolean b = this.countryService.updateCountry(countryDto);
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
