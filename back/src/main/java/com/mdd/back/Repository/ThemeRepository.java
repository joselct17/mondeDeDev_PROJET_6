package com.mdd.back.Repository;

import com.mdd.back.Model.Theme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ThemeRepository extends JpaRepository<Theme, Integer> {
    /**
     * Retrieves a theme by its name.
     *
     * @param name The name of the theme to retrieve
     * @return Theme corresponding to the provided name, or null if not found
     */
    Theme findByName(String name);
}
