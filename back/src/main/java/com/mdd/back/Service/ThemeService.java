package com.mdd.back.Service;

import com.mdd.back.Model.DTO.ThemeDto;
import com.mdd.back.Model.Theme;
import com.mdd.back.Repository.ThemeRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ThemeService {

    private final ThemeRepository themeRepository;
    private final ModelMapper modelMapper;

    /**
     * Retrieves all themes from the database.
     *
     * @return List of ThemeDto containing all themes
     */
    public List<ThemeDto> getAllThemes() {
        Iterable<Theme> themes = themeRepository.findAll();
        return ((List<Theme>) themes).stream()
                .map(theme -> modelMapper.map(theme, ThemeDto.class))
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a theme by its ID.
     *
     * @param id The ID of the theme to retrieve
     * @return Theme corresponding to the provided ID, or null if not found
     */
    public Theme getThemeById(int id) {
        return themeRepository.findById(id).get();
    }

    /**
     * Retrieves a theme by its name.
     *
     * @param name The name of the theme to retrieve
     * @return Theme corresponding to the provided name, or null if not found
     */
    public Theme findThemeByName(String name) {
        return themeRepository.findByName(name);
    }

    /**
     * Creates a new theme and saves it to the database.
     *
     * @param theme The theme to add
     * @return The newly created theme
     */
    public Theme addTheme(Theme theme) {
        return themeRepository.save(theme);
    }


    /**
     * Updates an existing theme in the database.
     *
     * @param theme The theme object with updated information to be saved
     * @return The updated theme object as persisted in the database
     */
    public Theme updateTheme(Theme theme) {
        return themeRepository.save(theme);
    }

    /**
     * Deletes a theme from the database by its ID.
     *
     * @param id The ID of the theme to delete
     */
    public void deleteTheme(int id) {
        themeRepository.deleteById(id);
    }

    /**
     * Unsubscribes a user from a theme by its ID.
     *
     * @param id The ID of the theme from which the user will be unsubscribed
     */
    public void unsubscribeFromTheme(Long id) {
    }

    /**
     * Subscribes a user to a theme by its ID.
     *
     * @param id The ID of the theme to subscribe to
     */
    public void subscribeToTheme(Long id) {
    }
}
