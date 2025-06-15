package com.example.product_api.service;

import com.example.product_api.exception.ResourceNotFoundException;
import com.example.product_api.model.Product;
import com.example.product_api.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product sampleProduct;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        sampleProduct = new Product("Laptop", "High-end gaming laptop", 5, BigDecimal.valueOf(1500.00));
        sampleProduct.setId(1L);
    }

    @Test
    void testCreateProduct() {
        when(productRepository.save(sampleProduct)).thenReturn(sampleProduct);
        Product result = productService.createProduct(sampleProduct);
        assertEquals(sampleProduct.getName(), result.getName());
        verify(productRepository, times(1)).save(sampleProduct);
    }

    @Test
    void testGetAllProducts() {
        when(productRepository.findAll()).thenReturn(Arrays.asList(sampleProduct));
        List<Product> products = productService.getAllProducts();
        assertEquals(1, products.size());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void testGetProductById_Found() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(sampleProduct));
        Product product = productService.getProductById(1L);
        assertNotNull(product);
        assertEquals("Laptop", product.getName());
    }

    @Test
    void testGetProductById_NotFound() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> productService.getProductById(1L));
    }

    @Test
    void testUpdateProduct() {
        Product updatedProduct = new Product("Monitor", "27 inch monitor", 10, BigDecimal.valueOf(300));
        when(productRepository.findById(1L)).thenReturn(Optional.of(sampleProduct));
        when(productRepository.save(any(Product.class))).thenReturn(updatedProduct);

        Product result = productService.updateProduct(1L, updatedProduct);
        assertEquals("Monitor", result.getName());
        assertEquals(10, result.getStockQuantity());
    }

    @Test
    void testDeleteProduct() {
        doNothing().when(productRepository).deleteById(1L);
        productService.deleteProduct(1L);
        verify(productRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteAllProducts() {
        doNothing().when(productRepository).deleteAll();
        productService.deleteAllProduct();
        verify(productRepository, times(1)).deleteAll();
    }
}