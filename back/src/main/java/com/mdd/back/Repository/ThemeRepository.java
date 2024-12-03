package com.mdd.back.Repository;

import com.mdd.back.Model.Theme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ThemeRepository extends JpaRepository<Theme, Integer> {
    Theme findByName(String name);
}
