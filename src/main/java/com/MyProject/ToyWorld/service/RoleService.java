package com.MyProject.ToyWorld.service;

import com.MyProject.ToyWorld.entity.Role;

import java.util.List;

public interface RoleService {
    Role findByRoleName(String roleName);
    List<Role> findAllRole();
    Role findById(String Id);
}
