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

    @GetMapping
    public ResponseEntity<Iterable<ThemeDto>> getThemes() {
        return ResponseEntity.ok(themeService.getAllThemes());
    }

    @PostMapping("/subscribe/{id}")
    public ResponseEntity<String> subscribeToTheme(@PathVariable Long id) {
        themeService.subscribeToTheme(id);
        return ResponseEntity.ok("Subscribed to theme successfully");
    }

    @DeleteMapping("/unsubscribe/{id}")
    public ResponseEntity<String> unsubscribeFromTheme(@PathVariable Long id) {
        themeService.unsubscribeFromTheme(id);
        return ResponseEntity.ok("Unsubscribed from theme successfully");
    }
}
