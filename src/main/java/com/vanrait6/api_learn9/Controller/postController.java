package com.vanrait6.api_learn9.Controller;

import com.vanrait6.api_learn9.DTO.DTO_post;
import com.vanrait6.api_learn9.DTO.DTO_report;
import com.vanrait6.api_learn9.Model.Data_Post;
import com.vanrait6.api_learn9.Model.Report;
import com.vanrait6.api_learn9.Model.User;
import com.vanrait6.api_learn9.Services.postService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class postController {
    @Autowired
    private postService service;

    @PostMapping(value = "/postData", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> postData(
            @RequestParam("emailPost_DTO") String email,
            @RequestParam("textPost_DTO") String text,
            @RequestParam("privacy_DTO") String privacy,
            @RequestParam("like_DTO") String like,
            @RequestParam(value = "dateOfPost_DTO", required = false) String date,
            @RequestPart(value = "imagePost_DTO", required = false) MultipartFile image
    ) {
        try {
            User user = service.findByEmail(email);
            if (user == null) {
                return ResponseEntity.badRequest()
                        .body(Map.of("Message", "User not found with email: " + email));
            }

            Data_Post dataPost = new Data_Post();
            dataPost.setUser(user);
            dataPost.setTextPost(text);
            dataPost.setPrivacy(privacy);

            if (date != null && !date.isEmpty()) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime dateTime = LocalDateTime.parse(date, formatter);
                dataPost.setDataOfPost(dateTime);
            }


            if (image != null && !image.isEmpty()) {
                dataPost.setImagePost(image.getBytes());
            }

            service.addDataPost(dataPost);
            return ResponseEntity.ok(Map.of("Message", "Posted successfully"));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("Message", "Error: " + e.getMessage()));
        }
    }



    @GetMapping("/getDataPost")
    public ResponseEntity<List<?>> getDataPost(){
        return ResponseEntity.ok(service.getAllPosts());
    }

    @PostMapping("/getYourPost")
    public ResponseEntity<List<?>> getYourPost(@RequestBody DTO_post request) {
        try{
            List<DTO_post> dataPost = service.findYourPostByEmail(request.getEmailPost());
            return ResponseEntity.ok(dataPost);
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.emptyList());
        }
    }

    @GetMapping("/post/Count/{email}")
    public ResponseEntity<?> getCountPost(@PathVariable String email){
        try{
            long count = service.findCountBy_Email(email);
            return ResponseEntity.ok(Map.of("count", count));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("Message", "Error: " + e.getMessage()));
        }
    }

    @PutMapping("/editPost")
    public ResponseEntity<?> editPost(
            @RequestParam("postId_DTO") Integer postId,
            @RequestParam("textPost_DTO") String textPost,
            @RequestParam("privacy_DTO") String privacy
    ) {
        try {
            Data_Post dataPost = service.findPostById(postId);

            if (dataPost == null) {
                return ResponseEntity.badRequest()
                        .body(Map.of("Message", "Post not found with id: " + postId));
            }

            dataPost.setTextPost(textPost);
            dataPost.setPrivacy(privacy);

            service.UpdatePost(dataPost);

            return ResponseEntity.ok(
                    Map.of("Message", "Updated Post successfully")
            );

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("Message", "Error: " + e.getMessage()));
        }
    }

    @DeleteMapping("/deletePost")
    public ResponseEntity<?> deletePost(@RequestBody DTO_post request) {
        try {
            service.deletePostById(request.getPostId());
            return ResponseEntity.ok(Map.of("Message", "Post deleted successfully"));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("Message", "Error: " + e.getMessage()));
        }
    }
}
