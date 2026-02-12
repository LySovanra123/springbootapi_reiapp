package com.vanrait6.api_learn9.Controller;

import com.vanrait6.api_learn9.DTO.DTO_Comment;
import com.vanrait6.api_learn9.Model.Comment;
import com.vanrait6.api_learn9.Model.Data_Post;
import com.vanrait6.api_learn9.Services.ServerComment;
import com.vanrait6.api_learn9.Services.postService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class ControllerComment {
    @Autowired
    private ServerComment server_comment;

    @Autowired
    private postService post_service;

    @PostMapping("/comment")
    public ResponseEntity<?> addComment(@RequestBody DTO_Comment commentDto) {
        try {
            System.out.println(commentDto.getPostId_DTO());
            System.out.println(commentDto.getEmailUser_DTO());
            System.out.println(commentDto.getCommentText_DTO());
            Data_Post post = post_service.findById(commentDto.getPostId_DTO());

            if (post == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            Comment comment = new Comment();
            comment.setPost(post);
            comment.setEmailUser(commentDto.getEmailUser_DTO());
            comment.setCommentText(commentDto.getCommentText_DTO());

            server_comment.save(comment);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Map.of("Message", "Comment added successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("Message", "Error: " + e.getMessage()));
        }
    }

    @PutMapping("/updateComment")
    private  ResponseEntity<?> updateComment(@RequestBody DTO_Comment commentDto) {
        try {
            Comment comment = server_comment.findCommentTextById(commentDto.getCommentId_DTO());
            if (comment == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            comment.setCommentText(commentDto.getCommentText_DTO());
            server_comment.save(comment);
            return  ResponseEntity.status(HttpStatus.OK)
                    .body(Map.of("Message", "Comment updated successfully"));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("Message", "Error: " + e.getMessage()));
        }
    }

    @DeleteMapping("/deleteComment/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable("commentId") int commentId) {
        try {
            Comment comment = server_comment.findCommentTextById(commentId);
            if (comment == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            server_comment.deleteComment(comment);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(Map.of("Message", "Comment deleted successfully"));
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("Message", "Error: " + e.getMessage()));
        }
    }

}
