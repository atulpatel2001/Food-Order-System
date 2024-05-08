package com.food.app.user.services;

import com.food.app.user.dto.LoginDto;
import com.food.app.user.dto.UserDto;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.boot.autoconfigure.security.SecurityProperties;

import javax.ws.rs.core.Response;
import java.util.List;

public interface KeyCloakAdminClientToUserService {


    public boolean  createKeyCloakUser(UserDto userDto);

    public List<UserDto> getUsers();

    public KeycloakBuilder login(LoginDto loginDto);

    public UserDto getUserById(String userId);


    public boolean updateUser(UserDto userDto);


    public boolean deleteUser(String userId);



}
