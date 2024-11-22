package com.mdd.back.Service;


import com.mdd.back.Model.DTO.RegisterUserDto;
import com.mdd.back.Model.DTO.UserDto;
import com.mdd.back.Model.User;
import com.mdd.back.Repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
            existingUser.setUsername(registerUserDto.getName());
            existingUser.setEmail(registerUserDto.getEmail());
            existingUser.setPassword(registerUserDto.getPassword());
            existingUser.setUpdatedAt(LocalDate.now());
            return userRepository.save(existingUser);
        }
        return null;
    }

}
