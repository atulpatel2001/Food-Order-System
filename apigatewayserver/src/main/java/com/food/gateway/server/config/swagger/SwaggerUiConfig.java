package com.food.gateway.server.config.swagger;


import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Configuration
public class SwaggerUiConfig {
//    @Bean
//    public UiConfiguration uiConfig(DiscoveryClient discoveryClient) throws URISyntaxException {
//        Map<String, String> serviceUrls = getMicroserviceUrls(discoveryClient);
//        UiConfiguration ui = new UiConfiguration(null);
//        ui.setUrls(serviceUrls);
//        return ui;
//    }
//
//    private Map<String, String> getMicroserviceUrls(DiscoveryClient discoveryClient) throws URISyntaxException {
//        // This logic is similar to your existing swaggerConfig method
//        // to retrieve service names and construct URLs for each microservice
//        URI uri = new URI("http://localhost:8074");
//        String url = new URI(uri.getScheme(), uri.getAuthority(), null, null, null).toString();
//        Map<String, String> swaggerUrls = new LinkedHashMap<>();
//
//        discoveryClient.getServices().stream().filter(s -> !s.startsWith("kube"))
//                .forEach(serviceName -> {
//                    Map<String, String> urlMap = new HashMap<>();
//                    urlMap.put("name", serviceName);
//                    urlMap.put("url", url + "/" + serviceName + "/v3/api-docs");
//                    swaggerUrls.putAll(urlMap);
//                });
//
//        return swaggerUrls;
//    }



}
