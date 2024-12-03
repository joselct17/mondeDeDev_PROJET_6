package com.mdd.back.Service;


import com.mdd.back.Model.AuthenticationToken;
import com.mdd.back.Model.DTO.RegisterUserDto;
import com.mdd.back.Model.DTO.UserDto;
import com.mdd.back.Model.DTO.UserProfileDto;
import com.mdd.back.Model.LoginRequest;
import com.mdd.back.Model.User;
import com.mdd.back.Repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;


    private final PasswordEncoder passwordEncoder;


    public User getUserByEmail(String login) {
        return userRepository.findByEmail(login);
    }

    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public User updateUser(Long id, RegisterUserDto registerUserDto) {
        Optional<User> existingUserOpt = userRepository.findById(id);
        if (existingUserOpt.isPresent()) {
            User existingUser = existingUserOpt.get();
            existingUser.setUsername(registerUserDto.getUserName());
            existingUser.setEmail(registerUserDto.getEmail());
            existingUser.setPassword(registerUserDto.getPassword());
            existingUser.setUpdatedAt(LocalDateTime.now());
            return userRepository.save(existingUser);
        }
        return null;
    }

    public UserProfileDto getUserProfile() {
        return null;
    }

    public void updateUserProfile(UserProfileDto profileDto) {
        return;
    }

    public User createUser(User user) {
        try {
            if (userRepository.existsByEmail(user.getEmail())) {
                throw new RuntimeException("Email already in use");
            }
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setCreatedAt(LocalDateTime.now());
            user.setUpdatedAt(LocalDateTime.now());
            return userRepository.save(user);
        } catch (RuntimeException e) {
            log.error("Error creating user: {}", e.getMessage());
            throw e;
        }
    }

}
