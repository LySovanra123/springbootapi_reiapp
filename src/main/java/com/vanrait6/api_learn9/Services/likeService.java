package com.vanrait6.api_learn9.Services;

import com.vanrait6.api_learn9.DTO.DTO_like;
import com.vanrait6.api_learn9.Model.Data_Post;
import com.vanrait6.api_learn9.Model.Like;
import com.vanrait6.api_learn9.Model.User;
import com.vanrait6.api_learn9.Repo.likeRepo;
import com.vanrait6.api_learn9.Repo.postRepo;
import com.vanrait6.api_learn9.Repo.userRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class likeService {

    @Autowired
    private likeRepo repo_like;

    @Autowired
    private postRepo post_Repo;

    @Autowired
    private userRepo user_Repo;

    public int toggleLike(DTO_like dto) {
        Optional<Like> existing =
                repo_like.findByPost_PostIdAndUser_Email(dto.getPost_id(), dto.getEmail());

        if (existing.isPresent()) {
            repo_like.delete(existing.get());
        } else {
            Data_Post post = post_Repo.findById(dto.getPost_id()).orElseThrow();
            Like like = new Like();
            like.setPost(post);
            User user = user_Repo.findByEmail(dto.getEmail());
            like.setUser(user);
            repo_like.save(like);
        }

        return repo_like.countByPost_PostId(dto.getPost_id());
    }

    public int countByPostId(int postId) {
        return repo_like.countByPost_PostId(postId);
    }

    public boolean isUserLiked(int postId, String email) {
        return repo_like.findByPost_PostIdAndUser_Email(postId, email).isPresent();
    }
}

