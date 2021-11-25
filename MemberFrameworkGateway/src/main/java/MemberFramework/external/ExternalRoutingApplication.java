package MemberFramework.external;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExternalRoutingApplication {

    @Value("${authentication.uri}")
    private String authenticationService;

    @Value("${address.uri}")
    private String addressService;


    private static final Logger LOGGER = LogManager.getLogger(ExternalRoutingApplication.class);

    @Bean
    public RouteLocator apiExternalRoutes(RouteLocatorBuilder builder) {

        LOGGER.info("Spring cloud gateway - Initializing API routes.");
        return builder.routes()
                .route(p -> p.path("/api/v1.0/auth-service/**")
                        .filters(f -> f.rewritePath("/api/v1.0/auth-service/(?<segment>.*)",
                                "/v1/auth-service/${segment}"))
                        .uri(authenticationService))
                .route(p -> p.path("/api/v1.0/add-service/**")
                        .filters(f -> f.rewritePath("/api/v1.0/add-service/(?<segment>.*)",
                                "/v1/add-service/${segment}"))
                        .uri(addressService))
                .build();
    }

}