package com.food.app.user.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PublicController {

    @Value("${atul}")
    private String atul;

    @GetMapping("/atul")
    public String getAtul(){
        return atul;
    }
}
