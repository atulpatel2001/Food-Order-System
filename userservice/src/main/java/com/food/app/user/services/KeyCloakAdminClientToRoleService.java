package com.food.app.user.services;

import com.food.app.user.dto.RoleDto;

import java.util.List;

public interface KeyCloakAdminClientToRoleService {
    List<RoleDto> getAllRoles();

    RoleDto getRole(String roleName);

    void addRole(RoleDto role);

    boolean updateRole(RoleDto role);

    boolean deleteRole(String roleName);

    List<RoleDto> getRolesForUser(String userId);

    boolean assignRoleToUser(String userId, String roleName);
}
