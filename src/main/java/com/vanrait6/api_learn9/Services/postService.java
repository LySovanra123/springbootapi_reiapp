package com.vanrait6.api_learn9.Services;

import com.vanrait6.api_learn9.DTO.DTO_Comment;
import com.vanrait6.api_learn9.DTO.DTO_post;
import com.vanrait6.api_learn9.Model.Comment;
import com.vanrait6.api_learn9.Model.Data_Post;
import com.vanrait6.api_learn9.Model.User;
import com.vanrait6.api_learn9.Repo.commentRepo;
import com.vanrait6.api_learn9.Repo.postRepo;
import com.vanrait6.api_learn9.Repo.userRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class postService {

    @Autowired
    private userRepo userRepo;
    @Autowired
    private postRepo postRepo;
    @Autowired
    private commentRepo commentRepo;

    @Autowired
    private likeService likeService;
    public List<DTO_post> getAllPosts() {
        return postRepo.findAll()
                .stream()
                .map(post -> {
                    DTO_post dto = new DTO_post();

                    // USER TABLE
                    if (post.getUser() != null) {
                        dto.setEmailPost(post.getUser().getEmail());
                        dto.setUsername(post.getUser().getUsername());

                        if (post.getUser().getProfile() != null) {
                            dto.setProfile(
                                    Base64.getEncoder()
                                            .encodeToString(post.getUser().getProfile())
                            );
                        }
                    }

                    // POST TABLE
                    dto.setPostId(post.getPostId());
                    dto.setTextPost(post.getTextPost());
                    dto.setPrivacy(post.getPrivacy());

                    if (post.getImagePost() != null) {
                        dto.setImagePost(
                                Base64.getEncoder()
                                        .encodeToString(post.getImagePost())
                        );
                    }

                    if (post.getDataOfPost() != null) {
                        dto.setDateOfPost(post.getDataOfPost().toString());
                    }

                    // --- GET COMMENTS ---
                    List<Comment> comments = commentRepo.findByPostId(post.getPostId());

                    List<DTO_Comment> commentDTOList = comments.stream().map(c -> {
                        DTO_Comment dtoComment = new DTO_Comment();
                        dtoComment.setCommentId_DTO(c.getId());
                        dtoComment.setPostId_DTO(c.getPost().getPostId());
                        dtoComment.setEmailUser_DTO(c.getEmailUser());
                        dtoComment.setCommentText_DTO(c.getCommentText());
                        return dtoComment;
                    }).collect(Collectors.toList());
                    // set to PostDTO
                    dto.setComments_DTO(commentDTOList);
                    dto.setCommentCount_DTO(commentDTOList.size());

                    //---GET LIKE
                    int likeCount = likeService.countByPostId(post.getPostId());
                    dto.setLike(likeCount);

                    return dto;
                })
                .toList();
    }


    @Transactional
    public void addDataPost(Data_Post dataPost) {
        postRepo.save(dataPost);
    }

    public User findByEmail(String emailPost) {
        return userRepo.findByEmail(emailPost);
    }

    public List<DTO_post> findYourPostByEmail(String emailPost) {
        return postRepo.findAll()
                .stream()
                .filter(post -> post.getUser() != null && post.getUser().getEmail().equals(emailPost))
                .map(post -> {
                    DTO_post dto = new DTO_post();

                    // USER TABLE
                    dto.setEmailPost(post.getUser().getEmail());
                    dto.setUsername(post.getUser().getUsername());

                    if (post.getUser().getProfile() != null) {
                        dto.setProfile(Base64.getEncoder().encodeToString(post.getUser().getProfile()));
                    }

                    // POST TABLE
                    dto.setPostId(post.getPostId());
                    dto.setTextPost(post.getTextPost());
                    dto.setPrivacy(post.getPrivacy());

                    if (post.getImagePost() != null) {
                        dto.setImagePost(Base64.getEncoder().encodeToString(post.getImagePost()));
                    }

                    if (post.getDataOfPost() != null) {
                        dto.setDateOfPost(post.getDataOfPost().toString());
                    }

                    // --- GET COMMENTS ---
                    List<Comment> comments = commentRepo.findByPostId(post.getPostId());

                    List<DTO_Comment> commentDTOList = comments.stream().map(c -> {
                        DTO_Comment dtoComment = new DTO_Comment();
                        dtoComment.setCommentId_DTO(c.getId());
                        dtoComment.setPostId_DTO(c.getPost().getPostId());
                        dtoComment.setEmailUser_DTO(c.getEmailUser());
                        dtoComment.setCommentText_DTO(c.getCommentText());
                        return dtoComment;
                    }).collect(Collectors.toList());
                    // set to PostDTO
                    dto.setComments_DTO(commentDTOList);
                    dto.setCommentCount_DTO(commentDTOList.size());

                    //---GET LIKE
                    int likeCount = likeService.countByPostId(post.getPostId());
                    dto.setLike(likeCount);

                    return dto;
                })
                .toList();
    }

    public long findCountBy_Email(String email) {
        return postRepo.countByEmail(email);
    }

    public void UpdatePost(Data_Post dataPost) {
        postRepo.save(dataPost);
    }


    public Data_Post findPostById(Integer postId) {
        return  postRepo.findById(postId).orElse(null);
    }

    public void deletePostById(int postId) {
        postRepo.deleteById(postId);
    }

    public Data_Post findById(int postIdDto) {
        return postRepo.findById(postIdDto).orElse(null);
    }
}
