package com.food.app.controller;

import com.food.app.constant.Constant;
import com.food.app.dto.ResponseDto;
import com.food.app.dto.UserAddressDto;
import com.food.app.service.UserAddressService;
import io.swagger.v3.oas.annotations.tags.Tag;
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
    @PostMapping("/")
    public ResponseEntity<?> addUserAddress(@Validated(UserAddressDto.Create.class) @RequestBody UserAddressDto addressDto){
        try{
            this.addressService.addUserAddress(addressDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDto(Constant.STATUS_201, Constant.MESSAGE_ADDRESS_201));
        }
        catch (Exception e){
         return    ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto(Constant.STATUS_400, Constant.MESSAGE_400));
        }
    }


    @GetMapping("/address")
    public ResponseEntity<?> getAllUserAddress(){
        try{

            return ResponseEntity.status(HttpStatus.OK).body(this.addressService.getAllUserAddress());
        }
        catch (Exception e){
            return    ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto(Constant.STATUS_400, Constant.MESSAGE_400));
        }
    }
}
