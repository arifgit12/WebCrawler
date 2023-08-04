package in.arifalimondal.inventoryservice.controller;

import in.arifalimondal.inventoryservice.dto.InventoryResponse;
import in.arifalimondal.inventoryservice.service.InventoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@Slf4j
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @GetMapping("/check/{sku-code}")
    @ResponseStatus(HttpStatus.OK)
    public boolean checkInStock(@PathVariable("sku-code") String skuCode){
        log.info("Check Inventory Stocked:" + skuCode);
        return inventoryService.checkStock(skuCode);
    }

    // http://localhost:8082/api/inventory/iphone-13,iphone13-red

    // http://localhost:8082/api/inventory?skuCode=iphone-13&skuCode=iphone13-red
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponse> isInStock(@RequestParam List<String> skuCode) {
        log.info("Received inventory check request for skuCode: {}", skuCode);
        return inventoryService.isInStock(skuCode);
    }
}
