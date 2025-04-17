package com.ecommerce.productservice.service;

import com.ecommerce.productservice.model.Product;
import com.ecommerce.productservice.repo.ProductRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepo productRepo;

    @InjectMocks
    private ProductService productService;

    @Test
    void getAllProducts_shouldReturnListOfProducts() {
        // Arrange
        Product product1 = new Product();
        product1.setId("1");
        product1.setName("Product 1");
        product1.setPrice(10.0);

        Product product2 = new Product();
        product2.setId("2");
        product2.setName("Product 2");
        product2.setPrice(20.0);

        List<Product> expectedProducts = Arrays.asList(product1, product2);
        when(productRepo.findAll()).thenReturn(expectedProducts);

        // Act
        List<Product> actualProducts = productService.getAllProducts();

        // Assert
        assertEquals(expectedProducts.size(), actualProducts.size());
        assertEquals(expectedProducts.get(0).getId(), actualProducts.get(0).getId());
        assertEquals(expectedProducts.get(0).getName(), actualProducts.get(0).getName());
        assertEquals(expectedProducts.get(0).getPrice(), actualProducts.get(0).getPrice());
        assertEquals(expectedProducts.get(1).getId(), actualProducts.get(1).getId());
        assertEquals(expectedProducts.get(1).getName(), actualProducts.get(1).getName());
        assertEquals(expectedProducts.get(1).getPrice(), actualProducts.get(1).getPrice());
        verify(productRepo, times(1)).findAll(); // Verify that findAll was called once
    }

    @Test
    void getProductById_shouldReturnProduct_whenIdExists() {
        // Arrange
        String productId = "123";
        Product expectedProduct = new Product();
        expectedProduct.setId(productId);
        expectedProduct.setName("Test Product");
        expectedProduct.setPrice(15.0);

        when(productRepo.findById(productId)).thenReturn(Optional.of(expectedProduct));

        // Act
        Product actualProduct = productService.getProductById(productId);

        // Assert
        assertEquals(expectedProduct.getId(), actualProduct.getId());
        assertEquals(expectedProduct.getName(), actualProduct.getName());
        assertEquals(expectedProduct.getPrice(), actualProduct.getPrice());
        verify(productRepo, times(1)).findById(productId); // Verify findById was called once with the correct ID
    }

    @Test
    void getProductById_shouldReturnNull_whenIdDoesNotExist() {
        // Arrange
        String productId = "nonExistentId";
        when(productRepo.findById(productId)).thenReturn(Optional.empty());

        // Act
        Product actualProduct = productService.getProductById(productId);

        // Assert
        assertNull(actualProduct);
        verify(productRepo, times(1)).findById(productId); // Verify findById was called once with the correct ID
    }

    @Test
    void addProduct_shouldSaveProductAndReturnSavedProduct() {
        // Arrange
        Product productToSave = new Product();
        productToSave.setName("New Product");
        productToSave.setPrice(25.0);

        Product savedProduct = new Product();
        savedProduct.setId("456");
        savedProduct.setName("New Product");
        savedProduct.setPrice(25.0);
        when(productRepo.save(productToSave)).thenReturn(savedProduct);

        // Act
        Product actualSavedProduct = productService.addProduct(productToSave);

        // Assert
        assertEquals(savedProduct.getId(), actualSavedProduct.getId());
        assertEquals(savedProduct.getName(), actualSavedProduct.getName());
        assertEquals(savedProduct.getPrice(), actualSavedProduct.getPrice());
        verify(productRepo, times(1)).save(productToSave); // Verify save was called once with the correct product
    }
}