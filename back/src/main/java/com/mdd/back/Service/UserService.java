package com.mdd.back.Service;


import com.mdd.back.Model.AuthenticationToken;
import com.mdd.back.Model.DTO.RegisterUserDto;
import com.mdd.back.Model.DTO.UserDto;
import com.mdd.back.Model.DTO.UserProfileDto;
import com.mdd.back.Model.LoginRequest;
import com.mdd.back.Model.User;
import com.mdd.back.Repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;


    private final PasswordEncoder passwordEncoder;


    public void registerUser(UserDto userDto) {
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new RuntimeException("Email already in use");
        }
        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);
    }

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

    public AuthenticationToken login(LoginRequest loginRequest) {
        return new AuthenticationToken("token", LocalDateTime.now().plusHours(1));
    }
}
