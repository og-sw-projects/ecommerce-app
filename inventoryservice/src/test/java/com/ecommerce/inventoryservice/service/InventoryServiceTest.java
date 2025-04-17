package com.ecommerce.inventoryservice.service;

import com.ecommerce.inventoryservice.model.Inventory;
import com.ecommerce.inventoryservice.repo.InventoryRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InventoryServiceTest {

    @Mock
    private InventoryRepo inventoryRepo;

    @InjectMocks
    private InventoryService inventoryService;

    @Test
    void getAllInventory_shouldReturnListOfInventory() {
        // Arrange
        Inventory inventory1 = new Inventory();
        inventory1.setProductId("1");
        inventory1.setQuantity(10);

        Inventory inventory2 = new Inventory();
        inventory2.setProductId("2");
        inventory2.setQuantity(25);

        List<Inventory> expectedInventory = Arrays.asList(inventory1, inventory2);
        when(inventoryRepo.findAll()).thenReturn(expectedInventory);

        // Act
        List<Inventory> actualInventory = inventoryService.getAllInventory();

        // Assert
        assertEquals(expectedInventory.size(), actualInventory.size());
        assertEquals(expectedInventory.get(0).getProductId(), actualInventory.get(0).getProductId());
        assertEquals(expectedInventory.get(0).getQuantity(), actualInventory.get(0).getQuantity());
        assertEquals(expectedInventory.get(1).getProductId(), actualInventory.get(1).getProductId());
        assertEquals(expectedInventory.get(1).getQuantity(), actualInventory.get(1).getQuantity());
        verify(inventoryRepo, times(1)).findAll();
    }

    @Test
    void getInventoryByProductId_shouldReturnInventory_whenProductExists() {
        // Arrange
        String productId = "123";
        Inventory expectedInventory = new Inventory();
        expectedInventory.setProductId(productId);
        expectedInventory.setQuantity(15);
        when(inventoryRepo.findById(productId)).thenReturn(Optional.of(expectedInventory));

        // Act
        Inventory actualInventory = inventoryService.getInventoryByProductId(productId);

        // Assert
        assertNotNull(actualInventory);
        assertEquals(expectedInventory.getProductId(), actualInventory.getProductId());
        assertEquals(expectedInventory.getQuantity(), actualInventory.getQuantity());
        verify(inventoryRepo, times(1)).findById(productId);
    }

    @Test
    void getInventoryByProductId_shouldReturnNull_whenProductDoesNotExist() {
        // Arrange
        String productId = "nonExistent";
        when(inventoryRepo.findById(productId)).thenReturn(Optional.empty());

        // Act
        Inventory actualInventory = inventoryService.getInventoryByProductId(productId);

        // Assert
        assertNull(actualInventory);
        verify(inventoryRepo, times(1)).findById(productId);
    }

    @Test
    void updateInventory_shouldUpdateQuantityAndReturnUpdatedInventory_whenProductExists() {
        // Arrange
        String productId = "456";
        int newQuantity = 30;
        Inventory existingInventory = new Inventory();
        existingInventory.setProductId(productId);
        existingInventory.setQuantity(20);

        Inventory updatedInventory = new Inventory();
        updatedInventory.setProductId(productId);
        updatedInventory.setQuantity(newQuantity);

        when(inventoryRepo.findById(productId)).thenReturn(Optional.of(existingInventory));
        when(inventoryRepo.save(existingInventory)).thenReturn(updatedInventory); // Mock the save operation

        // Act
        Inventory resultInventory = inventoryService.updateInventory(productId, newQuantity);

        // Assert
        assertNotNull(resultInventory);
        assertEquals(productId, resultInventory.getProductId());
        assertEquals(newQuantity, resultInventory.getQuantity());
        verify(inventoryRepo, times(1)).findById(productId);
        verify(inventoryRepo, times(1)).save(existingInventory);
    }

    @Test
    void updateInventory_shouldThrowException_whenProductDoesNotExist() {
        // Arrange
        String productId = "nonExistent";
        int newQuantity = 50;
        when(inventoryRepo.findById(productId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> inventoryService.updateInventory(productId, newQuantity));
        verify(inventoryRepo, times(1)).findById(productId);
        verify(inventoryRepo, never()).save(any()); // Ensure save is not called
    }
}