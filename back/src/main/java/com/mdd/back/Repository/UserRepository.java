package com.mdd.back.Repository;

import com.mdd.back.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Verifies if a user with the given email address exists in the database.
     *
     * @param email The email address to search for.
     * @return true if a user with the given email address exists, false otherwise.
     */
    boolean existsByEmail(String email);

    /**
     * Retrieves a user by their email address.
     *
     * @param login The email address to search for.
     * @return The User object if a user with the given email address was found, null otherwise.
     */
    User findByEmail(String login);

    boolean existsByUsername(String userName);
}
