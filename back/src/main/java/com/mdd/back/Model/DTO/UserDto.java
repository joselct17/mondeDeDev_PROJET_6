package com.mdd.back.Model.DTO;



import com.mdd.back.Model.Theme;
import com.mdd.back.Model.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class UserDto {

    private String email;
    private String password;
    private String username;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
    private List<ThemeDto> subscribedThemes;

    public UserDto(User user) {
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.username = user.getUsername();
        this.created_at = user.getCreatedAt();
        this.updated_at = user.getUpdatedAt();

        // Convertir les abonnements en ThemeDto
        if (user.getSubscriptions() != null) {
            this.subscribedThemes = user.getSubscriptions().stream()
                    .map(ThemeDto::new)
                    .collect(Collectors.toList());
        }
    }
}
