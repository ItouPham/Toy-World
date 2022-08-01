package com.MyProject.ToyWorld.service.impl;

import com.MyProject.ToyWorld.dto.admin.AddNewUserDTO;
import com.MyProject.ToyWorld.dto.admin.EditUserDTO;
import com.MyProject.ToyWorld.entity.User;
import com.MyProject.ToyWorld.repository.UserRepository;
import com.MyProject.ToyWorld.service.AdminService;
import com.MyProject.ToyWorld.service.RoleService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
@Transactional

public class AdminServiceImpl implements AdminService {
    private UserRepository userRepository;
    private RoleService roleService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public AdminServiceImpl(UserRepository userRepository, RoleService roleService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
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
