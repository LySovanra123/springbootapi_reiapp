package com.vanrait6.api_learn9.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DTO_like {
    @JsonProperty("postId_DTO")
    private int post_id;
    @JsonProperty("email_DTO")
    private String email;

    public int getPost_id() { return post_id; }
    public void setPost_id(int post_id) { this.post_id = post_id; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
