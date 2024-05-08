package com.food.app.user.services;

import com.food.app.user.model.Role;

import java.util.List;
import java.util.Optional;

public interface RoleService {

    Role create(Role role);
    Optional<Role> findByName(String name);

    List<Role> findAll();

    Role updateRole(Role role);

    void deleteRole(String name);
}
