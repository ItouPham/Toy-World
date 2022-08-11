package com.MyProject.ToyWorld.service.impl;

import com.MyProject.ToyWorld.dto.admin.AddNewProductDTO;
import com.MyProject.ToyWorld.entity.Product;
import com.MyProject.ToyWorld.repository.ProductRepository;
import com.MyProject.ToyWorld.service.CategoryService;
import com.MyProject.ToyWorld.service.ProductService;
import com.MyProject.ToyWorld.service.StorageService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public Page<Product> findAll(int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber - 1,10);
        return productRepository.findAll(pageable);
    }

    @Override
    public Page<Product> findAllProduct(int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber - 1,9);
        return productRepository.findAll(pageable);
    }

    @Override
    public Product findProductById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product Not Found"));
    }

}
