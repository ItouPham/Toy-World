package com.MyProject.ToyWorld.service.impl;

import com.MyProject.ToyWorld.entity.Role;
import com.MyProject.ToyWorld.repository.RoleRepository;
import com.MyProject.ToyWorld.service.RoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.management.relation.RoleNotFoundException;
import java.util.List;

@Service
@Transactional
public class RoleServiceIml implements RoleService {

    private RoleRepository roleRepository;

    public RoleServiceIml(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role findByRoleName(String roleName) {
        return roleRepository.findByRoleName(roleName).orElseThrow(() -> new RuntimeException("Role not found"));
    }

    @Override
    public List<Role> findAllRole() {
        return roleRepository.findAll();
    }

    @Override
    public Role findById(String Id) {
        return roleRepository.findById(Id).orElseThrow(() -> new RuntimeException("Role not found"));
    }
}
