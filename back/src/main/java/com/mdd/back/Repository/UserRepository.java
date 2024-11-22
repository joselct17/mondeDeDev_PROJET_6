package com.mdd.back.Repository;

import com.mdd.back.Model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    boolean existsByEmail(String email);
}
