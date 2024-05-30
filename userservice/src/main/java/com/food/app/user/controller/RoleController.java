package com.food.app.user.controller;

import com.food.app.user.constant.UserConstant;
import com.food.app.user.dto.ErrorResponseDto;
import com.food.app.user.dto.ResponseDto;
import com.food.app.user.dto.RoleDto;
import com.food.app.user.dto.UserDto;
import com.food.app.user.exception.ResourceNotFoundException;
import com.food.app.user.exception.UserAlreadyExistsException;
import com.food.app.user.services.KeyCloakAdminClientToRoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController


@RequestMapping(value = "/role", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
@Tag(
        name = "CRUD REST APIs for Role",
        description = "CRUD REST APIs  CREATE, UPDATE, FETCH AND DELETE Role details"
)
@CrossOrigin("*")
public class RoleController {
    private static final Logger logger = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    private KeyCloakAdminClientToRoleService clientToRoleService;


    /**
     * this endpoint provide a Fetch all  role from database
     *
     * @return List<RoleDto>
     */
    @Operation(
            summary = "Fetch roles REST API",
            description = "REST API to Fetch roles"
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
    @GetMapping("/roles")
    public ResponseEntity<?> getAllRole() {
        try {
            List<RoleDto> allRoles = this.clientToRoleService.getAllRoles();
            return ResponseEntity.status(HttpStatus.OK).body(allRoles);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong!");
        }
    }


    /**
     * this endpoint provide a Fetch role from database
     *
     * @param roleName
     * @return RoleDto
     */
    @Operation(
            summary = "Fetch Role REST API",
            description = "REST API to Fetch Role"
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
    public ResponseEntity<?> getRole(@RequestParam("roleName") String roleName) {
        try {
            RoleDto roleDto = this.clientToRoleService.getRole(roleName);
            return ResponseEntity.status(HttpStatus.OK).body(roleDto);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong!");
        }
    }


    /**
     * this endpoint provide a creat role and store to keycloak database and application database
     *
     * @param roleDto
     * @return ErrorResponse Dto With ResponseEntity
     */
    @Operation(
            summary = "Create Role REST API",
            description = "REST API to Create New Role"
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
    public ResponseEntity<?> addRole(@Valid @RequestBody RoleDto roleDto) {
        try {
            this.clientToRoleService.addRole(roleDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDto(UserConstant.STATUS_201, "Role Created Successfully!"));
        } catch (UserAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong!");
        }
    }


    /**
     * this endpoint provide a Update role and store to keycloak database and application database
     *
     * @param role
     * @return ErrorResponse Dto With ResponseEntity
     */
    @Operation(
            summary = "Update Role REST API",
            description = "REST API to  Update Role"
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
    @PutMapping("/update")
    public ResponseEntity<?> updateRole(@Valid @RequestBody RoleDto role) {
        try {
            boolean b = this.clientToRoleService.updateRole(role);
            if (b) {
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(UserConstant.STATUS_200, UserConstant.MESSAGE_200));
            } else {
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(UserConstant.STATUS_417, "Role Updation Failed"));

            }
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong!");

        }
    }


    /**
     * this endpoint provide a delete role
     *
     * @param roleName
     * @return ErrorResponse Dto With ResponseEntity
     */
    @Operation(
            summary = "Delete Role REST API",
            description = "REST API to  Delete Role"
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
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteRole(@RequestParam("roleName") @NotNull(message = "Role Name Must be require") String roleName) {
        try {
            boolean b = this.clientToRoleService.deleteRole(roleName);
            if (b) {
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(UserConstant.STATUS_200, UserConstant.MESSAGE_200));
            } else {
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(UserConstant.STATUS_417, "Role Deletion Failed"));

            }
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong!");

        }
    }

    /**
     * this endpoint provide a fetch role by user id
     *
     * @param userId
     * @return ErrorResponse Dto With ResponseEntity
     */
    @Operation(
            summary = "Fetch Role By User Id REST API",
            description = "REST API to  Fetch Role By User Id"
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
    @GetMapping("/user/{userId}/roles")
    public ResponseEntity<List<RoleDto>> fetchRoleByUserId(@PathVariable("userId") String userId) {
        return ResponseEntity.ok().body(clientToRoleService.getRolesForUser(userId));
    }


    /**
     * this endpoint provide a Assign  role to user
     *
     * @param userId | roleName
     * @return ErrorResponse Dto With ResponseEntity
     */
    @Operation(
            summary = "Assign Role To User REST API",
            description = "REST API to  Assign Role To User"
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
    @GetMapping("/user/{userId}/role/{roleName}")
    public ResponseEntity<?> assignRoleToUser(@PathVariable("userId") String userId, @PathVariable("roleName") String roleName) {
        try {
            boolean b = clientToRoleService.assignRoleToUser(userId, roleName);


            if (b) {
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(UserConstant.STATUS_200, UserConstant.MESSAGE_200));
            } else {
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(UserConstant.STATUS_417, "Role Assign Failed"));

            }
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong!");

        }


    }


}
