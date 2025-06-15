package com.example.product_api.controller;

import com.example.product_api.model.Product;
import com.example.product_api.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@Validated @RequestBody Product product){
        return new ResponseEntity<>(service.createProduct(product), HttpStatus.CREATED);
    }

    @PostMapping("/bulk")
    public ResponseEntity<List<Product>> createProducts(@Validated @RequestBody List<Product> products){
        List<Product> createdProducts = new ArrayList<>();
        for (Product product : products) {
            createdProducts.add(service.createProduct(product));
        }
        return new ResponseEntity<>(createdProducts, HttpStatus.CREATED);
    }

    @GetMapping
    public List<Product> getAllProducts(){
        return service.getAllProducts();
    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id){
        return service.getProductById(id);
    }

    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable Long id, @Validated @RequestBody Product product){
        return service.updateProduct(id, product);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id){
        service.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAllProducts(){
        service.deleteAllProduct();
        return ResponseEntity.noContent().build();
    }

}
