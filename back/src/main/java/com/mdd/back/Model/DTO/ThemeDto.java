package com.mdd.back.Model.DTO;


import com.mdd.back.Model.Article;
import com.mdd.back.Model.Theme;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class ThemeDto {
    Long id;
    String name;
    String description;
    Boolean isSubscribed;

    public ThemeDto(Theme theme) {
        this.id = theme.getId();
        this.name = theme.getName();
        this.description = theme.getDescription();
    }

}
