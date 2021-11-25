package MemberFramework.filter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.cors.reactive.CorsUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import reactor.core.publisher.Mono;


@Configuration
public class CorsConfiguration {

    private static final Logger LOGGER = LogManager.getLogger(CorsConfiguration.class.getName());
    //private static final String ALLOWED_HEADERS = "*";

    private static final String ALLOWED_HEADERS = "Origin,X-Requested-With,Content-Type,Accept,csrftoken,X-Forwarded-For,User-Agent";
    private static final String ALLOWED_METHODS = "GET, PUT, POST, DELETE, OPTIONS";
    private static final String ALLOWED_ORIGIN = "*";
    //private static final String MAX_AGE = "3600";
    private String securityPolicy = "default-src 'self'";
    private String xssprotection = "1; mode=block";
    private String contentOptions = "nosniff";
    private String transportSecurity = "max-age=31536000; includeSubdomains";
    private String xframeOPTIONS = "SAMEORIGIN";

    @Bean
    public WebFilter corsFilter() {
        return (ServerWebExchange ctx, WebFilterChain chain) -> {
            ServerHttpRequest request = ctx.getRequest();
            LOGGER.info(request.getRemoteAddress());
            LOGGER.info(request.getHeaders());
            if (CorsUtils.isCorsRequest(request)) {
                ServerHttpResponse response = ctx.getResponse();
                HttpHeaders headers = response.getHeaders();
                headers.add("Access-Control-Allow-Origin", ALLOWED_ORIGIN);
                headers.add("Access-Control-Allow-Methods", ALLOWED_METHODS);
                headers.add("X-FRAME-OPTIONS", this.xframeOPTIONS);
                headers.add("Content-Security-Policy", this.securityPolicy);
                headers.add("X-Content-Type-Options", this.contentOptions);
                headers.add("X-XSS-Protection", this.xssprotection);
                headers.add("Strict-Transport-Security", this.transportSecurity);
                headers.add("Access-Control-Allow-Headers", ALLOWED_HEADERS);


                if (request.getMethod() == HttpMethod.OPTIONS) {
                    response.setStatusCode(HttpStatus.OK);
                    return Mono.empty();
                }
            }
            return chain.filter(ctx);
        };
    }

}
