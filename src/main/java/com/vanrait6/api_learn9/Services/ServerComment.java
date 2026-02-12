package com.vanrait6.api_learn9.Services;

import com.vanrait6.api_learn9.Model.Comment;
import com.vanrait6.api_learn9.Repo.commentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServerComment {
    @Autowired
    private commentRepo repo_commentRepo;

    public void save(Comment comment) {
        repo_commentRepo.save(comment);
    }

    public Comment findCommentTextById(int commentIdDto) {
        return repo_commentRepo.findById(commentIdDto).get();
    }

    public void deleteComment(Comment comment) {
        repo_commentRepo.delete(comment);
    }
}
