package com.ecommerce.inventoryservice.service;

import com.ecommerce.inventoryservice.model.Inventory;
import com.ecommerce.inventoryservice.repo.InventoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryService {

    @Autowired
    private InventoryRepo inventoryRepo;

    public List<Inventory> getAllInventory() {
        return inventoryRepo.findAll();
    }

    public Inventory getInventoryByProductId(String productId) {
        return inventoryRepo.findById(productId).orElse(null);
    }

    public Inventory updateInventory(String productId, int quantity) {
        Inventory inventory = inventoryRepo.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        inventory.setQuantity(quantity);
        return inventoryRepo.save(inventory);
    }
}
