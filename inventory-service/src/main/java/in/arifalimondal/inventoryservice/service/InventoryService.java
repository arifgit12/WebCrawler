package in.arifalimondal.inventoryservice.service;

import in.arifalimondal.inventoryservice.dto.InventoryResponse;
import in.arifalimondal.inventoryservice.model.Inventory;
import in.arifalimondal.inventoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    @Transactional(readOnly = true)
    public boolean checkStock(String skuCode){
        log.info("Checking Stock of {}", skuCode);
        Optional<Inventory> inventory = inventoryRepository.findBySkuCode(skuCode);

        if (inventory.isPresent()){
            return true;
        } else {
            log.error("Item skuCode {} not found", skuCode);
            return false;
        }
    }

    @Transactional(readOnly = true)
    public List<InventoryResponse> isInStock(List<String> skuCode){
        log.info("Checking Stock");
        return inventoryRepository.findBySkuCodeIn(skuCode).stream()
                .map(inventory ->
                        InventoryResponse.builder()
                                .skuCode(inventory.getSkuCode())
                                .isInStock(inventory.getQuantity() > 0)
                                .build()
                ).toList();
    }
}
