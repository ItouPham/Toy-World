package com.MyProject.ToyWorld.service;

import com.MyProject.ToyWorld.dto.admin.AddNewProductDTO;
import com.MyProject.ToyWorld.entity.Product;

import java.util.List;

public interface ProductService {
    List<Product> findAllProduct();
    void addNewProduct(AddNewProductDTO addNewProductDTO);
}
