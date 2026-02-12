package com.vanrait6.api_learn9.Repo;

import com.vanrait6.api_learn9.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface userRepo extends JpaRepository<User,Integer> {

    boolean existsUserByEmail(String email);

    @Query("""
    SELECT u 
    FROM User u 
    WHERE u.email = :email
    """)
    User findUsernameAndProfile(String email);

    User findByEmail(String email);

    Optional<User> findUserByEmail(String email);
}
