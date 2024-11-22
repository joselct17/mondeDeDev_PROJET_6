package com.mdd.back.Repository;

import com.mdd.back.Model.Theme;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ThemeRepository extends CrudRepository<Theme, Integer> {
}
