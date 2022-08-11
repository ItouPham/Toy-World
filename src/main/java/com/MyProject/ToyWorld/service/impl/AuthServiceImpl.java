package com.MyProject.ToyWorld.service.impl;

import com.MyProject.ToyWorld.constant.Role;
import com.MyProject.ToyWorld.dto.EditProfileDTO;
import com.MyProject.ToyWorld.dto.RegisterDTO;
import com.MyProject.ToyWorld.entity.User;
import com.MyProject.ToyWorld.repository.UserRepository;
import com.MyProject.ToyWorld.service.AuthService;
import com.MyProject.ToyWorld.service.RoleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
@Transactional
public class AuthServiceImpl implements AuthService {
    private UserRepository userRepository;
    private RoleService roleService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public AuthServiceImpl(UserRepository userRepository, RoleService roleService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public Page<User> findAll(int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber - 1,10);
        return userRepository.findAll(pageable);
    }

    @Override
    public User findById(String Id) {
        return userRepository.findById(Id).orElseThrow(() -> new RuntimeException("User Not Found"));
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User Not Found"));
    }

    @Override
    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }

    @Override
    public boolean isExistsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public void register(RegisterDTO registerDTO) {
        User user = new User();
        user.setEmail(registerDTO.getEmail());
        user.setRoles(Collections.singleton(roleService.findByRoleName(Role.CUSTOMER.name())));
        user.setFirstName(registerDTO.getFirstName());
        user.setLastName(registerDTO.getLastName());
        user.setPassword(bCryptPasswordEncoder.encode(registerDTO.getPassword()));
        userRepository.save(user);
    }

    @Override
    public void editProfile(EditProfileDTO editProfileDTO) {
        User user = new User();
        user.setId(editProfileDTO.getId());
        user.setEmail(editProfileDTO.getEmail());
        user.setFirstName(editProfileDTO.getFirstName());
        user.setLastName(editProfileDTO.getLastName());
        user.setTelephone(editProfileDTO.getTelephone());
        user.setAddress(editProfileDTO.getAddress());
        user.setRoles(Collections.singleton(roleService.findById(editProfileDTO.getRoleID())));
        user.setPassword(editProfileDTO.getPassword());
        userRepository.save(user);
    }
}
