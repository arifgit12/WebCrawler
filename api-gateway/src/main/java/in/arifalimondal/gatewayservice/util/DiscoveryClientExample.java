package in.arifalimondal.gatewayservice.util;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DiscoveryClientExample {

    @Autowired
    private DiscoveryClient discoveryClient;

    public void getName() throws Exception {
        discoveryClient.getInstances("identity-service").forEach((ServiceInstance s) -> {
            System.out.println(ToStringBuilder.reflectionToString(s));
        });
    }

    public String getMicroserviceHostAndPort(String microserviceName) {
        // Get the list of service instances for the specified microservice
        List<ServiceInstance> instances = discoveryClient.getInstances(microserviceName);

        if (instances.isEmpty()) {
            return "Microservice not found";
        }

        // Get the first (or any) instance of the microservice
        ServiceInstance instance = instances.get(0);

        // Extract host and port
        String host = instance.getHost();
        int port = instance.getPort();

        return "http://" + host + ":" + port;
    }
}
