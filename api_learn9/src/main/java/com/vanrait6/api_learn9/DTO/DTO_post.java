package com.vanrait6.api_learn9.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class DTO_post {

    @JsonProperty("postId_DTO")
    private int postId;

    @JsonProperty("emailPost_DTO")
    private String emailPost;

    @JsonProperty("username_DTO")
    private String username;

    @JsonProperty("textPost_DTO")
    private String textPost;

    @JsonProperty("privacy_DTO")
    private String privacy;

    @JsonProperty("imagePost_DTO")
    private String imagePost;

    @JsonProperty("profile_DTO")
    private String profile;

    @JsonProperty("like_DTO")
    private int like;

    private List<DTO_Comment> comments_DTO; // list of comment texts
    private int commentCount_DTO;       // number of comments

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    @JsonProperty("dateOfPost_DTO")
    private String dateOfPost;

    public String getEmailPost() {
        return emailPost;
    }

    public void setEmailPost(String emailPost) {
        this.emailPost = emailPost;
    }

    public String getTextPost() {
        return textPost;
    }

    public void setTextPost(String textPost) {
        this.textPost = textPost;
    }

    public String getPrivacy() {
        return privacy;
    }

    public void setPrivacy(String privacy) {
        this.privacy = privacy;
    }

    public String getImagePost() {
        return imagePost;
    }

    public void setImagePost(String imagePost) {
        this.imagePost = imagePost;
    }

    public String getDateOfPost() {
        return dateOfPost;
    }

    public void setDateOfPost(String dateOfPost) {
        this.dateOfPost = dateOfPost;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }
    public int getCommentCount_DTO() {
        return commentCount_DTO;
    }

    public void setCommentCount_DTO(int commentCount_DTO) {
        this.commentCount_DTO = commentCount_DTO;
    }

    public List<DTO_Comment> getComments_DTO() {
        return comments_DTO;
    }

    public void setComments_DTO(List<DTO_Comment> comments_DTO) {
        this.comments_DTO = comments_DTO;
    }

}
