package com.MyProject.ToyWorld.service.impl;

import com.MyProject.ToyWorld.constant.Role;
import com.MyProject.ToyWorld.dto.RegisterDTO;
import com.MyProject.ToyWorld.entity.User;
import com.MyProject.ToyWorld.repository.UserRepository;
import com.MyProject.ToyWorld.service.AuthService;
import com.MyProject.ToyWorld.service.RoleService;
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
    public List<User> findAll() {
        return userRepository.findAll();
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
}
