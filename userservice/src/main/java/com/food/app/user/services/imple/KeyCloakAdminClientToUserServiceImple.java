/**
 * created  by-atul patel
 */

package com.food.app.user.services.imple;

import com.food.app.user.config.keycloak.KeyCloakProvider;
import com.food.app.user.dto.LoginDto;
import com.food.app.user.dto.UserDto;
import com.food.app.user.exception.ResourceNotFoundException;
import com.food.app.user.exception.UserAlreadyExistsException;
import com.food.app.user.mapper.UserMapper;
import com.food.app.user.model.User;
import com.food.app.user.services.KeyCloakAdminClientToRoleService;
import com.food.app.user.services.KeyCloakAdminClientToUserService;
import com.food.app.user.services.UserService;
import org.keycloak.admin.client.KeycloakBuilder;

import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * this service have all information about a store data and fetch data etc..
 */
@Service
public class KeyCloakAdminClientToUserServiceImple implements KeyCloakAdminClientToUserService {


    //this is realm variable
    @Value("${keycloak.realm}")
    public String realm;

    @Autowired
    private final KeyCloakProvider keyCloakProvider;

    @Autowired
    private UserService userService;

    @Autowired
    private KeyCloakAdminClientToRoleService clientToRoleService;

    public KeyCloakAdminClientToUserServiceImple(KeyCloakProvider keyCloakProvider) {
        this.keyCloakProvider = keyCloakProvider;
    }

    /**
     * this function is use for a store a user in keycloak database and also application database
     *
     * @param userDto
     * @return boolean
     */
    @Override
    public boolean createKeyCloakUser(UserDto userDto) {
        if (this.userService.findByEmail(userDto.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("User already registered with the given Email Id: " + userDto.getEmail());
        }

        if (this.userService.findByMobileNumber(userDto.getMobileNumber()).isPresent()) {
            throw new UserAlreadyExistsException("User already registered with the given mobile number: " + userDto.getMobileNumber());
        }

        if (this.userService.findByUserName(userDto.getUserName()).isPresent()) {
            throw new UserAlreadyExistsException("User already registered with the given User Name: " + userDto.getUserName());
        }

        UserRepresentation userRepresentation = UserMapper.UserDtoToMapUserRepresentation(userDto);

        UsersResource userResource = keyCloakProvider.getInstance().realm(realm).users();
        Response response = userResource.create(userRepresentation);

        if (response.getStatus() == HttpStatus.CREATED.value()) {
            User user = UserMapper.UserDtoToUser(userDto, new User());
            String userId = response.getLocation().getPath().replaceAll(".*/([^/]+)$", "$1");
            user.setUserId(userId);
            //userResource.get(user.getUserId()).sendVerifyEmail();
            user.setRole("ROLE_user");

            userService.createUser(user);
            this.clientToRoleService.assignRoleToUser(userId,"user");
            return true;
        } else {
            return false;
        }
    }

    /**
     * this function is fetch data from database
     *
     * @return List<UserDto>
     * @Param Null
     */

    @Override
    public List<UserDto> getUsers() {
        List<User> usersFromDatabase = userService.getAllUser();
        List<UserDto> userDtos = new ArrayList<>();
        // Get all users from Keycloak
        List<UserRepresentation> usersFromKeycloak = keyCloakProvider.getInstance().realm(realm).users().list();

        for (User user : usersFromDatabase) {
            Optional<UserRepresentation> keycloakUser = usersFromKeycloak.stream()
                    .filter(u -> u.getId().equals(user.getUserId()))
                    .findFirst();
            if (keycloakUser.isPresent()) {
                userDtos.add(UserMapper.userMapToUserDto(user, new UserDto()));
            }
        }

        return userDtos;
    }

    /**
     * this function use for valid credential and authorize user
     *
     * @param loginDto
     * @return KeyCloakBuilder
     */
    @Override
    public KeycloakBuilder login(LoginDto loginDto) {
        KeycloakBuilder login = null;
        try {
            login = this.keyCloakProvider.login(loginDto);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return login;
    }


    /**
     * this function is use for get user by id
     * @param userId
     * @return UserDto
     */
    @Override
    public UserDto getUserById(String userId) {
        Optional<User> userOptional = this.userService.findByUserId(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            try {
                keyCloakProvider.getInstance().realm(realm).users().get(userId).toRepresentation();
                return UserMapper.userMapToUserDto(user, new UserDto());
            } catch (javax.ws.rs.NotFoundException e) {
                userService.deleteByUserId(userId);
                throw new ResourceNotFoundException("User", "User Id", userId);
            }
        } else {
            try {
                keyCloakProvider.getInstance().realm(realm).users().get(userId).toRepresentation();
                throw new ResourceNotFoundException("User", "User Id", userId);
            } catch (javax.ws.rs.NotFoundException e) {

                throw new ResourceNotFoundException("User", "User Id", userId);
            }
        }
    }

    /**
     * this function is used for update user in application database and also keycloak database
     * @param userDto
     * @return boolean
     */
    @Override
    public boolean updateUser(UserDto userDto) {
        try {
            // Check for existing user (unchanged)
            checkForExistingUser(userDto);

            Optional<User> oldUserOptional = userService.findByUserId(userDto.getUserId());
            if (oldUserOptional.isPresent()) {
                UserRepresentation userRepresentation = UserMapper.UserDtoToMapUserRepresentation(userDto);

                // Remove password field if null (unchanged)
                if (userDto.getPassword() == null) {
                    userRepresentation.setCredentials(null);
                }
                userRepresentation.setUsername(userDto.getUserName());

                this.keyCloakProvider.getInstance()
                        .realm(realm)
                        .users()
                        .get(userDto.getUserId())
                        .update(userRepresentation);

                // Update user in database (unchanged)
                User user = UserMapper.UserDtoToUser(userDto, new User());
                return userService.updateUser(user) != null;
            } else {
                throw new ResourceNotFoundException("User", "User Id", userDto.getUserId());
            }
        }
        catch (UserAlreadyExistsException e) {
            throw e; // Re-throw to be handled by the controller
        }
        catch (ResourceNotFoundException e) {
            throw e; // Re-throw to be handled by the controller
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while updating the user.", e); // Wrap in a RuntimeException
        }
    }


    /**
     * purpose of this function is used for userId
     * @param userId
     * @return boolean
     */
    @Override
    public boolean deleteUser(String userId) {
        boolean f = false;

        Response delete = keyCloakProvider.getInstance().realm(realm).users().delete(userId);
        if (delete.getStatusInfo().getFamily() == Response.Status.Family.SUCCESSFUL) {
            f = true;
            userService.deleteByUserId(userId);
        } else if (delete.getStatusInfo().getFamily() == Response.Status.Family.CLIENT_ERROR) {
            if (!userService.findByUserId(userId).isPresent()) {
                throw new ResourceNotFoundException("User", "User Id", userId);
            }
        }

        return f;
    }


    /**
     * purpose this function is used for check existing user and throw exception
     * @param userDto
     */
    private void checkForExistingUser(UserDto userDto) {
        if (userService.findByUserName(userDto.getUserName()).isPresent()) {
            throw new UserAlreadyExistsException("User Already Register With UserName:-" + userDto.getUserName());
        }
        if (userService.findByMobileNumber(userDto.getMobileNumber()).isPresent()) {
            throw new UserAlreadyExistsException("User Already Register With Mobile Number:-" + userDto.getMobileNumber());
        }
        if (userService.findByEmail(userDto.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("User Already Register With Email Id:-" + userDto.getEmail());
        }
    }
}
