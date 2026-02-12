package com.vanrait6.api_learn9.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DTO_friend {
    @JsonProperty("emailFollower")
    private String emailFollower;

    @JsonProperty("emailFollowing")
    private String emailFollowing;

    @JsonProperty("username")
    private String username;

    @JsonProperty("profile")
    private String profile; // Base64 encoded

    @JsonProperty("friendStatus")
    private String friendStatus;

    public DTO_friend(String emailFollower, String emailFollowing,
                      String username, String profile, String friendStatus) {
        this.emailFollower = emailFollower;
        this.emailFollowing = emailFollowing;
        this.username = username;
        this.profile = profile;
        this.friendStatus = friendStatus;
    }

    public String getEmailFollower() {
        return emailFollower;
    }

    public void setEmailFollower(String emailFollower) {
        this.emailFollower = emailFollower;
    }

    public String getEmailFollowing() {
        return emailFollowing;
    }

    public void setEmailFollowing(String emailFollowing) {
        this.emailFollowing = emailFollowing;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getFriendStatus() {
        return friendStatus;
    }

    public void setFriendStatus(String friendStatus) {
        this.friendStatus = friendStatus;
    }
}

