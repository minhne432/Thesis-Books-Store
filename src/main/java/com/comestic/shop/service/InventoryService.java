package com.comestic.shop.service;

import com.comestic.shop.model.Inventory;
import com.comestic.shop.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    public List<Inventory> getAllInventories() {
        return inventoryRepository.findAll();
    }

    public Optional<Inventory> getInventoryById(Long id) {
        return inventoryRepository.findById(id);
    }

    public Inventory addInventory(Inventory inventory) {
        return inventoryRepository.save(inventory);
    }

    public Inventory updateInventory(Long id, Inventory inventoryDetails) {
        Optional<Inventory> optionalInventory = inventoryRepository.findById(id);
        if (optionalInventory.isPresent()) {
            Inventory inventory = optionalInventory.get();
            inventory.setBranch(inventoryDetails.getBranch());
            inventory.setProduct(inventoryDetails.getProduct());
            inventory.setQuantity(inventoryDetails.getQuantity());
            inventory.setLastUpdatedDate(inventoryDetails.getLastUpdatedDate());
            return inventoryRepository.save(inventory);
        } else {
            return null; // Hoặc bạn có thể ném ra ngoại lệ tùy theo logic
        }
    }

    public void deleteInventory(Long id) {
        inventoryRepository.deleteById(id);
    }
}
