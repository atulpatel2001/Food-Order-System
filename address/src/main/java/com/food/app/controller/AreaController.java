package com.food.app.controller;

import com.food.app.constant.Constant;
import com.food.app.dto.ErrorResponseDto;
import com.food.app.dto.ResponseDto;
import com.food.app.dto.AreaDto;
import com.food.app.exception.AllDataNotFoundException;
import com.food.app.exception.AlreadyExistsException;
import com.food.app.exception.ResourceNotFoundException;
import com.food.app.service.AreaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.groups.Default;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController


@RequestMapping(value = "/area", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
@Tag(
        name = "CRUD REST APIs for Area",
        description = "CRUD REST APIs  CREATE, UPDATE, FETCH AND DELETE Area details"
)
@CrossOrigin("*")
public class AreaController {

    @Autowired
    private AreaService areaService;


    /**
     * this endpoint provide a new area to application database
     *
     * @param areaDto
     * @return ErrorResponse Dto With ResponseEntity | ResponseDto
     */
    @Operation(
            summary = "Add New Area REST API",
            description = "REST API to Add New Area"
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
    public ResponseEntity<?> createArea(@Validated(AreaDto.Create.class) @RequestBody AreaDto areaDto) {
        try {
            this.areaService.addArea(areaDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDto(Constant.STATUS_201, Constant.MESSAGE_AREA_201));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto(Constant.STATUS_400, Constant.MESSAGE_400));

        }
    }


    /**
     * this endpoint provide a Fetch area from database
     *
     * @param areaId
     * @return ErrorResponse Dto With ResponseEntity | ResponseDto
     */
    @Operation(
            summary = "Fetch Area By Id REST API",
            description = "REST API to Fetch Area By Id"
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
    public ResponseEntity<?> getAreaById(@RequestParam("id")
                                         @NotNull(message = "Area Id can not be null or empty")
                                         Long areaId) {
        try {
            AreaDto area = this.areaService.getAreaById(areaId);
            return ResponseEntity.status(HttpStatus.OK).body(area);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto(Constant.STATUS_400, Constant.MESSAGE_400));
        }
    }


    /**
     * this endpoint provide a Fetch  area by cityId from database
     *
     * @param cityId
     * @return ErrorResponse Dto With ResponseEntity | ResponseDto
     */
    @Operation(
            summary = "Fetch Area By City Id REST API",
            description = "REST API to Fetch Area By City Id"
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
    @GetMapping("/city")
    public ResponseEntity<?> getAreaByCountryId(@RequestParam("cityId")
                                          @NotNull(message = "Country Id can not be null or empty")
                                          Long cityId) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(this.areaService.getAreaByCityId(cityId));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto(Constant.STATUS_400, Constant.MESSAGE_400));
        }
    }



    /**
     * this endpoint provide a Fetch  area by PinCode from database
     *
     * @param pinCode
     * @return ErrorResponse Dto With ResponseEntity | ResponseDto
     */
    @Operation(
            summary = "Fetch Area By PinCode REST API",
            description = "REST API to Fetch Area By PinCode"
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
    @GetMapping("/pincode")
    public ResponseEntity<?> getAreaByPinCode(@RequestParam("pinCode")
                                                  @Pattern(regexp = "(^$|[0-9]{6})", message = "PinCode must be 6 digits without any special characters or alphabets")
                                                  @NotNull(message = "PinCode can not be null or empty")
                                                String pinCode) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(this.areaService.getAreaByPinCode(pinCode));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto(Constant.STATUS_400, Constant.MESSAGE_400));
        }
    }


    /**
     * this endpoint is use for delete area from database
     *
     * @param areaId
     * @return ErrorResponse Dto With ResponseEntity | ResponseDto
     */
    @Operation(
            summary = "delete By Id REST API",
            description = "REST API to delete Area By Id"
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
    public ResponseEntity<?> deleteArea(@RequestParam("areaId") @NotNull(message = "Area Id can not be null or empty") Long areaId) {
        try {
            this.areaService.deleteArea(areaId);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDto(Constant.STATUS_200, Constant.MESSAGE_200));

        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto(Constant.STATUS_417, Constant.MESSAGE_417_DELETE));
        }
    }

    /**
     * this endpoint provide a Fetch all  area from database
     *
     * @return ErrorResponse Dto With ResponseEntity | ResponseDto
     */
    @Operation(
            summary = "Fetch All Area  REST API",
            description = "REST API to Fetch All Area"
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
    @GetMapping("/areas")
    public ResponseEntity<?> getAllCities() {
        try {
            List<AreaDto> allArea = this.areaService.getAllArea();
            return ResponseEntity.status(HttpStatus.OK).body(allArea);
        } catch (AllDataNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


    /**
     * this endpoint is use for update area from database
     *
     * @param areaDto|
     * @return ErrorResponse Dto With ResponseEntity | ResponseDto
     */
    @Operation(
            summary = "update area REST API",
            description = "REST API to update Area"
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
    public ResponseEntity<?> updateArea( @Validated(AreaDto.Update.class) @RequestBody AreaDto areaDto) {
        try {
            boolean b = this.areaService.updateArea(areaDto);
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
