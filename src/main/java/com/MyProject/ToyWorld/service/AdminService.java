package com.MyProject.ToyWorld.service;

import com.MyProject.ToyWorld.dto.admin.AddNewProductDTO;
import com.MyProject.ToyWorld.dto.admin.AddNewUserDTO;
import com.MyProject.ToyWorld.dto.admin.EditProductDTO;
import com.MyProject.ToyWorld.dto.admin.EditUserDTO;

public interface AdminService {
    void addNewProduct(AddNewProductDTO addNewProductDTO);
    void editProduct(EditProductDTO editProductDTO);
    void deleteProduct(Long Id);
    void addNewUser(AddNewUserDTO addNewUserDTO);
    void editUser(EditUserDTO editUserDTO);
    void deleteUser(String Id);
}
