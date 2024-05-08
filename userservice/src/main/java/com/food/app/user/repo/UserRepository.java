package com.food.app.user.repo;

import com.food.app.user.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByMobileNumber(String mobileNumber);
    Optional<User> findByEmail(String email);
    Optional<User> findByUserName(String userName);

    Optional<User> findByUserId(String userId);
    @Transactional
    @Modifying
    void deleteByUserId(String userId);



}
