package com.mdd.back.Controller;


import com.mdd.back.Model.DTO.UserProfileDto;
import com.mdd.back.Service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * Retrieve the user profile.
     *
     * @return ResponseEntity containing the user's profile information as UserProfileDto
     */
    @GetMapping("/profile")
    public ResponseEntity<UserProfileDto> getUserProfile() {
        return ResponseEntity.ok(userService.getUserProfile());
    }

    /**
     * Updates the user profile with the provided data.
     *
     * @param profileDto The UserProfileDto object containing the updated user profile data
     * @return ResponseEntity<String> indicating the success message for updating the profile
     */
    @PutMapping("/profile")
    public ResponseEntity<String> updateUserProfile(@RequestBody UserProfileDto profileDto) {
        userService.updateUserProfile(profileDto);
        return ResponseEntity.ok("Profile updated successfully");
    }

}
