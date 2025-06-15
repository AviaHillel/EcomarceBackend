package com.example.product_api.controller;

import com.example.product_api.model.Product;
import com.example.product_api.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateProduct() throws Exception {
        Product input = new Product("Keyboard", "Gaming keyboard", 20, new BigDecimal("199.99"));
        Product saved = new Product("Keyboard", "Gaming keyboard", 20, new BigDecimal("199.99"));
        saved.setId(1L);

        when(productService.createProduct(any(Product.class))).thenReturn(saved);

        mockMvc.perform(post("/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Keyboard"));
    }

    @Test
    void testGetAllProducts() throws Exception {
        Product p1 = new Product("Mouse", "Wireless", 10, new BigDecimal("49.99"));
        Product p2 = new Product("Monitor", "27 inch", 5, new BigDecimal("999.99"));
        when(productService.getAllProducts()).thenReturn(List.of(p1, p2));

        mockMvc.perform(get("/product"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void testGetProductById() throws Exception {
        Product p = new Product("Desk", "Wooden desk", 2, new BigDecimal("299.99"));
        p.setId(5L);
        when(productService.getProductById(5L)).thenReturn(p);

        mockMvc.perform(get("/product/5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Desk"));
    }

    @Test
    void testUpdateProduct() throws Exception {
        Product updated = new Product("Tablet", "Updated description", 7, new BigDecimal("599.99"));
        updated.setId(3L);

        when(productService.updateProduct(Mockito.eq(3L), any(Product.class))).thenReturn(updated);

        mockMvc.perform(put("/product/3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Tablet"));
    }

    @Test
    void testDeleteProduct() throws Exception {
        mockMvc.perform(delete("/product/1"))
                .andExpect(status().isNoContent());
    }
}
