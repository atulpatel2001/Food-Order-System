package com.food.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)

public class AllDataNotFoundException extends RuntimeException{

    public AllDataNotFoundException(String resourceName){
        super(String.format("No %s found in the database. Please contact the administrator to add %s to the system.",resourceName,resourceName));
    }
}
