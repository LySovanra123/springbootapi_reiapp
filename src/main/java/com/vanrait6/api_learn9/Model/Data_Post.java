package com.vanrait6.api_learn9.Model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "posts")
public class Data_Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int postId;  // <-- rename from post_id

    private String textPost;
    private String privacy;

    @Column(columnDefinition = "LONGBLOB")
    private byte[] imagePost;

    private LocalDateTime dataOfPost;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Comment> comments;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_email", referencedColumnName = "email")
    private User user;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Like> likes;

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
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

    public byte[] getImagePost() {
        return imagePost;
    }

    public void setImagePost(byte[] imagePost) {
        this.imagePost = imagePost;
    }

    public LocalDateTime getDataOfPost() {
        return dataOfPost;
    }

    public void setDataOfPost(LocalDateTime dataOfPost) {
        this.dataOfPost = dataOfPost;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Like> getLikes() {
        return likes;
    }

    public void setLikes(List<Like> likes) {
        this.likes = likes;
    }
}
