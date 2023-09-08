package in.arifalimondal.gatewayservice.filter;

import in.arifalimondal.gatewayservice.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
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
            if (validator.isSecured.test(exchange.getRequest())) {
                //header contains token or not
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    throw new RuntimeException("missing authorization header");
                }

                String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    authHeader = authHeader.substring(7);
                    //System.out.println("Token: "+authHeader);
                }
                try {
                    //REST call to AUTH service
                    ValidateTokenFromIdentityServer(authHeader);

                    logger.info("Validation token");
                    jwtUtil.validateToken(authHeader);
                } catch (Exception e) {
                    logger.error("Invalid Access .... {}", e.getMessage());
                    throw new RuntimeException("un authorized access to application");
                }
            }
            return chain.filter(exchange);
        });
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
