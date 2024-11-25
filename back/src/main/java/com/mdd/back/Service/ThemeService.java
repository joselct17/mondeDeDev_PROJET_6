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

    public List<ThemeDto> getAllThemes() {
        Iterable<Theme> themes = themeRepository.findAll();
        return ((List<Theme>) themes).stream()
                .map(theme -> modelMapper.map(theme, ThemeDto.class))
                .collect(Collectors.toList());
    }

    public Theme getThemeById(int id) {
        return themeRepository.findById(id).get();
    }

    public Theme findThemeByName(String name) {
        return themeRepository.findByName(name);
    }

    public Theme addTheme(Theme theme) {
        return themeRepository.save(theme);
    }


    public Theme updateTheme(Theme theme) {
        return themeRepository.save(theme);
    }

    public void deleteTheme(int id) {
        themeRepository.deleteById(id);
    }

    public void unsubscribeFromTheme(Long id) {
    }

    public void subscribeToTheme(Long id) {
    }
}
