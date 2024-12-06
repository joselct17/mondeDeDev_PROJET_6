package com.mdd.back.Service;

import com.mdd.back.Model.DTO.SubscriberDto;
import com.mdd.back.Model.DTO.ThemeDto;
import com.mdd.back.Model.Theme;
import com.mdd.back.Model.User;
import com.mdd.back.Repository.ThemeRepository;
import com.mdd.back.Repository.UserRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ThemeService {

    private final ThemeRepository themeRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    /**
     * Retrieves all themes from the database.
     *
     * @return List of ThemeDto containing all themes
     */
    public List<ThemeDto> getAllThemes() {
        return themeRepository.findAll()
                .stream()
                .map(theme -> {
                    ThemeDto themeDto = modelMapper.map(theme, ThemeDto.class);
                    // Mapper les abonnés pour ne récupérer que l'email
                    themeDto.setSubscribers(
                            theme.getSubscribers().stream()
                                    .map(subscriber -> new SubscriberDto(subscriber.getEmail()))
                                    .collect(Collectors.toList())
                    );
                    return themeDto;
                })
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
     * @param themeDto The theme to add
     * @return The newly created theme
     */
    public Theme addTheme(ThemeDto themeDto) {

        Theme newTheme = new Theme();
        newTheme.setName(themeDto.getName());

        return themeRepository.save(newTheme);
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
    public void unsubscribeFromTheme(Integer themeId, User subscriber) {
        Theme theme = themeRepository.findById(themeId)
                .orElseThrow(() -> new IllegalArgumentException("Theme not found with id: " + themeId));

        if (theme.getSubscribers().contains(subscriber)) {
            theme.getSubscribers().remove(subscriber);
            subscriber.getSubscriptions().remove(theme); // Mettre à jour la liste des abonnements de l'utilisateur
            themeRepository.save(theme); // Sauvegarde les modifications dans la base
            userRepository.save(subscriber); // Sauvegarde aussi l'utilisateur pour garantir la synchronisation
        }
    }

    /**
     * Subscribes a user to a theme by its ID.
     *
     * @param themeId The ID of the theme to subscribe to
     */
    public void subscribeToTheme(Integer themeId, User subscriber) {
        Theme theme = themeRepository.findById(themeId)
                .orElseThrow(() -> new IllegalArgumentException("Theme not found with id: " + themeId));

        if (!theme.getSubscribers().contains(subscriber)) {
            theme.getSubscribers().add(subscriber);
            subscriber.getSubscriptions().add(theme); // Mettre à jour la liste des abonnements de l'utilisateur
            themeRepository.save(theme); // Sauvegarde les modifications dans la base
            userRepository.save(subscriber); // Sauvegarde aussi l'utilisateur pour garantir la synchronisation
        }
    }


}
