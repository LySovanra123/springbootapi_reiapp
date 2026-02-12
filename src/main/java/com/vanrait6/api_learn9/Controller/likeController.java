package com.vanrait6.api_learn9.Controller;

import com.vanrait6.api_learn9.DTO.DTO_like;
import com.vanrait6.api_learn9.Services.likeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class likeController {

    @Autowired
    private likeService like_service;

    @PostMapping("/like")
    public ResponseEntity<?> likePost(@RequestBody DTO_like dto) {
        try {
            int totalLikes = like_service.toggleLike(dto);
            return ResponseEntity.ok(Map.of("likeCount", totalLikes));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("Message", e.getMessage()));
        }
    }

    @GetMapping("/checkLike")
    public ResponseEntity<Map<String, Boolean>> checkLike(
            @RequestParam("postId_DTO") int postId,
            @RequestParam("email_DTO") String email
    ) {
        try {
            boolean isLiked = like_service.isUserLiked(postId, email);
            return ResponseEntity.ok(Map.of("isLiked", isLiked));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("isLiked", false));
        }
    }
}
