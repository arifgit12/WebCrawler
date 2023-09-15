package in.arifalimondal.productservice.controller;

import in.arifalimondal.productservice.dto.ProductRequest;
import in.arifalimondal.productservice.dto.ProductResponse;
import in.arifalimondal.productservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createProduct(@RequestBody ProductRequest productRequest) {
        productService.createProduct(productRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> getAllProducts(){
        return productService.getAllProducts();
    }

    @GetMapping("/test")
    //@PreAuthorize("hasRole('ORDER')")
    public String adminOrders() {
        return "This is the admin orders page.";
    }
}
