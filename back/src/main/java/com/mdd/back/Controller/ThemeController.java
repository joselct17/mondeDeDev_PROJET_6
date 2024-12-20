package com.mdd.back.Controller;


import com.mdd.back.Model.DTO.ThemeDto;
import com.mdd.back.Model.Theme;
import com.mdd.back.Model.User;
import com.mdd.back.Repository.UserRepository;
import com.mdd.back.Service.ThemeService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/themes")
@AllArgsConstructor
public class ThemeController {

    private final ThemeService themeService;
    private final UserRepository userRepository;

    /**
     * Retrieves all themes available in the system.
     *
     * @return ResponseEntity containing an Iterable of ThemeDto representing all available themes
     */
    @GetMapping
    public ResponseEntity<Map<String, List<ThemeDto>>> getThemes(Principal principal) {
        User user = userRepository.findByEmail(principal.getName());

        List<ThemeDto> themes = themeService.getAllThemesWithSubscriptionStatus(user);

        Map<String, List<ThemeDto>> themeDtoMap = new HashMap<>();
        themeDtoMap.put("themes", themes);
        return ResponseEntity.ok(themeDtoMap);
    }

    /**
     * Creates a new theme in the system.
     *
     * @param themeDto ThemeDto representing the theme to be created
     * @return ResponseEntity containing the newly created Theme object
     */
    @PostMapping
    public ResponseEntity<Theme> createTheme(@RequestBody ThemeDto themeDto) {
        return ResponseEntity.ok(themeService.addTheme(themeDto));
    }

    /**
     * Subscribes a user to a specific theme identified by the provided ID.
     *
     * @param themeId The ID of the theme to subscribe to
     * @return ResponseEntity indicating the success of subscribing to the theme
     */
    @PostMapping("/subscribe/{themeId}")
    public ResponseEntity<String> subscribeToTheme(@PathVariable Integer themeId, Principal principal) {

        User subscriber = userRepository.findByEmail(principal.getName());

        themeService.subscribeToTheme(themeId, subscriber);
        return ResponseEntity.ok("Subscribed to theme successfully");
    }

    /**
     * Unsubscribes the user from a specific theme identified by the provided ID.
     *
     * @param themeId The ID of the theme to unsubscribe from
     * @return ResponseEntity indicating the success of unsubscribing from the theme with a message "Unsubscribed from theme successfully"
     */
    @DeleteMapping("/unsubscribe/{themeId}")
    public ResponseEntity<String> unsubscribeFromTheme(@PathVariable Integer themeId, Principal principal) {
        User subscriber = userRepository.findByEmail(principal.getName());

        themeService.unsubscribeFromTheme(themeId, subscriber);
        return ResponseEntity.ok("Unsubscribed from theme successfully");
    }
}
