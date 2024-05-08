package com.food.app.user.mapper;

import com.food.app.user.dto.UserDto;
import com.food.app.user.model.User;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;

import java.util.ArrayList;
import java.util.List;

public class UserMapper {


    public static UserDto userMapToUserDto(User user,UserDto  userDto){
        userDto.setUserId(user.getUserId());
          userDto.setUserName(user.getUserName());
          userDto.setFirstName(user.getFirstName());
          userDto.setLastName(user.getLastName());
          userDto.setPassword(user.getPassword());
          userDto.setMobileNumber(user.getMobileNumber());
          userDto.setEmail(user.getEmail());

          return userDto;
    }

    public static User UserDtoToUser(UserDto userDto, User user){
        user.setUserId(userDto.getUserId());
        user.setUserName(userDto.getUserName());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setPassword(userDto.getPassword());
        user.setMobileNumber(userDto.getMobileNumber());
        user.setEmail(userDto.getEmail());

        return user;
    }



    public static UserRepresentation UserDtoToMapUserRepresentation(UserDto user){
        UserRepresentation kcUser = new UserRepresentation();
        kcUser.setId(user.getUserId());
        kcUser.setUsername(user.getUserName());
        kcUser.setFirstName(user.getFirstName());
        kcUser.setLastName(user.getLastName());
        kcUser.setEmail(user.getEmail());
        kcUser.setEnabled(true);
        kcUser.setEmailVerified(false);

        List<CredentialRepresentation> creds=new ArrayList<>();
        CredentialRepresentation cred = new CredentialRepresentation();
        cred.setTemporary(false);
        cred.setValue(user.getPassword());
        creds.add(cred);
        kcUser.setCredentials(creds);
        return kcUser;
    }


    public static List<UserDto> userRepresentationsToMapUsersDto(List<UserRepresentation> userRepresentations) {
        List<UserDto> users=new ArrayList<>();

        if(!userRepresentations.isEmpty()){
            userRepresentations.forEach(userRepo->{
                users.add(UserRepresentationToMapUserDto(userRepo));
            });
        }

        return users;
    }

    public static UserDto UserRepresentationToMapUserDto(UserRepresentation userRepresentation){
        UserDto user=new UserDto();
        user.setUserId(userRepresentation.getId());
        user.setFirstName(userRepresentation.getFirstName());
        user.setLastName(userRepresentation.getLastName());
        user.setUserName(userRepresentation.getUsername());
        user.setEmail(userRepresentation.getEmail());
        return user;
    }



}
