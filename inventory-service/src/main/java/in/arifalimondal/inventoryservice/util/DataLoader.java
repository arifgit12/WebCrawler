//package in.arifalimondal.inventoryservice.util;
//
//import in.arifalimondal.inventoryservice.model.Inventory;
//import in.arifalimondal.inventoryservice.repository.InventoryRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//@Component
//@RequiredArgsConstructor
//public class DataLoader implements CommandLineRunner {
//    private final InventoryRepository inventoryRepository;
//    @Override
//    public void run(String... args) throws Exception {
//        Inventory inventory = new Inventory();
//        inventory.setSkuCode("iphone12");
//        inventory.setQuantity(100);
//
//        Inventory inventory1 = new Inventory();
//        inventory1.setSkuCode("iphone12_red");
//        inventory1.setQuantity(2);
//
//        inventoryRepository.save(inventory);
//        inventoryRepository.save(inventory1);
//    }
//}
