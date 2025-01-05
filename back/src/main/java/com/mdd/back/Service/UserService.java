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


    /**
     * Retrieves a user by their email address.
     *
     * @param login The email address to search for.
     * @return A User object if a user with the given email address was found, null otherwise.
     */
    public User getUserByEmail(String login) {
        return userRepository.findByEmail(login);
    }

    /**
     * Retrieves all users from the repository.
     *
     * @return An Iterable containing all User objects.
     */
    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Retrieves a user by their database ID.
     *
     * @param id The database ID of the user to retrieve.
     * @return An Optional containing the User object if a user with the given ID was found, or an empty Optional otherwise.
     */
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    /**
     * Updates a user with the provided information.
     *
     * @param id            The database ID of the user to update.
     * @param registerUserDto The RegisterUserDto object containing the new user information.
     * @return The updated User object, or null if the user was not found.
     */
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

    /**
     * Retrieves the profile information of the currently authenticated user.
     *
     * @return UserProfileDto containing the user's profile details, or null if not found.
     */
    public UserProfileDto getUserProfile() {
        return null;
    }

    /**
     * Updates the profile of the currently authenticated user with the provided profile data.
     *
     * @param profileDto The UserProfileDto object containing the new profile information for the user.
     */
    public void updateUserProfile(UserProfileDto profileDto) {
        return;
    }

    /**
     * Creates a new user based on the provided User object.
     *
     * @param userDto The User object containing user details like email, password, and username.
     * @return The created User object if successful, null otherwise.
     * @throws RuntimeException If an error occurs while creating the user, or if the email address is already in use.
     */
    public User createUser(RegisterUserDto userDto) {
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new RuntimeException("Email already in use");
        }
        if (userRepository.existsByUsername(userDto.getUserName())) {
            throw new RuntimeException("Username already in use");
        }

        User user = mapToEntity(userDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        return userRepository.save(user);
    }


    private User mapToEntity(RegisterUserDto userDto) {
        User user = new User();
        user.setUsername(userDto.getUserName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        return user;
    }

}
