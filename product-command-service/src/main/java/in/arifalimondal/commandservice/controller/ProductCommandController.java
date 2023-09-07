package in.arifalimondal.commandservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import in.arifalimondal.commandservice.dto.ProductEvent;
import in.arifalimondal.commandservice.entity.Product;
import in.arifalimondal.commandservice.service.ProductCommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductCommandController {

    @Autowired
    private ProductCommandService commandService;

    @PostMapping
    public Product createProduct(@RequestBody ProductEvent productEvent) throws JsonProcessingException {
        return commandService.createProduct(productEvent);
    }

    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable long id,
                                 @RequestBody ProductEvent productEvent) throws JsonProcessingException {
        return commandService.updateProduct(id, productEvent);
    }
}
