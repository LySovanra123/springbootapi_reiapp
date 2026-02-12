package com.vanrait6.api_learn9.Model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "friend")
public class Friend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int fir_id;
    @Column(name = "email_follower")
    private String emailFollower;
    @Column(name = "email_following")
    private String emailFollowing;

    private String friendStatus;

    private boolean isFriend;

    public String getFriendStatus() {
        return friendStatus;
    }

    public void setFriendStatus(String friendStatus) {
        this.friendStatus = friendStatus;
    }

    public boolean isFriend() {
        return isFriend;
    }

    public void setFriend(boolean friend) {
        isFriend = friend;
    }

    public int getFir_id() {
        return fir_id;
    }

    public void setFir_id(int fir_id) {
        this.fir_id = fir_id;
    }

    public String getEmailFollowing() {
        return emailFollowing;
    }

    public void setEmailFollowing(String emailFollowing) {
        this.emailFollowing = emailFollowing;
    }

    public String getEmailFollower() {
        return emailFollower;
    }

    public void setEmailFollower(String emailFollower) {
        this.emailFollower = emailFollower;
    }
}
