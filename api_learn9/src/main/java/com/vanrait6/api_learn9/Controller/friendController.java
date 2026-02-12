package com.vanrait6.api_learn9.Controller;

import com.vanrait6.api_learn9.DTO.DTO_friend;
import com.vanrait6.api_learn9.Model.Friend;
import com.vanrait6.api_learn9.Services.friendService;
import com.vanrait6.api_learn9.Services.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class friendController {

    @Autowired
    private friendService friendService;

    @Autowired
    private userService userService;

    // FOLLOW or FOLLOW BACK
    @PostMapping("/follow")
    public ResponseEntity<?> follow(@RequestBody DTO_friend request) {
        try {
            Friend friend = friendService.findByEmailFollowerAndEmailFollowing(
                    request.getEmailFollower(),
                    request.getEmailFollowing()
            );

            if (friend == null) {
                friend = new Friend();
                friend.setEmailFollower(request.getEmailFollower());
                friend.setEmailFollowing(request.getEmailFollowing());

                // true = friend (you are now following)
                friend.setFriend(true);

                friendService.follow(friend);
                return ResponseEntity.ok(Map.of("Message", "Following"));
            } else {
                // Already exists, maybe just update friend if it was false
                if (!friend.isFriend()) {
                    friend.setFriend(true);
                    friendService.follow(friend); // update existing
                    return ResponseEntity.ok(Map.of("Message", "Followed back"));
                }
                return ResponseEntity.ok(Map.of("Message", "Already Following"));
            }

        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("Message", "Error: " + e.getMessage()));
        }
    }

    // CHECK FOLLOW STATUS
    @PostMapping("/existsFollow")
    public ResponseEntity<?> existsFollow(@RequestBody DTO_friend request) {

        Friend myFollow = friendService.findByEmailFollowerAndEmailFollowing(
                request.getEmailFollower(),
                request.getEmailFollowing()
        );

        Friend targetFollow = friendService.findByEmailFollowerAndEmailFollowing(
                request.getEmailFollowing(),
                request.getEmailFollower()
        );

        String status;

        if (myFollow != null && targetFollow != null) {
            status = "Friend";
        } else if (myFollow != null) {
            status = "Following";
        } else if (targetFollow != null) {
            status = "Follow Back";
        } else {
            status = "Follow";
        }

        return ResponseEntity.ok(Map.of("status", status));
    }



    // UNFOLLOW
    @DeleteMapping("/unFollow")
    public ResponseEntity<?> unFollow(@RequestBody DTO_friend request) {
        try {
            Friend friend = friendService.findByEmailFollowerAndEmailFollowing(
                    request.getEmailFollower(),
                    request.getEmailFollowing()
            );
            if (friend != null) {
                friendService.unFollow(friend);
                return ResponseEntity.ok(Map.of("Message", "Unfollowed"));
            }
            return ResponseEntity.ok(Map.of("Message", "Not following"));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("Message", "Error: " + e.getMessage()));
        }
    }

    @GetMapping("/{me}")
    public ResponseEntity<List<DTO_friend>> getFriends(@PathVariable String me){
        List<DTO_friend> list = friendService.getFriendsWithStatus(me);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/friend/Count/{email}")
    public ResponseEntity<?> getFriendsCount(@PathVariable String email) {
        try {
            long count = friendService.getFriendCount(email);

            return ResponseEntity.ok(Map.of(
                    "message", "Friend Count",
                    "count", count
            ));

        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Error: " + e.getMessage()));
        }
    }

}
