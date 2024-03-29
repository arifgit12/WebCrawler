package in.arifalimondal.gatewayservice.filter;

import in.arifalimondal.gatewayservice.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.concurrent.TimeUnit;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationFilter.class);

    @Autowired
    private RouteValidator validator;

    @Autowired
    private JwtUtil jwtUtil;

    private final WebClient webClient;

    @Autowired
    public AuthenticationFilter(WebClient.Builder webClientBuilder) {
        super(Config.class);
        this.webClient = webClientBuilder.baseUrl("http://IDENTITY-SERVICE/api/auth").build();;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {

            ServerHttpRequest request = exchange.getRequest();

            if (validator.isSecured.test(request)) {
                if (!request.getHeaders().containsKey("Authorization")) {
                    return getResponseError(exchange, "{\"error\": \"Unauthorized\", \"message\": \"No Authorization header\"}");
                    //return this.onError(exchange, "No Authorization header", HttpStatus.UNAUTHORIZED);
                }

                String authorizationHeader = request.getHeaders().get("Authorization").get(0);

                if (!this.isAuthorizationValid(authorizationHeader)) {
                    return getResponseError(exchange, "{\"error\": \"Unauthorized\", \"message\": \"Invalid Authorization header\"}");
                    //return this.onError(exchange, "Invalid Authorization header", HttpStatus.UNAUTHORIZED);
                }

            }


            return chain.filter(exchange);
        });
    }

    private Mono<Void> getResponseError(ServerWebExchange exchange, String s) {
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        String errorMessage = s;
        DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(errorMessage.getBytes());
        return exchange.getResponse().writeWith(Mono.just(buffer));
    }

    private boolean isAuthorizationValid(String authHeader) {
        boolean isValid = true;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            authHeader = authHeader.substring(7);
        }
        try {
            //REST call to AUTH service
            //ValidateTokenFromIdentityServer(authHeader);

            logger.info("Validation token");
            jwtUtil.validateToken(authHeader);
        } catch (Exception e) {
            logger.error("Invalid Access .... {}", e.getMessage());
            isValid=false;
        }

        return isValid;
    }

    private Mono<Void> onError(ServerWebExchange exchange, String err,
                               HttpStatus httpStatus)  {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);

        DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(err.getBytes());
        response.writeWith(Mono.just(buffer));
        return response.setComplete();
    }

    private void ValidateTokenFromIdentityServer(String authHeader) throws InterruptedException, java.util.concurrent.ExecutionException, java.util.concurrent.TimeoutException {
        String finalAuthHeader = authHeader;
        Mono<String> result = this.webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/validate")
                        .queryParam("token", finalAuthHeader)
                        .build())
                .retrieve()
                .bodyToMono(String.class);

        String response = result.subscribeOn(Schedulers.boundedElastic()).toFuture().get(5L, TimeUnit.SECONDS);
        logger.info("Identity Service Response : {}", response);
    }

    public static class Config {

    }
}
