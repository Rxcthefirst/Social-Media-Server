package com.revature.repositories;

import com.revature.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmailAndPassword(String email, String password);

    Optional<User> findByEmail(String email);

    Optional<List<User>> findAllByFirstNameAndLastName(String firstName, String lastName);

    @Query(
        "SELECT u FROM User u " +
            "WHERE lower(u.firstName) LIKE '%'||lower(?1)||'%' AND lower(u.lastName) LIKE '%'||lower(?2)||'%' " +
                "OR lower(u.firstName) LIKE '%'||lower(?2)||'%' AND lower(u.lastName) LIKE '%'||lower(?1)||'%'"
    )
    Optional<List<User>> findAllMatchesByFirstAndLastNames(String firstName, String lastName);

    @Query(
        "SELECT u FROM User u " +
            "WHERE lower(u.firstName) LIKE '%'||lower(?1)||'%' OR lower(u.lastName) LIKE '%'||lower(?1)||'%' "
    )
    Optional<List<User>>findAllMatchesByEitherFirstOrLastName(String name);
}