package in.arifalimondal.orderservice.service;

import in.arifalimondal.orderservice.dto.InventoryResponse;
import in.arifalimondal.orderservice.dto.OrderLineItemsDto;
import in.arifalimondal.orderservice.dto.OrderRequest;
import in.arifalimondal.orderservice.model.Order;
import in.arifalimondal.orderservice.model.OrderLineItems;
import in.arifalimondal.orderservice.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final WebClient webClient;

    public OrderService(OrderRepository orderRepository, WebClient.Builder webClientBuilder) {
        this.orderRepository = orderRepository;
        this.webClient = webClientBuilder.baseUrl("http://localhost:8082").build();
    }

    public void placeOrder(OrderRequest orderRequest) {

        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItems> orderLineItems =
                orderRequest.getOrderLineItemsDtoList()
                .stream()
                .map(this::mapToDto)
                .toList();

        order.setOrderLineItemsList(orderLineItems);

        // Call Inventory Service to check all skuCode
        List<String> skuCodes = order.getOrderLineItemsList().stream()
                                        .map(OrderLineItems::getSkuCode)
                                        .toList();
        InventoryResponse[] inventoryResponseArray = webClient.get()
                                    .uri("/api/inventory",
                                            uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
                                    .retrieve()
                                    .bodyToMono(InventoryResponse[].class)
                                    .block();

        final boolean allProductInStock = Arrays.stream(inventoryResponseArray).allMatch(InventoryResponse::isInStock);

        if (allProductInStock) {
            orderRepository.save(order);
        }else {
            throw new IllegalArgumentException("Items not in stock, try later");
        }
    }

    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        return orderLineItems;
    }
}
