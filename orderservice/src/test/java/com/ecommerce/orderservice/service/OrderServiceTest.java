package com.ecommerce.orderservice.service;

import com.ecommerce.orderservice.client.ProductClient;
import com.ecommerce.orderservice.dto.Product;
import com.ecommerce.orderservice.model.Order;
import com.ecommerce.orderservice.repo.OrderRep;
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
class OrderServiceTest {

    @Mock
    private OrderRep orderRep;

    @Mock
    private ProductClient productClient;

    @InjectMocks
    private OrderService orderService;

    @Test
    void getAllOrders_shouldReturnListOfOrders() {
        // Arrange
        Order order1 = new Order();
        order1.setId("1");
        order1.setProductId("prod1");
        order1.setQuantity(2);

        Order order2 = new Order();
        order2.setId("2");
        order2.setProductId("prod2");
        order2.setQuantity(1);

        List<Order> expectedOrders = Arrays.asList(order1, order2);
        when(orderRep.findAll()).thenReturn(expectedOrders);

        // Act
        List<Order> actualOrders = orderService.getAllOrders();

        // Assert
        assertEquals(expectedOrders.size(), actualOrders.size());
        assertEquals(expectedOrders.get(0).getId(), actualOrders.get(0).getId());
        assertEquals(expectedOrders.get(0).getProductId(), actualOrders.get(0).getProductId());
        assertEquals(expectedOrders.get(0).getQuantity(), actualOrders.get(0).getQuantity());
        assertEquals(expectedOrders.get(1).getId(), actualOrders.get(1).getId());
        assertEquals(expectedOrders.get(1).getProductId(), actualOrders.get(1).getProductId());
        assertEquals(expectedOrders.get(1).getQuantity(), actualOrders.get(1).getQuantity());
        verify(orderRep, times(1)).findAll();
    }

    @Test
    void getOrderById_shouldReturnOrder_whenOrderExists() {
        // Arrange
        String orderId = "123";
        Order expectedOrder = new Order();
        expectedOrder.setId(orderId);
        expectedOrder.setProductId("testProd");
        expectedOrder.setQuantity(3);
        when(orderRep.findById(orderId)).thenReturn(Optional.of(expectedOrder));

        // Act
        Order actualOrder = orderService.getOrderById(orderId);

        // Assert
        assertNotNull(actualOrder);
        assertEquals(expectedOrder.getId(), actualOrder.getId());
        assertEquals(expectedOrder.getProductId(), actualOrder.getProductId());
        assertEquals(expectedOrder.getQuantity(), actualOrder.getQuantity());
        verify(orderRep, times(1)).findById(orderId);
    }

    @Test
    void getOrderById_shouldReturnNull_whenOrderDoesNotExist() {
        // Arrange
        String orderId = "nonExistent";
        when(orderRep.findById(orderId)).thenReturn(Optional.empty());

        // Act
        Order actualOrder = orderService.getOrderById(orderId);

        // Assert
        assertNull(actualOrder);
        verify(orderRep, times(1)).findById(orderId);
    }

    @Test
    void addOrder_shouldSaveOrderAndReturnSavedOrder_whenProductExists() {
        // Arrange
        Order orderToSave = new Order();
        orderToSave.setProductId("validProd");
        orderToSave.setQuantity(1);

        Product mockProduct = new Product();
        mockProduct.setId("validProd");
        mockProduct.setName("Test Product");
        mockProduct.setPrice(10.0);

        Order savedOrder = new Order();
        savedOrder.setId("456"); // Assume ID is generated on save
        savedOrder.setProductId("validProd");
        savedOrder.setQuantity(1);

        when(productClient.getProductById("validProd")).thenReturn(mockProduct);
        when(orderRep.save(orderToSave)).thenReturn(savedOrder);

        // Act
        Order actualSavedOrder = orderService.addOrder(orderToSave);

        // Assert
        assertNotNull(actualSavedOrder);
        assertEquals(savedOrder.getId(), actualSavedOrder.getId());
        assertEquals(orderToSave.getProductId(), actualSavedOrder.getProductId());
        assertEquals(orderToSave.getQuantity(), actualSavedOrder.getQuantity());
        verify(productClient, times(1)).getProductById("validProd");
        verify(orderRep, times(1)).save(orderToSave);
    }

    @Test
    void addOrder_shouldThrowException_whenProductDoesNotExist() {
        // Arrange
        Order orderToSave = new Order();
        orderToSave.setProductId("invalidProd");
        orderToSave.setQuantity(1);

        when(productClient.getProductById("invalidProd")).thenReturn(null);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> orderService.addOrder(orderToSave), "Product not found");
        verify(productClient, times(1)).getProductById("invalidProd");
        verify(orderRep, never()).save(any());
    }
}