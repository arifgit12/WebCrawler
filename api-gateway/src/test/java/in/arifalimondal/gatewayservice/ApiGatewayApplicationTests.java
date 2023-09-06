package in.arifalimondal.gatewayservice;

import in.arifalimondal.gatewayservice.util.DiscoveryClientExample;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URI;

@SpringBootTest
class ApiGatewayApplicationTests {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private DiscoveryClientExample discoveryClient;

	private final WebClient webClient;

	@Autowired
	public ApiGatewayApplicationTests(WebClient.Builder webClientBuilder) {
		this.webClient = webClientBuilder.baseUrl("http://IDENTITY-SERVICE").build();
	}

	@Test
	void contextLoads() {

	}

	@Test
	void verifyTokenTest() {
		String authHeader = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhcmlmIiwiaWF0IjoxNjkzNjgwNzQ3LCJleHAiOjE2OTM2ODI1NDd9.01jjKwJHREy2JiCJQFFcCoLwKGwAZ2QmK01ps0GOFfk";

		String identityServiceName = "IDENTITY-SERVICE";
		String provider = discoveryClient.getMicroserviceHostAndPort(identityServiceName);
		System.out.println(provider);
		String endpoint = "/api/auth/validate?token="+authHeader; // Replace with the endpoint path
		// Construct the full URL using the microservice name
		String url = provider + endpoint;
		System.out.println(URI.create(url));
		String response = restTemplate.getForObject(url, String.class);
		System.out.println("Response Status: " + response);
	}

	@Test
	void verifyToken2Test() {
		String authHeader = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhcmlmIiwiaWF0IjoxNjkzNjgzMzYyLCJleHAiOjE2OTM2ODUxNjJ9.zLYcKkQinpD9qwlnNHwrMlABC0dSDTWuoHhuI0_agJ8";

		System.out.println(this.webClient.get().toString());
		String endpoint = "/api/auth/validate?token="+authHeader; // Replace with the endpoint path

		String response = this.webClient
				.get()
				.uri(uriBuilder -> uriBuilder
						.path("/api/auth/validate")
						.queryParam("token", authHeader)
						.build())
				.retrieve()
				.bodyToMono(String.class)
				.block();
		System.out.println(response);
	}
}
