package com.mdd.back.Controller;


import com.mdd.back.Model.AuthenticationToken;
import com.mdd.back.Model.DTO.UserDto;
import com.mdd.back.Model.LoginRequest;
import com.mdd.back.Service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserDto userDto) {
        userService.registerUser(userDto);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationToken> login(@RequestBody LoginRequest loginRequest) {
        AuthenticationToken token = userService.login(loginRequest);
        return ResponseEntity.ok(token);
    }

}
