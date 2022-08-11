package com.MyProject.ToyWorld.service;

import com.MyProject.ToyWorld.dto.admin.AddNewProductDTO;
import com.MyProject.ToyWorld.entity.Product;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {
    Page<Product> findAllProduct(int pageNumber);
    Page<Product> findAll(int pageNumber);
    Product findProductById(Long id);
}
