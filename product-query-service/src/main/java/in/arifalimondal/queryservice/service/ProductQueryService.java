package in.arifalimondal.queryservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import in.arifalimondal.queryservice.dto.ProductEvent;
import in.arifalimondal.queryservice.entity.Product;
import in.arifalimondal.queryservice.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductQueryService {

    private final Logger logger = LoggerFactory.getLogger(ProductQueryService.class);

    @Autowired
    private ProductRepository repository;

    public List<Product> getProducts() {
        return repository.findAll();
    }

    @KafkaListener(topics = "product-event-topic",groupId = "product-event-group")
    public void processProductEvents(String productEventJson) {

        ProductEvent productEvent = deserializeEvent(productEventJson);
        logger.info(String.format("Event name {0} and Product {1}", productEvent.getEventType(),
                productEvent.getProduct()));
        Product product = productEvent.getProduct();
        if (productEvent.getEventType().equals("CreateProduct")) {
            repository.save(product);
        }

        if (productEvent.getEventType().equals("UpdateProduct")) {
            Optional<Product> existingProduct = repository.findById(productEvent.getProduct().getId());

            existingProduct.get().setName(product.getName());
            existingProduct.get().setPrice(product.getPrice());
            existingProduct.get().setDescription(product.getDescription());
            repository.save(existingProduct.get());
        }
    }

    public ProductEvent deserializeEvent(String json) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(json, ProductEvent.class);
        } catch (Exception e) {
            // Handle deserialization error
            e.printStackTrace();
            return null; // or throw an exception
        }
    }
}
