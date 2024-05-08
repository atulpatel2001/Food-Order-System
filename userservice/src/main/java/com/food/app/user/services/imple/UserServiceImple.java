package com.food.app.user.services.imple;

import com.food.app.user.exception.ResourceNotFoundException;
import com.food.app.user.exception.UserAlreadyExistsException;
import com.food.app.user.model.User;
import com.food.app.user.repo.UserRepository;
import com.food.app.user.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImple implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User createUser(User user) {
        return this.userRepository.save(user);
    }

    @Override
    public void deleteByUserId(String userId) {
        this.userRepository.deleteByUserId(userId);
    }

    @Override
    public Optional<User> findByMobileNumber(String mobileNumber) {
        return this.userRepository.findByMobileNumber(mobileNumber);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }

    @Override
    public Optional<User> findByUserName(String userName) {
        return this.userRepository.findByUserName(userName);
    }

    @Override
    public Optional<User> findByUserId(String userId) {
        return this.userRepository.findByUserId(userId);
    }

    @Override
    public List<User> getAllUser() {
        return this.userRepository.findAll();
    }

    @Override
    public User updateUser(User user) {
        Optional<User> oldUserOptional = userRepository.findByUserId(user.getUserId());

        if (oldUserOptional.isPresent()) {
            User oldUser = oldUserOptional.get();

            oldUser.setFirstName(user.getFirstName());
            oldUser.setLastName(user.getLastName());
            oldUser.setUserName(user.getUserName());
            oldUser.setEmail(user.getEmail());
            oldUser.setMobileNumber(user.getMobileNumber());
            // Update password only if not null
            if (user.getPassword() != null) {
                oldUser.setPassword(user.getPassword());
            }
            // Save the updated user to the database
            return userRepository.save(oldUser);
        } else {
            throw new ResourceNotFoundException("User", "User Id", user.getUserId());
        }
    }


}
