package com.MyProject.ToyWorld.service;

import com.MyProject.ToyWorld.dto.RegisterDTO;
import com.MyProject.ToyWorld.entity.User;

import java.util.List;

public interface AuthService {
    List<User> findAll();
    User findById(String Id);
    User findUserByEmail(String email);
    boolean isExistsByEmail(String email);
    void register(RegisterDTO registerDTO);
    void deleteUser(String id);
}
