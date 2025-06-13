package com.example.product_api.service;

import com.example.product_api.exception.ResourceNotFoundException;
import com.example.product_api.model.Product;
import com.example.product_api.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService{

    private final ProductRepository repo;

    public ProductServiceImpl(ProductRepository repo) {
        this.repo = repo;
    }

    @Override
    public Product createProdect(Product product) {
        return repo.save(product);
    }

    @Override
    public List<Product> getAllProducts() {
        return repo.findAll();
    }

    @Override
    public Product getProductById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
    }

    @Override
    public Product updateProduct(Long id, Product product) {
        Product prodToUpdate = getProductById(id);
        prodToUpdate.setName(product.getName());
        prodToUpdate.setStockQuantity(product.getStockQuantity());
        prodToUpdate.setPrice(product.getPrice());
        prodToUpdate.setDescription(product.getDescription());
        return repo.save(prodToUpdate);
    }

    @Override
    public void deleteProduct(Long id) {
        repo.deleteById(id);
    }

    @Override
    public void deleteAllProduct() {
        repo.deleteAll();
    }

}
