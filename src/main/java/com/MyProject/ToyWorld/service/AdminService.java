package com.MyProject.ToyWorld.service;

import com.MyProject.ToyWorld.dto.admin.AddNewUserDTO;
import com.MyProject.ToyWorld.dto.admin.EditUserDTO;

public interface AdminService {
    void addNewUser(AddNewUserDTO addNewUserDTO);
    void editUser(EditUserDTO editUserDTO);
    void deleteUser(String Id);
}
