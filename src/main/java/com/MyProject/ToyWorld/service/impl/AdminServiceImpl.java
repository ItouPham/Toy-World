package com.MyProject.ToyWorld.service.impl;

import com.MyProject.ToyWorld.dto.admin.AddNewProductDTO;
import com.MyProject.ToyWorld.dto.admin.AddNewUserDTO;
import com.MyProject.ToyWorld.dto.admin.EditProductDTO;
import com.MyProject.ToyWorld.dto.admin.EditUserDTO;
import com.MyProject.ToyWorld.entity.Category;
import com.MyProject.ToyWorld.entity.Product;
import com.MyProject.ToyWorld.entity.User;
import com.MyProject.ToyWorld.repository.ProductRepository;
import com.MyProject.ToyWorld.repository.UserRepository;
import com.MyProject.ToyWorld.service.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.UUID;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {
    private UserRepository userRepository;
    private RoleService roleService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private CategoryService categoryService;
    private StorageService storageService;
    private ProductRepository productRepository;
    private ProductService productService;

    public AdminServiceImpl(UserRepository userRepository, RoleService roleService, BCryptPasswordEncoder bCryptPasswordEncoder, CategoryService categoryService, StorageService storageService, ProductRepository productRepository, ProductService productService) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.categoryService = categoryService;
        this.storageService = storageService;
        this.productRepository = productRepository;
        this.productService = productService;
    }

    @Override
    public void addNewProduct(AddNewProductDTO addNewProductDTO) {
        Product product = new Product();
        product.setProductName(addNewProductDTO.getProductName());
        product.setProductDescription(addNewProductDTO.getProductDescription());
        product.setPrice(addNewProductDTO.getPrice());
        product.setQuantity(addNewProductDTO.getQuantity());
        product.setSize(addNewProductDTO.getSize());
        product.setCategory(categoryService.findById(addNewProductDTO.getCategoryID()));
        if (!addNewProductDTO.getImageFile().isEmpty()) {
            UUID uuid = UUID.randomUUID();
            String uuString = uuid.toString();
            product.setProductImage(storageService.getStoredFilename(addNewProductDTO.getImageFile(), uuString));
            storageService.store(addNewProductDTO.getImageFile(), product.getProductImage());
        }
        productRepository.save(product);
    }

    @Override
    public void editProduct(EditProductDTO editProductDTO) {
        Product product = productService.findProductById(editProductDTO.getId());
        Category category = categoryService.findById(editProductDTO.getCategoryID());
        if (category.getId() != product.getCategory().getId()) {
            product.setCategory(category);
        }
        product.setProductName(editProductDTO.getProductName());
        product.setProductDescription(editProductDTO.getProductDescription());
        product.setPrice(editProductDTO.getPrice());
        product.setSize(editProductDTO.getSize());
        product.setQuantity(editProductDTO.getQuantity());
        if (!editProductDTO.getImageFile().isEmpty()) {
            UUID uuid = UUID.randomUUID();
            String uuString = uuid.toString();
            product.setProductImage(storageService.getStoredFilename(editProductDTO.getImageFile(), uuString));
            storageService.store(editProductDTO.getImageFile(), product.getProductImage());
        }
        productRepository.save(product);
    }

    @Override
    public void deleteProduct(Long Id) {
        productRepository.deleteById(Id);
    }

    @Override
    public void addNewUser(AddNewUserDTO addNewUserDTO) {
        User user = new User();
        user.setEmail(addNewUserDTO.getEmail());
        user.setRoles(Collections.singleton(roleService.findById(addNewUserDTO.getRoleID())));
        user.setFirstName(addNewUserDTO.getFirstName());
        user.setLastName(addNewUserDTO.getLastName());
        user.setAddress(addNewUserDTO.getAddress());
        user.setTelephone(addNewUserDTO.getTelephone());
        user.setPassword(bCryptPasswordEncoder.encode(addNewUserDTO.getPassword()));
        userRepository.save(user);
    }

    @Override
    public void editUser(EditUserDTO editUserDTO) {
        User user = new User();
        user.setId(editUserDTO.getId());
        user.setEmail(editUserDTO.getEmail());
        user.setFirstName(editUserDTO.getFirstName());
        user.setLastName(editUserDTO.getLastName());
        user.setTelephone(editUserDTO.getTelephone());
        user.setAddress(editUserDTO.getAddress());
        user.setRoles(Collections.singleton(roleService.findById(editUserDTO.getRoleID())));
        user.setPassword(bCryptPasswordEncoder.encode(editUserDTO.getPassword()));
        userRepository.save(user);
    }

    @Override
    public void deleteUser(String Id) {
        userRepository.deleteById(Id);
    }
}
