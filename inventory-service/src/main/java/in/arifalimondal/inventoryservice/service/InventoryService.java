package in.arifalimondal.inventoryservice.service;

import in.arifalimondal.inventoryservice.dto.InventoryResponse;
import in.arifalimondal.inventoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    @Transactional(readOnly = false)
    public boolean checkStock(String skuCode){
        return inventoryRepository.findBySkuCode(skuCode).isPresent();
    }

    @Transactional(readOnly = false)
    public List<InventoryResponse> isInStock(List<String> skuCode){
        log.info("Checking Stock");
        return inventoryRepository.findBySkuCodeIn(skuCode)
                .stream().map(inventory -> InventoryResponse.builder()
                                                .skuCode(inventory.getSkuCode())
                                                .isInStock(inventory.getQuantity()>0)
                                                .build())
                .toList();
    }
}
