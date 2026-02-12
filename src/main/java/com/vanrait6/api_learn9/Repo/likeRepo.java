package com.vanrait6.api_learn9.Repo;

import com.vanrait6.api_learn9.Model.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface likeRepo extends JpaRepository<Like, Integer> {

        Optional<Like> findByPost_PostIdAndUser_Email(int postId, String email);
        int countByPost_PostId(int postId);
}

