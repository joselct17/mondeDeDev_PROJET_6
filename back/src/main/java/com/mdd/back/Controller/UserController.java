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

    @GetMapping("/profile")
    public ResponseEntity<UserProfileDto> getUserProfile() {
        return ResponseEntity.ok(userService.getUserProfile());
    }

    @PutMapping("/profile")
    public ResponseEntity<String> updateUserProfile(@RequestBody UserProfileDto profileDto) {
        userService.updateUserProfile(profileDto);
        return ResponseEntity.ok("Profile updated successfully");
    }


}
