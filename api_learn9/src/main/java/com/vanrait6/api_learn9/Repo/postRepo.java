package com.vanrait6.api_learn9.Repo;

import com.vanrait6.api_learn9.Model.Data_Post;
import com.vanrait6.api_learn9.Model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface postRepo extends JpaRepository<Data_Post,Integer> {
    List<Data_Post> findByUser_Email(String email);


    @Query("SELECT COUNT(p) FROM Data_Post p WHERE p.user.email = :email")
    long countByEmail(@Param("email") String email);

}
