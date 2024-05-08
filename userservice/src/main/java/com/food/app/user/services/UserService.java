package com.food.app.user.services;

import com.food.app.user.model.User;
import org.keycloak.jose.jwk.JWK;

import java.util.List;
import java.util.Optional;

public interface UserService {


    User createUser(User user);

    void deleteByUserId(String userId);
    Optional<User> findByMobileNumber(String mobileNumber);
    Optional<User> findByEmail(String email);

    Optional<User> findByUserName(String userName);


    Optional<User> findByUserId(String userId);

    List<User> getAllUser();

    User updateUser(User user);


}
