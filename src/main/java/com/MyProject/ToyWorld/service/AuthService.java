package com.MyProject.ToyWorld.service;

import com.MyProject.ToyWorld.dto.EditProfileDTO;
import com.MyProject.ToyWorld.dto.RegisterDTO;
import com.MyProject.ToyWorld.entity.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface AuthService {
    Page<User> findAll(int pageNumber);
    User findById(String Id);
    User findUserByEmail(String email);
    boolean isExistsByEmail(String email);
    void register(RegisterDTO registerDTO);
    void editProfile(EditProfileDTO editProfileDTO);
    void deleteUser(String id);
}
