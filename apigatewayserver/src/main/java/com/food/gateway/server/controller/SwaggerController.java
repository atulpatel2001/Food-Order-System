package com.food.gateway.server.controller;

import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@RestController
public class SwaggerController {

    private final DiscoveryClient discoveryClient;

    public SwaggerController(final DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
    }

    @GetMapping("/v3/api-docs/swagger-config")
    public Mono<ResponseEntity<Map<String, Object>>> swaggerConfig() throws URISyntaxException {
        URI gatewayUri = new URI("http://localhost:8074"); // Your gateway URL
        String gatewayUrl = new URI(gatewayUri.getScheme(), gatewayUri.getAuthority(), null, null, null).toString();
        Map<String, Object> swaggerConfig = new LinkedHashMap<>();
        List<Map<String, String>> swaggerUrls = new LinkedList<>();

        discoveryClient.getServices().stream().filter(s -> !s.startsWith("kube")).forEach(serviceName -> {
            Map<String, String> urlMap = new HashMap<>();
            urlMap.put("name", serviceName);
            urlMap.put("url", gatewayUrl + "/" + serviceName + "/v3/api-docs"); // Gateway URL + service name
            swaggerUrls.add(urlMap);
        });

        swaggerConfig.put("urls", swaggerUrls);
        return Mono.just(ResponseEntity.ok(swaggerConfig));
    }
}



