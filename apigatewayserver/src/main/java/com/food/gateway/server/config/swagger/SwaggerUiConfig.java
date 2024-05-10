//package com.food.gateway.server.config.swagger;
//
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springdoc.core.models.GroupedOpenApi;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.gateway.route.RouteDefinition;
//import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.ArrayList;
//import java.util.List;
//
//
//@Configuration
//public class SwaggerUiConfig {
//    private static final Logger LOGGER = LoggerFactory
//            .getLogger(SwaggerUiConfig.class);
//
//    @Autowired
//    RouteDefinitionLocator locator;
//
//    @Bean
//    public List<GroupedOpenApi> apis() {
//        List<GroupedOpenApi> groups = new ArrayList<>();
//        List<RouteDefinition> definitions = locator
//                .getRouteDefinitions().collectList().block();
//        assert definitions != null;
//        definitions.stream().filter(routeDefinition -> routeDefinition
//                        .getId()
//                        .matches(".*-service"))
//                .forEach(routeDefinition -> {
//                    String name = routeDefinition.getId()
//                            .replaceAll("-service", "");
//                    groups.add(GroupedOpenApi.builder()
//                            .pathsToMatch("/" + name + "/**").group(name).build());
//                });
//        return groups;
//    }
//
//}
