package com.food.gateway.server;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@CrossOrigin("*")
@SecurityScheme(
		openIdConnectUrl = "http://localhost:8072/realms/FoodSwift/protocol/openid-connect/certs",
		scheme = "bearer",
		type = SecuritySchemeType.OPENIDCONNECT,
		in = SecuritySchemeIn.HEADER
)
public class ApigatewayserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApigatewayserverApplication.class, args);
	}

}
