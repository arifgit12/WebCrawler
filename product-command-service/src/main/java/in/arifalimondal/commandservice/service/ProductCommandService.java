package in.arifalimondal.commandservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import in.arifalimondal.commandservice.dto.ProductEvent;
import in.arifalimondal.commandservice.entity.Product;
import in.arifalimondal.commandservice.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductCommandService {

    @Autowired
    private ProductRepository repository;

    @Autowired
    private KafkaTemplate<String,Object> kafkaTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public Product createProduct(ProductEvent productEvent) throws JsonProcessingException {
        Product productDO = repository.save(productEvent.getProduct());
        ProductEvent event=new ProductEvent("CreateProduct", productDO);

        String serializedMessage = objectMapper.writeValueAsString(event);

        kafkaTemplate.send("product-event-topic", serializedMessage);
        return productDO;
    }

    public Product updateProduct(long id,ProductEvent productEvent) throws JsonProcessingException {

        Optional<Product> existingProduct = repository.findById(id);
        Product newProduct=productEvent.getProduct();
        if (existingProduct.isPresent()) {
            existingProduct.get().setName(newProduct.getName());
            existingProduct.get().setPrice(newProduct.getPrice());
            existingProduct.get().setDescription(newProduct.getDescription());
            Product productDO = repository.save(existingProduct.get());
            ProductEvent event=new ProductEvent("UpdateProduct", productDO);

            String serializedMessage = objectMapper.writeValueAsString(event);

            kafkaTemplate.send("product-event-topic", serializedMessage);
            return productDO;
        } else {
            return new Product();
        }
    }

}
