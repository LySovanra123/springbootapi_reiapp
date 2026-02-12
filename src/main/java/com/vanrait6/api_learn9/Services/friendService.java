package com.vanrait6.api_learn9.Services;

import com.vanrait6.api_learn9.DTO.DTO_friend;
import com.vanrait6.api_learn9.Model.Friend;
import com.vanrait6.api_learn9.Model.User;
import com.vanrait6.api_learn9.Repo.friendRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class friendService {
    @Autowired
    private friendRepo friendRepo;

    @Autowired
    private userService userService;

    public void follow(Friend friend) {
       friendRepo.save(friend);
    }
    public Friend findByEmailFollowerAndEmailFollowing(String emailFollowerDto, String emailFollowingDto) {
        return friendRepo.findByEmailFollowerAndEmailFollowing(emailFollowerDto, emailFollowingDto);
    }

    public void unFollow(Friend friend) {
        friendRepo.delete(friend);
    }

    public Friend save(Friend friend) {
        return friendRepo.save(friend);
    }

    public List<DTO_friend> getFriendsWithStatus(String me) {
        List<Friend> related = friendRepo.findAllRelated(me);

        Map<String, Friend> aFollowsB = new HashMap<>();
        Map<String, Friend> bFollowsA = new HashMap<>();

        // Separate relationships
        for (Friend f : related) {
            if (f.getEmailFollower().equals(me)) aFollowsB.put(f.getEmailFollowing(), f);
            if (f.getEmailFollowing().equals(me)) bFollowsA.put(f.getEmailFollower(), f);
        }

        Set<String> allUsers = new HashSet<>();
        allUsers.addAll(aFollowsB.keySet());
        allUsers.addAll(bFollowsA.keySet());

        List<DTO_friend> result = new ArrayList<>();

        for (String userEmail : allUsers) {
            String status;
            Friend relation = aFollowsB.get(userEmail);

            if (aFollowsB.containsKey(userEmail) && bFollowsA.containsKey(userEmail)) {
                status = "Friend";
                if (!relation.isFriend()) {
                    relation.setFriend(true);
                    friendRepo.save(relation);
                }
            } else if (bFollowsA.containsKey(userEmail)) {
                status = "Follow Back";
            } else {
                status = "Following";
            }

            // Fetch user info
            User user = userService.findByEmail(userEmail);
            String profileBase64 = user.getProfile() != null
                    ? Base64.getEncoder().encodeToString(user.getProfile())
                    : null;

            DTO_friend dto = new DTO_friend(me, userEmail, user.getUsername(), profileBase64, status);
            result.add(dto);
        }

        return result;
    }


    public long getFriendCount(String email) {
        return friendRepo.getFriendCount(email);
    }
}
