package com.mdd.back.Controller;


import com.mdd.back.Model.AuthResponse;
import com.mdd.back.Model.AuthenticationToken;
import com.mdd.back.Model.DTO.LoginDto;
import com.mdd.back.Model.DTO.UserDto;
import com.mdd.back.Model.LoginRequest;
import com.mdd.back.Model.User;
import com.mdd.back.Service.UserService;
import com.mdd.back.config.AuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<UserDto> createUser(@RequestBody User users) {
        User createdUser = userService.createUser(users);
        UserDto userDto = new UserDto(createdUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(userDto);
    }

    @PostMapping("/login")
    public ResponseEntity<?> getToken(@RequestBody LoginDto loginDto) {
        String login = loginDto.getLogin();
        String email = loginDto.getEmail();

        // Utilisez l'email ou le login en fonction de ce qui est présent
        String identifier = (login != null) ? login : email;
        if (identifier == null) {
            throw new RuntimeException("Login or email must be provided");
        }

        String password = loginDto.getPassword();
        String token = authenticationService.loginAndGenerateToken(identifier, password);
        return ResponseEntity.ok(new AuthResponse(token));
    }

    @GetMapping("/me")
    public ResponseEntity<UserDto> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName(); // Si le JWT contient le login
        User currentUser = userService.getUserByEmail(login); // Rechercher l'utilisateur par login

        // Créer un UserDto à partir de l'entité Users
        UserDto userDto = new UserDto(currentUser);

        return ResponseEntity.ok(userDto);
    }

}
