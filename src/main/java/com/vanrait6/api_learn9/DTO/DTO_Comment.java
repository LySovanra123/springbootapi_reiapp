package com.vanrait6.api_learn9.DTO;

public class DTO_Comment {
    private int commentId_DTO;
    private int postId_DTO;
    private String emailUser_DTO;
    private String commentText_DTO;

    public int getCommentId_DTO() {
        return commentId_DTO;
    }

    public void setCommentId_DTO(int commentId_DTO) {
        this.commentId_DTO = commentId_DTO;
    }

    public int getPostId_DTO() {
        return postId_DTO;
    }

    public void setPostId_DTO(int postId_DTO) {
        this.postId_DTO = postId_DTO;
    }

    public String getEmailUser_DTO() {
        return emailUser_DTO;
    }

    public void setEmailUser_DTO(String emailUser_DTO) {
        this.emailUser_DTO = emailUser_DTO;
    }

    public String getCommentText_DTO() {
        return commentText_DTO;
    }

    public void setCommentText_DTO(String commentText_DTO) {
        this.commentText_DTO = commentText_DTO;
    }
}
