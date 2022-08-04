package com.MyProject.ToyWorld.service.impl;

import com.MyProject.ToyWorld.dto.admin.AddNewProductDTO;
import com.MyProject.ToyWorld.entity.Product;
import com.MyProject.ToyWorld.repository.ProductRepository;
import com.MyProject.ToyWorld.service.CategoryService;
import com.MyProject.ToyWorld.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ProductServiceImpl implements ProductService {
    private ProductRepository productRepository;
    private CategoryService categoryService;

    public ProductServiceImpl(ProductRepository productRepository, CategoryService categoryService) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
    }

    @Override
    public List<Product> findAllProduct() {
        return productRepository.findAll();
    }

    @Override
    public void addNewProduct(AddNewProductDTO addNewProductDTO) {
        Product product = new Product();
        product.setProductName(addNewProductDTO.getProductName());
        product.setProductDescription(addNewProductDTO.getProductDescription());
        product.setPrice(addNewProductDTO.getPrice());
        product.setQuantity(addNewProductDTO.getQuantity());
        product.setSize(addNewProductDTO.getSize());
        System.out.println(addNewProductDTO.getCategoryID());
        product.setCategory(categoryService.findById(addNewProductDTO.getCategoryID()));
        productRepository.save(product);
    }
}
