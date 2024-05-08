package com.food.app.user.services.imple;

import com.food.app.user.config.keycloak.KeyCloakProvider;
import com.food.app.user.dto.RoleDto;
import com.food.app.user.dto.UserDto;
import com.food.app.user.exception.ResourceNotFoundException;
import com.food.app.user.exception.UserAlreadyExistsException;
import com.food.app.user.mapper.RoleMapper;
import com.food.app.user.mapper.UserMapper;
import com.food.app.user.model.Role;
import com.food.app.user.model.User;
import com.food.app.user.services.KeyCloakAdminClientToRoleService;
import com.food.app.user.services.RoleService;
import com.food.app.user.services.UserService;
import org.keycloak.admin.client.resource.RoleResource;
import org.keycloak.admin.client.resource.RolesResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class KeyCloakAdminClientToRoleServiceImple implements KeyCloakAdminClientToRoleService {

    //this is realm variable
    @Value("${keycloak.realm}")
    public String realm;

    @Autowired
    private final KeyCloakProvider keyCloakProvider;
    @Autowired
    private UserService userService;

    public KeyCloakAdminClientToRoleServiceImple(KeyCloakProvider keyCloakProvider) {
        this.keyCloakProvider = keyCloakProvider;
    }

    @Autowired
    private RoleService roleService;

    @Override
    public List<RoleDto> getAllRoles() {

        List<Role> formDatabase = roleService.findAll();
        List<RoleDto> roleDtos = new ArrayList<>();

        List<RoleRepresentation> roleRepresentationList = keyCloakProvider.getInstance().realm(realm).roles().list();

        for (Role role : formDatabase) {
            Optional<RoleRepresentation> keycloakRole = roleRepresentationList.stream()
                    .filter(r -> r.getName().equals(role.getName()))
                    .findFirst();
            if (keycloakRole.isPresent()) {
                roleDtos.add(RoleMapper.mapToRoleDto(role, new RoleDto()));
            }
        }
        return roleDtos;
    }

    @Override
    public RoleDto getRole(String roleName) {


        Optional<Role> roleOptional = this.roleService.findByName(roleName);
        if (roleOptional.isPresent()) {
            Role role = roleOptional.get();

            RoleResource roleResource = keyCloakProvider.getInstance().realm(realm)
                    .roles().get(roleName);
            if (roleResource != null) {
                return RoleMapper.mapToRoleDto(role, new RoleDto());
            } else {
                throw new ResourceNotFoundException("Role", "Name", roleName);
            }

        } else {
            throw new ResourceNotFoundException("Role", "Name", roleName);
        }
    }

    @Override
    public void addRole(RoleDto role) {
        if (this.roleService.findByName(role.getName()).isPresent()) {
            throw new UserAlreadyExistsException("Role already registered with the given Name: " + role.getName());
        }

        RoleRepresentation roleRepresentation = RoleMapper.mapToRepresentation(role);
        RolesResource rolesResource = keyCloakProvider.getInstance().realm(realm).roles();
        rolesResource.create(roleRepresentation);

        this.roleService.create(RoleMapper.mapToRole(role, new Role()));
    }

    @Override
    public boolean updateRole(RoleDto role) {
          boolean f=false;
        if (!this.roleService.findByName(role.getName()).isPresent()) {
            f=false;
            throw new ResourceNotFoundException("Role", "Name", role.getName());

        } else {
            keyCloakProvider.getInstance().realm(realm).roles().get(role.getName()).update(RoleMapper.mapToRepresentation(role));
            roleService.updateRole(RoleMapper.mapToRole(role, new Role()));
            f=true;
        }
        return f;
    }

    @Override
    public boolean deleteRole(String roleName) {
        boolean f=false;
        if (this.roleService.findByName(roleName).isEmpty()) {
            f=false;
            throw new ResourceNotFoundException("Role", "Name", roleName);
        }
        else {
            keyCloakProvider.getInstance().realm(realm).roles().deleteRole(roleName);
            this.roleService.deleteRole(roleName);
            f=true;
        }
        return f;

    }

    @Override
    public List<RoleDto> getRolesForUser(String userId) {
        Optional<User> userOptional = this.userService.findByUserId(userId);
        if (userOptional.isPresent()) {
            UserResource userResource = keyCloakProvider.getInstance().realm(realm).users().get(userId);
            if (userResource != null) {
                return RoleMapper.mapListRoleDto(userResource.roles().realmLevel().listAll());
            } else {
                throw new ResourceNotFoundException("User", "User Id", userId);
            }
        } else {
            throw new ResourceNotFoundException("User", "User Id", userId);
        }
    }

    @Override
    public boolean assignRoleToUser(String userId, String roleName) {
        boolean f=false;
        Optional<User> userOptional = this.userService.findByUserId(userId);
        Optional<Role> roleOptional=this.roleService.findByName(roleName);
        if (userOptional.isPresent() && roleOptional.isPresent()) {
            UserResource userResource = keyCloakProvider.getInstance().realm(realm).users().get(userId);
            RoleResource roleResource = keyCloakProvider.getInstance().realm(realm).roles().get(roleName);
            if (userResource != null && roleResource != null) {
                RoleRepresentation representation = roleResource.toRepresentation();
                userResource.roles().realmLevel().add(Arrays.asList(representation));
                f=true;
            }
            else if(roleResource == null){
                f=false;
                throw new ResourceNotFoundException("Role", "Name", roleName);
            }
            else {
                f=false;
                throw new ResourceNotFoundException("User", "User Id", userId);

            }
        } else if (userOptional.isEmpty()){
            f=false;
            throw new ResourceNotFoundException("User", "User Id", userId);
        }
        else{
            f=false;
            throw new ResourceNotFoundException("Role", "Name", roleName);
        }

        return f;
    }
}
