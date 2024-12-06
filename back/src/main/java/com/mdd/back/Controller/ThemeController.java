package com.mdd.back.Controller;


import com.mdd.back.Model.DTO.ThemeDto;
import com.mdd.back.Service.ThemeService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/themes")
@AllArgsConstructor
public class ThemeController {

    private final ThemeService themeService;

    /**
     * Retrieves all themes available in the system.
     *
     * @return ResponseEntity containing an Iterable of ThemeDto representing all available themes
     */
    @GetMapping
    public ResponseEntity<Iterable<ThemeDto>> getThemes() {
        return ResponseEntity.ok(themeService.getAllThemes());
    }

    /**
     * Subscribes a user to a specific theme identified by the provided ID.
     *
     * @param id The ID of the theme to subscribe to
     * @return ResponseEntity indicating the success of subscribing to the theme
     */
    @PostMapping("/subscribe/{id}")
    public ResponseEntity<String> subscribeToTheme(@PathVariable Long id) {
        themeService.subscribeToTheme(id);
        return ResponseEntity.ok("Subscribed to theme successfully");
    }

    /**
     * Unsubscribes the user from a specific theme identified by the provided ID.
     *
     * @param id The ID of the theme to unsubscribe from
     * @return ResponseEntity indicating the success of unsubscribing from the theme with a message "Unsubscribed from theme successfully"
     */
    @DeleteMapping("/unsubscribe/{id}")
    public ResponseEntity<String> unsubscribeFromTheme(@PathVariable Long id) {
        themeService.unsubscribeFromTheme(id);
        return ResponseEntity.ok("Unsubscribed from theme successfully");
    }
}
