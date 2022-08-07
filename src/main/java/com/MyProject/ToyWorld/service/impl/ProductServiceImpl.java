package com.MyProject.ToyWorld.service.impl;

import com.MyProject.ToyWorld.dto.admin.AddNewProductDTO;
import com.MyProject.ToyWorld.entity.Product;
import com.MyProject.ToyWorld.repository.ProductRepository;
import com.MyProject.ToyWorld.service.CategoryService;
import com.MyProject.ToyWorld.service.ProductService;
import com.MyProject.ToyWorld.service.StorageService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
public class ProductServiceImpl implements ProductService {
    private ProductRepository productRepository;
    private CategoryService categoryService;
    private StorageService storageService;

    public ProductServiceImpl(ProductRepository productRepository, CategoryService categoryService, StorageService storageService) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
        this.storageService = storageService;
    }

    @Override
    public List<Product> findAllProduct() {
        return productRepository.findAll();
    }

    @Override
    public Product findProductById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product Not Found"));
    }

}
