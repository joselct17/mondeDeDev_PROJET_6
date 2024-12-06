package com.mdd.back.Model.DTO;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class ThemeDto {
    String name;
    List<ArticleDto> articles;
    List<SubscriberDto> subscribers;

}
