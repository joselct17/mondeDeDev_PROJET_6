package com.mdd.back.Controller;


import com.mdd.back.Model.AuthResponse;
import com.mdd.back.Model.AuthenticationToken;
import com.mdd.back.Model.DTO.LoginDto;
import com.mdd.back.Model.DTO.RegisterUserDto;
import com.mdd.back.Model.DTO.UserDto;
import com.mdd.back.Model.LoginRequest;
import com.mdd.back.Model.User;
import com.mdd.back.Service.UserService;
import com.mdd.back.config.AuthenticationService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

/**
 * Controller handling authentication-related endpoints.
 */
@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);
    private final UserService userService;
    private final AuthenticationService authenticationService;

    /**
     * Creates a new user based on the provided User object.
     *
     * @param userDto The User object containing user details like email, password, and username.
     * @return ResponseEntity<UserDto> A ResponseEntity containing the UserDto of the created user with HTTP status 201 (Created).
     */
    @PostMapping("/register")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody RegisterUserDto userDto) {
        User createdUser = userService.createUser(userDto);
        UserDto response = new UserDto(createdUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Retrieves a JWT token based on the provided LoginDto object.
     *
     * @param loginDto The LoginDto object containing login, email, and password from the user.
     * @return ResponseEntity<?> A ResponseEntity containing an AuthResponse object with the generated JWT token if successful. Returns HTTP status 200 (OK) on success.
     * @throws RuntimeException If neither login nor email is provided, or if the login credentials are invalid.
     */
    @PostMapping("/login")
    public ResponseEntity<?> getToken(@RequestBody LoginDto loginDto) {
        String userName = loginDto.getUserName();
        String email = loginDto.getEmail();

        // Utilisez l'email ou le login en fonction de ce qui est présent
        String identifier = (userName != null) ? userName : email;
        if (identifier == null) {
            throw new RuntimeException("Username or email must be provided");
        }

        String password = loginDto.getPassword();
        String token = authenticationService.loginAndGenerateToken(identifier, password);
        return ResponseEntity.ok(new AuthResponse(token));
    }

    /**
     * Retrieves the currently authenticated user.
     *
     * @return ResponseEntity<UserDto> A ResponseEntity containing the UserDto of the current user with HTTP status 200 (OK).
     */
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
